package eu.javaexperience.teavm.templatesite;

import eu.javaexperience.file.AbstractFile;
import eu.javaexperience.interfaces.simple.getBy.GetBy1;
import eu.javaexperience.io.IOStreamServer;
import eu.javaexperience.rpc.RpcFacility;
import eu.javaexperience.rpc.RpcRequest;
import eu.javaexperience.teavm.templatesite.common.PageStorage;
import eu.javaexperience.teavm.templatesite.frontend.PageStarter;
import eu.javaexperience.web.dom.build.WebSoftwareBundle;
import eu.javaexperience.web.facility.SiteFacility.SiteObjectSettings;
import eu.javaexperience.web.service.hooks.ServiceProcessHooks;
import eu.jvx.js.lib.activity.StartablePageId;

public class TeaVmTemplateWebsiteBuilder
{
	public String siteName;
	public boolean production = true;
	public PageStorage<? extends StartablePageId> pageProvider;
	public Class<? extends PageStarter> cls;
	public WebSoftwareBundle[] sws;
	public RpcFacility<? extends RpcRequest>[] apis;
	
	public String tmpDir;
	public int port;
	public AbstractFile scriptFile;
	public String baseUrl;
	
	public GetBy1<SiteObjectSettings, SiteObjectSettings> configModifier;
	
	public GetBy1<ServiceProcessHooks, ServiceProcessHooks> hookModifier;
	
	public TeaVmTemplateWebsiteBuilder
	(
		String siteName,
		boolean production,
		PageStorage<? extends StartablePageId> pageProvider,
		Class<? extends PageStarter> cls,
		WebSoftwareBundle[] sws,
		RpcFacility<? extends RpcRequest>[] apis
	)
	{
		this.siteName = siteName;
		this.production = production;
		this.pageProvider = pageProvider;
		this.cls = cls;
		this.sws = sws;
		this.apis = apis;
	}

	public TeaVmTemplateWebsiteBuilder setPort(int port)
    {
		this.port = port;
	    return this;
    }

	public TeaVmTemplateWebsiteBuilder setBase(String base)
	{
		baseUrl = base;
		return this;
	}
	
	public GetBy1<String, String> siteScriptUrlProcessor;
	public GetBy1<IOStreamServer, Object> serverSocketCreator;
	
	public TeaVmTemplateWebsiteBuilder setSiteScriptUrlProcessor(GetBy1<String, String> proc)
	{
		this.siteScriptUrlProcessor = proc;
		return this;
	}

	public TeaVmTemplateWebsiteBuilder withConfigWrapper(GetBy1<SiteObjectSettings, SiteObjectSettings> modifier)
	{
		this.configModifier = modifier;
		return this;
	}
	
	public TeaVmTemplateWebsiteBuilder withHookModifier(GetBy1<ServiceProcessHooks, ServiceProcessHooks> modifier)
	{
		this.hookModifier = modifier;
		return this;
	}
}
