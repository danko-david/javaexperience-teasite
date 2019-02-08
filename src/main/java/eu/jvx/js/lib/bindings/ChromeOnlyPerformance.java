package eu.jvx.js.lib.bindings;

import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;

public abstract class ChromeOnlyPerformance implements JSObject
{
	@JSProperty
	public abstract ChromeOnlyJsMemory getMemory();
}
