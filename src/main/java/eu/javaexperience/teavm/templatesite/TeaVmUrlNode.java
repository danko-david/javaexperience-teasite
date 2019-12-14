package eu.javaexperience.teavm.templatesite;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import eu.javaexperience.file.AbstractFile;
import eu.javaexperience.io.IOTools;
import eu.javaexperience.reflect.Mirror;
import eu.javaexperience.teavm.TeaVmWebCompiler;
import eu.javaexperience.web.MIME;
import eu.javaexperience.web.dispatch.url.CachedSaltedContentUrlNode;
import eu.javaexperience.web.dispatch.url.spec.compile.CompilerUrlNode;

public class TeaVmUrlNode
{
	protected boolean isProduction; 
	protected String tmpDir;
	protected AbstractFile targetFile;
	protected Class<?> masterClass;
	protected CachedSaltedContentUrlNode scripts;
	
	public TeaVmUrlNode(boolean production, String tmpDir, AbstractFile targetFile, Class<?> masterClass)
	{
		this.isProduction = production;
		this.tmpDir = tmpDir;
		this.targetFile = targetFile;
		this.masterClass = masterClass;
		if(production)
		{
			scripts = new CachedSaltedContentUrlNode("sitescript.js", MIME.javascript.mime)
			{
				@Override
				public byte[] loadContent()
				{
					try(InputStream is = targetFile.openRead())
					{
						return IOTools.loadAllFromInputStream(is);
					}
					catch (IOException e)
					{
						e.printStackTrace();
						return Mirror.emptyByteArray;
					}
				}
				
				@Override
				public long determineLastModified()
				{
					return targetFile.lastModified();
				}
			};
		}
		else
		{
			scripts = new CompilerUrlNode
			(
				"sitescript.js",
				new File(null == targetFile?tmpDir+"/final_out.js":targetFile.getUrl()),
				new TeaVmWebCompiler(masterClass, new File(tmpDir)),
				production
			);
		}
		
		if(!production)
		{
			scripts.setCheckModifiedOnRequest(true);
		}
		scripts.setValidForever(true);
		scripts.refresh();
	}
	
	public CachedSaltedContentUrlNode getScriptNode()
	{
		return scripts;
	}
}
