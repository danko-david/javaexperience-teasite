package eu.javaexperience.teavm.templatesite;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eu.javaexperience.file.AbstractFile;
import eu.javaexperience.file.fs.os.OsFile;
import eu.javaexperience.interfaces.simple.getBy.GetBy1;
import eu.javaexperience.io.IOStreamServer;
import eu.javaexperience.io.fd.IOStreamFactory;
import eu.javaexperience.patterns.behavioral.cor.CorDispatcher;
import eu.javaexperience.patterns.behavioral.cor.link.CorChainLink;
import eu.javaexperience.rpc.JavaClassRpcCollector;
import eu.javaexperience.rpc.RpcFacility;
import eu.javaexperience.rpc.RpcRequest;
import eu.javaexperience.rpc.RpcTools;
import eu.javaexperience.rpc.SimpleRpcRequest;
import eu.javaexperience.rpc.SimpleRpcSession;
import eu.javaexperience.rpc.web.RpcUrlNode;
import eu.javaexperience.teavm.templatesite.common.PageId;
import eu.javaexperience.teavm.templatesite.common.PageStorage;
import eu.javaexperience.teavm.templatesite.frontend.PageStarter;
import eu.javaexperience.web.Context;
import eu.javaexperience.web.TemplateWebsiteServer;
import eu.javaexperience.web.dispatch.DefaultDispatchStructure;
import eu.javaexperience.web.dispatch.url.PreparedURL;
import eu.javaexperience.web.dom.build.WebSoftwareBundle;
import eu.javaexperience.web.facility.SiteFacility.SiteObjectSettings;
import eu.javaexperience.web.facility.SiteFacilityTools;
import eu.javaexperience.web.service.hooks.ServiceProcessHooks;
import eu.javaexperience.web.template.TemplateManager;
import eu.jvx.js.lib.activity.StartablePageId;
import eu.javaexperience.rpc.RpcSessionTools;

public abstract class TeaVmTemplateWebsite extends TemplateWebsiteServer
{
	protected final List<String> lCss;
	protected final List<String> lPreJs;
	protected final List<String> lPostJs;
	protected final TeaVmUrlNode teavm;
	
	protected final String baseURL;
	
	protected final PageStorage<? extends StartablePageId> pageProvider;
	
	protected final boolean production;
	
	protected HashMap<PageId, String> pageMapping = new HashMap<>();
	
	protected RpcUrlNode<SimpleRpcRequest, SimpleRpcSession> api;
	protected GetBy1<SiteObjectSettings, SiteObjectSettings> configModifier;
	
	protected GetBy1<IOStreamServer, Object> serverSocketCreator;
	
	@Override
	protected SiteObjectSettings generateConfiguration()
	{
		if(null != configModifier)
		{
			return configModifier.getBy(super.generateConfiguration());
		}
		return super.generateConfiguration();
	}
	
	public void registerApis(RpcFacility<? extends RpcRequest>[] apis)
	{
		api = RpcUrlNode.createSimple
		(
			"site_api",
			"",
			apis
		);
	}
	
	public TeaVmTemplateWebsite(TeaVmTemplateWebsiteBuilder builder) throws IOException
	{
		this
		(
			builder.siteName,
			builder.production,
			builder.pageProvider,
			builder.cls,
			builder.sws,
			builder.apis,
			null != builder.tmpDir?builder.tmpDir:"/tmp/"+builder.siteName+"/",
			builder.scriptFile,
			builder.baseUrl,
			builder.siteScriptUrlProcessor,
			builder.configModifier,
			builder.hookModifier,
			builder.serverSocketCreator
		);
	}
	
	public TeaVmTemplateWebsite
	(
		String siteName,
		boolean production,
		PageStorage<? extends StartablePageId> pageProvider,
		Class<? extends PageStarter> cls,
		WebSoftwareBundle[] sws,
		RpcFacility<? extends RpcRequest>[] apis
	)
		throws IOException
	{
		this
		(
			siteName,
			production,
			pageProvider,
			cls,
			sws,
			apis,
			"/tmp/"+siteName+"/",
			new OsFile(new File("/tmp/"+siteName+"/final_out.js")),
			null,
			null,
			null,
			null,
			null
		);
	}
	
	public TeaVmTemplateWebsite
	(
		String siteName,
		boolean production,
		PageStorage<? extends StartablePageId> pageProvider,
		Class<? extends PageStarter> cls,
		WebSoftwareBundle[] sws,
		RpcFacility<? extends RpcRequest>[] apis,
		String tmpDir,
		final AbstractFile builtScript,
		String baseUrl,
		GetBy1<String, String> siteScriptUrlProcessor,
		GetBy1<SiteObjectSettings, SiteObjectSettings> configModifier,
		GetBy1<ServiceProcessHooks, ServiceProcessHooks> hookModifier,
		GetBy1<IOStreamServer, Object> serverSocketCreator
	)
		throws IOException
	{
		super(new DefaultDispatchStructure().getChains());
		
		this.configModifier = configModifier;
		this.serverSocketCreator = serverSocketCreator;
		this.hookModifier = hookModifier;
		
		getDispatcher().getChainByName("modify").addLink(new CorChainLink<Context>()
		{
			@Override
			public boolean dispatch(Context ctx)
			{
				if(usePathDispatch)
				{
					PreparedURL url = ctx.getRequestUrl();
					url.setPathPointer(url.getDomainElements());
				}
				return false;
			}
		});
		
		this.production = production;
		this.pageProvider = pageProvider;
		
		if(null == baseUrl)
		{
			baseUrl = "";
		}
		
		this.baseURL = baseUrl;
		
		this.siteScriptUrlProcessor = siteScriptUrlProcessor; 
		
		registerApis(apis);
		
		teavm = new TeaVmUrlNode(production, tmpDir, builtScript, cls);
		
		((CorDispatcher<Context>)getDispatcher()).getChainByName("static").addLink(new CorChainLink<Context>()
		{
			@Override
			public boolean dispatch(Context ctx)
			{
				PreparedURL url = ctx.getRequestUrl();
				if("sitescript.js".equals(url.getCurrentURLElement()))
				{
					url.jumpNextURLElement();
					teavm.getScriptNode().dispatch(ctx);
					return true;
				}
				
				return false;
			}
		});
		
		lCss = new ArrayList<>();
		lPreJs = new ArrayList<>();
		lPostJs = new ArrayList<>();
		
		for(WebSoftwareBundle sw:sws)
		{
			for(String s:sw.getCss())
			{
				lCss.add(s);
			}
			
			for(String s:sw.getPreJs())
			{
				lPreJs.add(s);
			}
			
			for(String s:sw.getPostJs())
			{
				lPostJs.add(s);
			}
		}
		
		lPostJs.add(getSitescriptUrl());
	}
	
	protected boolean usePathDispatch = false;
	
	public boolean isUsePathDispatch()
	{
		return usePathDispatch;
	}
	
	public void setUsePathDispatch(boolean pd)
	{
		this.usePathDispatch = pd;
	}
	
	public void useApiRequestMultiplexer()
	{
		registerApis(RpcTools.addMultiplexer(api.getApis(), (e)->new SimpleRpcRequest(RpcSessionTools.ensureGetCurrentRpcSession(), e)).toArray(JavaClassRpcCollector.emptyRpcFacilityArray));
	}
	
	public void refreshSiteScriptUrl()
	{
		for(int i=0;i<lPostJs.size();++i)
		{
			if(lPostJs.get(i).startsWith("/sitescript.js/"))
			{
				lPostJs.set(i, getSitescriptUrl());
				return;
			}
		}
	}
	
	public void useSitescript()
	{
		if(!production)
		{
			if(teavm.getScriptNode().checkModificationRefreshIfNeeded())
			{
				refreshSiteScriptUrl();
			}
		}
	}
	
	public RpcUrlNode getApiNode()
	{
		return api;
	}
	
	protected String getSitescriptUrl()
	{
		return teavm.getScriptNode().getSaltedUrl();
	}
	
	//TODO prefixed version
	public void registerPage(final String path, final PageId page)
	{
		pageMapping.put(page, path);
		((CorDispatcher<Context>)getDispatcher()).getChainByName("app").addLink(new CorChainLink<Context>()
		{
			@Override
			public boolean dispatch(Context ctx)
			{
				if(ctx.getRequestUrl().getPath().equals(path))
				{
					useSitescript();
					generateBootPage(ctx, lCss, lPreJs, lPostJs, page);
					return true;
				}
				return false;
			}
		});
	}
	
	public void registerDefaultPage(final PageId def)
	{
		pageMapping.put(def, null);
		((CorDispatcher<Context>)getDispatcher()).getChainByName("last").addLink(new CorChainLink<Context>()
		{
			@Override
			public boolean dispatch(Context ctx)
			{
				useSitescript();
				generateBootPage(ctx, lCss, lPreJs, lPostJs, def);
				return true;
			}
		});
	}
	
	@Override
	public CorDispatcher<Context> getDispatcher()
	{
		return (CorDispatcher<Context>) super.getDispatcher();
	}
	

	@Override
	public TemplateManager createDefaultTemplateManager()
	{
		return null;
	}
	
	public abstract void generateBootPage
	(
		Context ctx,
		List<String> css,
		List<String> preJs,
		List<String> postJs,
		PageId page
	);
	
	/*@Deprecated
	public static WebSoftwareBundle getApiAsBundle(final boolean preJs)
	{
		final List<String> ret = new OneShotList<>("/site_api/source?lang=javascript&async=true&teavm=true");
		return new WebSoftwareBundle()
		{
			@Override
			public List<String> getPreJs()
			{
				return preJs?ret:NullList.instance;
			}
			
			@Override
			public List<String> getPostJs()
			{
				return preJs?NullList.instance:ret;
			}
			
			@Override
			public List<String> getCss()
			{
				return NullList.instance;
			}
		};
	}*/

	public PageStorage<? extends StartablePageId> getPageStorage()
	{
		return pageProvider;
	}

	public HashMap<PageId, String> getPageMapping()
	{
		return pageMapping;
	}

	/**
	 * Make api available at /site_api/
	 * */
	public void useBuiltinApi()
	{
		getDispatcher().getChainByName("static").addLink(new CorChainLink<Context>()
		{
			@Override
			public boolean dispatch(Context ctx)
			{
				PreparedURL req = ctx.getRequestUrl();
				if(req.getPath().startsWith("/site_api/"))
				{
					req.setPathPointer(req.getDomainElements()+1);
					api.dispatch(ctx);
				}
				
				return false;
			}
		});
	}
	
	public static TeaVmTemplateWebsite createSimpleBootPage
	(
		final int port,
		String siteName,
		boolean production,
		PageStorage<? extends StartablePageId> pageProvider,
		Class<? extends PageStarter> cls,
		WebSoftwareBundle[] sws,
		RpcFacility<? extends RpcRequest>[] apis
	)
		throws IOException
	{
		TeaVmTemplateWebsiteBuilder builder = new TeaVmTemplateWebsiteBuilder(siteName, production, pageProvider, cls, sws, apis);
		builder.port = port;
		return createSimpleBootPage(builder);
	}

	protected GetBy1<String, String> siteScriptUrlProcessor;
	
	public static class WebsiteResources
	{
		public List<String> css;
		public List<String> preJs;
		public List<String> postJs;
	}
	
	public static void renderBootPage(Context ctx, WebsiteResources res, String page)
	{
		renderBootPage(ctx, res.css, res.preJs, res.postJs, page);
	}
	
	public static void renderBootPage(Context ctx, List<String> css, List<String> preJs, List<String> postJs, String page)
	{
		StringBuilder sb = new StringBuilder();
		renderBootPage(sb, css, preJs, postJs, page);
		SiteFacilityTools.finishWithElementSend(ctx, sb.toString());
	}
	
	public static void renderBootPage(StringBuilder sb, List<String> css, List<String> preJs, List<String> postJs, String page)
	{
		sb.append("<html>\n");
		sb.append("\t<head>\n");
		sb.append("\t\t<meta charset=\"utf-8\">\n");
		/*if("" != baseURL)
		{
			sb.append("\t\t<base href=\"");
			sb.append(baseURL);
			sb.append("\">\n");
		}*/
		
		if(null != css)
		{
			for(String c:css)
			{
				sb.append("\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
				sb.append(c);
				sb.append("\" />\n");
			}
		}
		
		if(null != preJs)
		{
			for(String c:preJs)
			{
				sb.append("\t\t<script type=\"text/javascript\" src=\"");
				sb.append(c);
				sb.append("\"></script>\n");
			}
		}
		sb.append("\t</head>\n");
		
		sb.append("\t<body>\n");
		
		if(null != page)
		{
			sb.append("\t\t<div class=\"page_identifier_data\" data-page_id=\"");
			sb.append(page);
			sb.append("\"></div>\n");
		}
		
		sb.append("");
		
		if(null != postJs)
		{
			for(String c:postJs)
			{
				sb.append("\t\t<script type=\"text/javascript\" src=\"");
				/*if(null != siteScriptUrlProcessor)
				{
					if(c.startsWith("/sitescript.js/"))
					{
						c = siteScriptUrlProcessor.getBy(c);
					}
				}*/
				sb.append(c);
				sb.append("\"></script>\n");
			}
		}
		
		sb.append("\t\t<script type=\"text/javascript\">main();</script>\n");
		
		
		sb.append("\t</body>\n");
		
		sb.append("</html>");
	}
	
	public static TeaVmTemplateWebsite createSimpleBootPage(final TeaVmTemplateWebsiteBuilder builder)
		throws IOException
	{
		return new TeaVmTemplateWebsite(builder)
		{
			@Override
			protected IOStreamServer getWebServerSocket() throws IOException
			{
				if(null != serverSocketCreator)
				{
					return serverSocketCreator.getBy(builder.port);
				}
				
				if(builder.port < 1 || builder.port > 65565)
				{
					//System.err.println("Warning: invalid port, therfore we don't bind: "+builder.port);
					return IOStreamFactory.NO_OP_SERVER_SOCKET;
				}
				else
				{
					return IOStreamFactory.fromServerSocket(new ServerSocket(builder.port));
				}
			}
			
			@Override
			public void generateBootPage(Context ctx, List<String> css, List<String> preJs, List<String> postJs, PageId page)
			{
				renderBootPage(ctx, css, preJs, postJs, null == page?null:page.getId());
			}
		};
	}
	
}
