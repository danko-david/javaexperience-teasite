package eu.jvx.js.lib.ui.style;

import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;
import org.teavm.jso.core.JSArray;

public interface CssStyleSheet extends JSObject
{
	//az index a firefoxnak kell különben behal...
	public abstract int insertRule(String rule, int index);
	public abstract void deleteRule(int index);
	public abstract JSArray<CssStyleRule> getRules();
	
	@JSProperty
	public abstract int getLength();
}
