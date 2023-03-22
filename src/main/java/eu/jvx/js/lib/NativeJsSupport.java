package eu.jvx.js.lib;

public class NativeJsSupport
{
	public static NativeJs SUPPORT;
	
	public static NativeJs getSupport()
	{
		if(null == SUPPORT)
		{
			throw new RuntimeException("NativeJs suport not set (assing NativeJsSupport.SUPPORT) before booting application");
		}
		
		return SUPPORT;
	}
}
