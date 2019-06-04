package eu.javaexperience.teasite;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

import org.teavm.jso.core.JSFunction;

import eu.javaexperience.interfaces.simple.getBy.GetBy1;
import eu.javaexperience.interfaces.simple.getBy.GetBy2;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.javaexperience.reflect.PrimitiveTools;
import eu.javaexperience.rpc.RpcFacility;
import eu.javaexperience.rpc.client.JvxClientException;
import eu.javaexperience.rpc.codegen.JavaRpcExportTools;
import eu.javaexperience.rpc.codegen.JavaRpcExportTools.SourceModifierBuilder;
import eu.javaexperience.rpc.function.JavaFunctionRpcWrapper;
import eu.javaexperience.rpc.function.RpcFunctionParameter;
import eu.javaexperience.semantic.references.MayNull;
import eu.teasite.frontend.api.ApiInterface;

public class TeaVmApiExporter
{
	protected static class Inject implements SimplePublish2<String, String>
	{
		@Override
		public void publish(String a, String b)
		{
			System.out.println("Injected method invocation");
		}
	}
	
	public static void exportApis
	(
		String dstDir,
		List<RpcFacility> api,
		String packageName,
		Class... apiRequiredClasses
	)
			throws IOException
	{
		JavaRpcExportTools.generateToDir
		(
			dstDir,
			teaVmClassGenerator
			(
				packageName,
				teaVmApiInterfaceMethodGenerator,
				apiRequiredClasses
			),
			api
		);
	}
	
	public static void exportBothApis
	(
		String dstDir,
		List<RpcFacility> api,
		String packageName,
		Class... apiRequiredClasses
	)
			throws IOException
	{
		JavaRpcExportTools.generateToDir
		(
			dstDir,
			teaVmClassGenerator
			(
				packageName,
				generateTeaVmFunctionGeneratorBoth(null, null),
				apiRequiredClasses
			),
			api
		);
	}
	
	
	public static void exportApis
	(
		String dstDir,
		SimplePublish2<StringBuilder, JavaFunctionRpcWrapper> functionGenerator,
		List<RpcFacility> api,
		String packageName,
		Class... apiRequiredClasses
	)
			throws IOException
	{
		JavaRpcExportTools.generateToDir
		(
			dstDir,
			teaVmClassGenerator
			(
				packageName,
				functionGenerator,
				apiRequiredClasses
			),
			api
		);
	}
	
	public static void __main(String[] args) throws IOException
	{
		/*
		System.out.println(InjectCode.getClassBytecodeBase64(Inject.class));
		*/
		System.exit(0);
		SourceModifierBuilder smb = new SourceModifierBuilder();
		
		smb.addModifier
		(
			JavaRpcExportTools.withImportedClasses
			(
				JSFunction.class,
				BiFunction.class,
				JvxClientException.class,
				SimplePublish2.class
			)
		);
		
		smb.addModifier(JavaRpcExportTools.addPackage("eu.teasite.frontend.api.interfaces"));
		
		//smb.addModifier(JavaRpcExportTools.makeClassAbstract());
		//smb.addModifier(StringFunctions.replace("public void", "public abstract void"));
		
		//smb.addModifier(StringFunctions.replace("public void ", "@ "));
		
		//smb.addModifier(JavaRpcExportTools.makeClassAbstract());
		
		//smb.addModifier(JavaRpcExportTools.extendsClass(JSObject.class.getName()));
		
		HashMap<String, Object> opts = new HashMap<>();
		
		opts.put("async", "SimplePublish2<$ret, JvxClientException>");
		opts.put("async_rettype_replace", "$ret");
		
		//opts.put("async", "JsSimplePublish2<$ret, JvxClientException>");
		//opts.put("async_rettype_replace", "$ret");
		
		//opts.put("async", "JsSimplePublish2");
		
		//opts.put("async", "BiFunction<$ret, JvxClientException, Void>");
		//opts.put("async_rettype_replace", "$ret");
		
		//opts.put("async", "JSFunction");
		
	}
	
	public static SimplePublish2<StringBuilder, JavaFunctionRpcWrapper> generateTeaVmFunctionGenerator
	(
		@MayNull final GetBy1<String, String> mangleReturnType,
		@MayNull final GetBy1<String, RpcFunctionParameter> mangeParameterType
	)
	{
		return new SimplePublish2<StringBuilder, JavaFunctionRpcWrapper>()
		{
			@Override
			public void publish(StringBuilder sb, JavaFunctionRpcWrapper m)
			{
				sb.append("\tpublic void ");
				sb.append(m.getMethodName());
				sb.append("(SimplePublish2<");
				
				String retType = m.getReturningClass().getTypeFullQualifiedName();
				
				retType = PrimitiveTools.objectifyPrimitiveName(retType, retType);
				
				if(null != mangleReturnType)
				{
					retType = mangleReturnType.getBy(retType);
				}
				
				
				sb.append(retType);
				sb.append(", JvxClientException> callback");
				
				int i=0;
				for(RpcFunctionParameter p:m.getParameterClasses())
				{
					sb.append(", ");
					
					if(null != mangeParameterType)
					{
						sb.append(mangeParameterType.getBy(p));
					}
					else
					{
						sb.append(p.getTypeFullQualifiedName());
					}
					
					sb.append(" ");
					sb.append((char)('a'+i));
					++i;
				}
				
				sb.append(")\n\t{\n\t\t");
				sb.append("transfer.publish(pack(\"");
				sb.append(m.getMethodName());
				sb.append("\"");
				
				i=0;
				for(RpcFunctionParameter p:m.getParameterClasses())
				{
					sb.append(", ");
					sb.append((char)('a'+i));
					++i;
				}
				
				sb.append("), (SimplePublish2) callback);\n\t}\n\n"); 
			}
		};
	}
	
	public static SimplePublish2<StringBuilder, JavaFunctionRpcWrapper> generateTeaVmFunctionGeneratorBoth
	(
		@MayNull final GetBy1<String, String> mangleReturnType,
		@MayNull final GetBy1<String, RpcFunctionParameter> mangeParameterType
	)
	{
		return new SimplePublish2<StringBuilder, JavaFunctionRpcWrapper>()
		{
			@Override
			public void publish(StringBuilder sb, JavaFunctionRpcWrapper m)
			{
				{
					sb.append("\tpublic void ");
					sb.append(m.getMethodName());
					sb.append("(SimplePublish2<");
					
					String retType = m.getReturningClass().getTypeFullQualifiedName();
					
					retType = PrimitiveTools.objectifyPrimitiveName(retType, retType);
					
					if(null != mangleReturnType)
					{
						retType = mangleReturnType.getBy(retType);
					}
					
					
					sb.append(retType);
					sb.append(", JvxClientException> callback");
					
					int i=0;
					for(RpcFunctionParameter p:m.getParameterClasses())
					{
						sb.append(", ");
						
						if(null != mangeParameterType)
						{
							sb.append(mangeParameterType.getBy(p));
						}
						else
						{
							sb.append(p.getTypeFullQualifiedName());
						}
						
						sb.append(" ");
						sb.append((char)('a'+i));
						++i;
					}
					
					sb.append(")\n\t{\n\t\t");
					sb.append("transfer.publish(pack(\"");
					sb.append(m.getMethodName());
					sb.append("\"");
					
					i=0;
					for(RpcFunctionParameter p:m.getParameterClasses())
					{
						sb.append(", ");
						sb.append((char)('a'+i));
						++i;
					}
					
					sb.append("), (SimplePublish2) callback);\n\t}\n\n");
				}
				
				{
					sb.append("\tpublic ");
					
					String retType = m.getReturningClass().getTypeFullQualifiedName();
					
					retType = PrimitiveTools.objectifyPrimitiveName(retType, retType);
					
					if(null != mangleReturnType)
					{
						retType = mangleReturnType.getBy(retType);
					}
					
					if(retType.equals("Void"))
					{
						retType = "void";
					}
					
					sb.append(retType);
					sb.append(" ");
					sb.append(m.getMethodName());
					sb.append("(");
					int i=0;
					for(RpcFunctionParameter p:m.getParameterClasses())
					{
						if(i > 0)
						{
							sb.append(", ");
						}
						
						if(null != mangeParameterType)
						{
							sb.append(mangeParameterType.getBy(p));
						}
						else
						{
							sb.append(p.getTypeFullQualifiedName());
						}
						
						sb.append(" ");
						sb.append((char)('a'+i));
						++i;
					}
					
					sb.append(") throws JvxClientException\n\t{\n\t\t");
					
					String retClass = m.getReturningClass().getTypeName().toLowerCase();
					if(!retClass.equals("void"))
					{
						sb.append("return (");
						sb.append(retType);
						sb.append(") ");
					}
					
					sb.append("transfer.transmitSync(pack(\"");
					sb.append(m.getMethodName());
					sb.append("\"");
					
					i=0;
					for(RpcFunctionParameter p:m.getParameterClasses())
					{
						sb.append(", ");
						sb.append((char)('a'+i));
						++i;
					}
					
					sb.append("));\n\t}\n\n");
				}
			}
		};
	}
	
	protected static SimplePublish2<StringBuilder, JavaFunctionRpcWrapper> teaVmApiInterfaceMethodGenerator = generateTeaVmFunctionGenerator(null, null);
	
	protected static final GetBy2<String, String, Collection<JavaFunctionRpcWrapper>> teaVmClassGenerator
	(
		final String packageName,
		final SimplePublish2<StringBuilder, JavaFunctionRpcWrapper> functionGenerator,
		final Class... imports
	)
	{
		return new GetBy2<String, String, Collection<JavaFunctionRpcWrapper>>()
		{
			@Override
			public String getBy(String a, Collection<JavaFunctionRpcWrapper> b)
			{
				StringBuilder sb = new StringBuilder();
				sb.append("package ");
				sb.append(packageName);
				sb.append(";\n\n");
				
				for(Class cls:imports)
				{
					sb.append("import ");
					sb.append(cls.getName());
					sb.append(";\n");
				}
				
				sb.append("\npublic class ");
				sb.append(a);
				sb.append(" extends ");
				sb.append(ApiInterface.class.getName());
				sb.append("\n{\n");
				
				for(JavaFunctionRpcWrapper m:b)
				{
					functionGenerator.publish(sb, m);
				}
				
				sb.append("}");
				
				return sb.toString();
			}
		};
		
	}
	
	
}
