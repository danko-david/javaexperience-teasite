package eu.jvx.js.lib;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.core.JSString;

public interface NativeJs
{
	public Object eval(String src);
	public Object eval(JSObject param, String src);
	public Object log(Object... src);
	public Object newInstanceOf(Object origin, Object... args);
	public Object getProp(Object obj, String prop);
	public void setProp(Object obj, String prop, Object value);
	public NativeObject newObject();
	public JSArray<JSString> keys(JSObject pass);
}
