package eu.jvx.js.lib.bindings;

import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;

public abstract class ChromeOnlyJsMemory implements JSObject
{
	@JSProperty
	public abstract double getJsHeapSizeLimit();
	
	@JSProperty
	public abstract double getTotalJSHeapSize();
	
	@JSProperty
	public abstract double getUsedJSHeapSize();
	//public long jsHeapSizeLimit;
	//public long totalJSHeapSize;
	//public long usedJSHeapSize;
}
