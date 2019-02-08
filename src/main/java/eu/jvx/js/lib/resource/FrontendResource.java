package eu.jvx.js.lib.resource;

public class FrontendResource
{
	public final FrontendResourceType type;
	public final String url;
	
	public FrontendResource(FrontendResourceType type, String url)
	{
		this.type = type;
		this.url = url;
	}
}