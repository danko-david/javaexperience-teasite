package eu.jvx.js.lib.activity;

public interface EnumStartablePageId extends StartablePageId
{
	public default String url()
	{
		return "/"+name().toLowerCase();
	}
	
	public default String getId()
	{
		return name();
	}
	
	public String name();
}
