package eu.javaexperience.teasite;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import eu.javaexperience.file.AbstractFile;
import eu.javaexperience.interfaces.simple.getBy.GetBy1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.io.file.FileTools;
import eu.javaexperience.rpc.RpcFacility;
import eu.javaexperience.rpc.SimpleRpcRequest;
import eu.javaexperience.rpc.SimpleRpcSession;
import eu.javaexperience.rpc.web.RpcUrlNode;
import eu.javaexperience.semantic.references.MayNull;
import eu.javaexperience.teavm.url.TeaVmUrlNode;
import eu.javaexperience.web.Context;
import eu.javaexperience.web.dispatch.url.AttachDirectoryURLNode;
import eu.javaexperience.web.dispatch.url.JavaClassURLNode;
import eu.javaexperience.web.dispatch.url.PreparedURL;
import eu.javaexperience.web.dispatch.url.URLNode;
import eu.javaexperience.web.dom.build.WebSoftwareBundle;

public class TeasiteBundle extends JavaClassURLNode
{
	public final URLNode resources;

	public final TeaVmUrlNode script;
	
	public final RpcUrlNode<SimpleRpcRequest, SimpleRpcSession> api;
	
	protected WebSoftwareBundle[] bundles;
	
	protected List<String> css = new ArrayList<>();
	protected List<String> preJs = new ArrayList<>();
	protected List<String> postJs = new ArrayList<>();
	
	public TeasiteBundle
	(
		String name,
		boolean dev,
		@MayNull AbstractFile resourcesDir,
		@MayNull String compileTmpDir,
		@MayNull AbstractFile scriptFile,
		@MayNull Class scriptClass,
		@MayNull WebSoftwareBundle[] bundles,
		@MayNull RpcFacility... rpcFacilities
	)
	{
		super(name);
		
		if(null != resourcesDir)
		{
			resources = new AttachDirectoryURLNode("resources", resourcesDir, dev);
			addChild(resources);
		}
		else
		{
			this.resources = null;
		}
		
		if(null != scriptClass && null != scriptFile && null != compileTmpDir)
		{
			script = new TeaVmUrlNode
			(
				!dev,
				compileTmpDir,
				scriptFile,
				scriptClass
			);
			script.getScriptNode().getRefreshListener().addEventListener((n)->refreshBundles());
			addChild(script.getScriptNode());
			refreshBundles();
		}
		else
		{
			script = null;
		}
		
		if(null != rpcFacilities && 0 != rpcFacilities.length)
		{
			api = RpcUrlNode.createSimple("api", "", rpcFacilities);
			addChild(api);
		}
		else
		{
			api = null;
		}
		
		this.bundles = bundles;
	}
	
	public void setWebSoftwareBundles(WebSoftwareBundle... sw)
	{
		this.bundles = sw;
		refreshBundles();
	}
	
	protected GetBy1<String, Context> pageId;
	
	protected void refreshBundles()
	{
		css.clear();
		preJs.clear();
		postJs.clear();
		if(null != bundles)
		{
			for(WebSoftwareBundle b:bundles)
			{
				{
					List<String> c = b.getCss();
					if(null != c)
					{
						css.addAll(c);
					}
				}
				
				{
					List<String> pre = b.getPreJs();
					if(null != pre)
					{
						preJs.addAll(pre);
					}
				}
				
				{
					List<String> post = b.getPostJs();
					if(null != post)
					{
						postJs.addAll(postJs);
					}
				}
			}
		}
		
		if(null != script)
		{
			postJs.add(FileTools.normalizeSlashes(script.getScriptNode().getSaltedUrl()));
		}
	}
	
	public SimplePublish1<Context> asPathDispatch()
	{
		return ctx->
		{
			PreparedURL url = ctx.getRequestUrl();
			url.setPathPointer(url.getDomainElements());
			TeasiteBundle.this.dispatch(ctx);
		};
	}
	
	@Override
	protected boolean beforeCall(Context ctx, Method m)
	{
		return true;
	}
	
	@Override
	protected void afterCall(Context ctx, Method m){}
	
	@Override
	protected void backward(Context ctx)
	{
		endpoint(ctx);
	}
	
	@Override
	protected boolean endpoint(Context ctx)
	{
		TeasiteBackendTools.renderBootPage(ctx, css, preJs, postJs, null == pageId?null:pageId.getBy(ctx));
		return true;
	}
	
	@Override
	protected boolean access(Context ctx)
	{
		return true;
	}
}
