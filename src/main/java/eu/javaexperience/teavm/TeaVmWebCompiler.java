package eu.javaexperience.teavm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.teavm.diagnostics.ProblemProvider;
import org.teavm.tooling.TeaVMProblemRenderer;
import org.teavm.tooling.TeaVMTargetType;
import org.teavm.tooling.TeaVMTool;
import org.teavm.tooling.TeaVMToolLog;
import org.teavm.tooling.sources.SourceFileInfo;
import org.teavm.tooling.sources.SourceFileProvider;
import org.teavm.vm.TeaVM;
import org.teavm.vm.TeaVMOptimizationLevel;

import eu.javaexperience.io.IOTools;
import eu.javaexperience.reflect.Mirror;
import eu.javaexperience.text.StringTools;
import eu.javaexperience.web.dispatch.url.spec.compile.WebCompiler;


public class TeaVmWebCompiler implements WebCompiler
{
	protected Class<?> mainClass;
	
	protected File tmpDir;
	
	protected boolean compactize = false;
	
	public TeaVmWebCompiler(Class<?> mainClass, File tmpDir)
	{
		this.mainClass = mainClass;
		this.tmpDir = tmpDir;
	}
	
	protected TeaVMTool compiler;
	
	protected static final TeaVMToolLog TEAVM_LOG = new TeaVMToolLog()
	{
		@Override
		public void warning(String arg0, Throwable arg1)
		{
			System.out.println(arg0);
			arg1.printStackTrace();
		}
		
		@Override
		public void warning(String arg0)
		{
			System.out.println(arg0);
		}
		
		@Override
		public void info(String arg0, Throwable arg1)
		{
			System.out.println(arg0);
			arg1.printStackTrace();
		}
		
		@Override
		public void info(String arg0)
		{
			System.out.println(arg0);
		}
		
		@Override
		public void error(String arg0, Throwable arg1)
		{
			System.out.println(arg0);
			arg1.printStackTrace();
		}
		
		@Override
		public void error(String arg0)
		{
			System.out.println(arg0);
		}
		
		@Override
		public void debug(String arg0, Throwable arg1)
		{
			System.out.println(arg0);
			arg1.printStackTrace();
		}
		
		@Override
		public void debug(String arg0)
		{
			System.out.println(arg0);
		}
	};
	
	protected TeaVMTool getConfiguredCompiler()
	{
		if(null == compiler)
		{
			TeaVMTool t = new TeaVMTool();
			
			tmpDir.mkdirs();
			
			t.setLog(TEAVM_LOG);
			
			t.addSourceFileProvider(new SourceFileProvider()
			{
				@Override
				public void open() throws IOException
				{
				}
				
				@Override
				public SourceFileInfo getSourceFile(String arg0) throws IOException
				{
					if(null == arg0)
					{
						return null;
					}
					
					File _src = null;
					for(String s:sourceDirs)
					{
						File f = new File(s+"/"+arg0);
						if(f.exists())
						{
							_src = f;
							break;
						}
					}
					
					if(null == _src)
					{
						return null;
					}
					
					final File src = _src;
					
					return new SourceFileInfo()
					{
						@Override
						public InputStream open() throws IOException
						{
							return new FileInputStream(src);
						}
						
						@Override
						public long lastModified()
						{
							return src.lastModified();
						}
					};
				}
				
				@Override
				public void close() throws IOException
				{
				}
			});
			
			t.setObfuscated(false);
			
			if(compactize)
			{
				t.setDebugInformationGenerated(false);
				t.setOptimizationLevel(TeaVMOptimizationLevel.FULL);
				//t.setMinifying(true);
			}
			else
			{
				t.setDebugInformationGenerated(true);
				t.setOptimizationLevel(TeaVMOptimizationLevel.SIMPLE);
				t.setSourceFilesCopied(true);
				t.setSourceMapsFileGenerated(true);
			}
			
			t.setClassLoader(Thread.currentThread().getContextClassLoader());
			
			t.setCacheDirectory(tmpDir);
			t.setMainClass(mainClass.getName());
			
			t.setTargetType(TeaVMTargetType.JAVASCRIPT);
			t.setTargetDirectory(tmpDir);
			t.setTargetFileName("out.js");
			
			compiler = t;
		}
		
		return compiler;
	}
	
	protected HashSet<String> sourceDirs = new HashSet<>();
	
	public void addSourceSearchDir(String dir)
	{
		if(null != dir)
		{
			sourceDirs.add(dir);
		}
	}
	
	@Override
	public void compile(boolean incremental)
	{
		try
		{
			TeaVMTool c = getConfiguredCompiler();
			c.generate();
			ProblemProvider problems = c.getProblemProvider();
			if(!problems.getProblems().isEmpty())
			{
				TeaVMProblemRenderer.describeProblems((TeaVM)Mirror.tryGetFieldValue(c, "vm"), TEAVM_LOG);
			}
			else
			{
				accumulateUsedSourceFilesList(c.getUsedResources().toArray(Mirror.emptyStringArray));
			}
		}
		catch(Exception e)
		{
			Mirror.propagateAnyway(e);
		}
	}

	@Override
	public void emitMergedOutput(String destinationFile)
	{
		try
		{
			IOTools.putFileContent(destinationFile, Mirror.emptyByteArray);
			if(new File(tmpDir+"/runtime.js").exists())
			{
				IOTools.appendData(destinationFile, IOTools.loadFileContent(tmpDir+"/runtime.js"));
			}
			
			if(new File(tmpDir+"/additional.js").exists())
			{
				IOTools.appendData(destinationFile, IOTools.loadFileContent(tmpDir+"/additional.js"));
			}
			IOTools.appendData(destinationFile, IOTools.loadFileContent(tmpDir+"/out.js"));
			new File(destinationFile).setLastModified(getSourcesLastModification());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			new File(destinationFile).delete();
		}
	}
	
	protected File getSourceListFile()
	{
		return new File(tmpDir+"/sources.list");
	}
	
	protected void accumulateUsedSourceFilesList(String[] list) throws IOException
	{
		IOTools.putFileContent(getSourceListFile().toString(), StringTools.join("\n", list).getBytes());
	}
	
	protected Collection<String> getUsedFiles()
	{
		File lst = getSourceListFile();
		Collection<String> ret = null;
		if(lst.exists())
		{
			ret = new ArrayList<>();
			try
			{
				IOTools.loadFillAllLine(lst.toString(), ret);
			}
			catch(Exception e)
			{}
		}
		
		if(null != ret && !ret.isEmpty())
		{
			return ret;
		}
		
		TeaVMTool compiler = getConfiguredCompiler();
		Collection<String> di = compiler.getUsedResources();
		if(di.isEmpty())
		{
			try
			{
				compiler.generate();
			}
			catch(Exception e){}
			di = compiler.getUsedResources();
		}
		
		
		try
		{
			accumulateUsedSourceFilesList(di.toArray(Mirror.emptyStringArray));
		}
		catch (IOException e)
		{
			Mirror.propagateAnyway(e);
		}
		
		return di;
	}
	
	@Override
	public long getSourcesLastModification()
	{
		long ret = 0;
		for(String cls:getUsedFiles())
		{
			try
			{
				String c = StringTools.replaceAllStrings(StringTools.getSubstringBeforeLastString(cls, ".java"), "/", ".");
				URL url = Mirror.getClassUrl(Class.forName(c));
				
				//urldecode: if the path contains spaces or accent charaters
				long t = new File(URLDecoder.decode(url.getFile())).lastModified();
				if(ret < t)
				{
					ret = t;
				}
			}
			catch (Throwable e){}
		}
		
		return ret;
	}

	public void setCompactize(boolean b)
	{
		compactize = b;
	}

	@Override
	public boolean isProduction()
	{
		return compactize;
	}

	@Override
	public void setProduction(boolean val)
	{
		compactize = val;
	}
}
