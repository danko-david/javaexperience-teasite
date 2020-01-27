package eu.jvx.js.lib.ui.style;

import org.teavm.jso.browser.Window;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Element;

import eu.jvx.js.lib.ImpersonalisedHtml;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.bindings.VanillaTools.EtcNodeSupport;

public class StyleContainer implements ImpersonalisedHtml
{
	protected HTMLElement element;
	protected CssStyleSheet style;
	
	public StyleContainer()
	{
		element = VanillaTools.inlineCreateElement("style");
		element.appendChild(Window.current().getDocument().createTextNode(""));
	}
	
	public void refresh()
	{
		style = ((EtcNodeSupport)element).getSheet();
	}
	
	public CssStyleSheet getStyleSheet()
	{
		return style;
	}
	
	public JSArray<CssStyleRule> getRules()
	{
		return style.getRules();
	}
	
	public void addStyle(String rule)
	{
		style.insertRule(rule, style.getLength());
	}
	
	public void removeRules()
	{
		while(0 != style.getLength())
		{
			style.deleteRule(0);
		}
	}
	
	@Override
    public Object getImpersonator()
    {
	    return this;
    }

	@Override
	public Element getHtml()
	{
		return element;
	}
	
	public void remove()
	{
		VanillaTools.remove(element);
	}
}
