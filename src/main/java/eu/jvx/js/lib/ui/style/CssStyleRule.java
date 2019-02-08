package eu.jvx.js.lib.ui.style;

import org.teavm.jso.JSObject;

public interface CssStyleRule extends JSObject
{
	public abstract String getCssText();
	public abstract void getCssText(String css);
	
	//public abstract CssMediaRule getParentRule();
	
	public abstract CssStyleSheet getParentStyleSheet();
	
	public abstract int getType();
	
	
}
