package eu.jvx.js.lib.form;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.dom.html.HTMLElement;

public abstract class FormData implements JSObject
{
	@JSBody(script = "return new FormData();")
	public native static FormData create();
	
	@JSBody(params={"form"},script = "return new FormData(form);")
	public native static FormData create(HTMLElement form);
	
	public abstract void append(String key, String value);
	
	public abstract void append(String key, JSObject value, String name);
}
