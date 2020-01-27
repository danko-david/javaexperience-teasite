package eu.jvx.js.lib.style;

import org.teavm.jso.dom.html.HTMLElement;

import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.ui.style.StyleContainer;

public class StyleCollection
{
	protected final StyleContainer container;
	public StyleCollection(StyleContainer sc)
	{
		this.container = sc;
	}
	
	
	public static StyleCollection createStyleDomCollection()
	{
		StyleContainer styles = new StyleContainer();
		VanillaTools.addToHeader((HTMLElement) styles.getHtml());
		styles.refresh();
		return new StyleCollection(styles);
	}
	
	public Styler addStyler(Styler s)
	{
		container.getStyleSheet().insertRule("."+s.cssClass+"{"+s.rules+"}", container.getStyleSheet().getLength());
		String[] or = s.getOtherRules();
		if(null != or && 0 != or.length)
		{
			for(String r:or)
			{
				container.getStyleSheet().insertRule(r, container.getStyleSheet().getLength());
			}
		}
		
		return s;
	}
	
	public void addStyle(String style)
	{
		container.getStyleSheet().insertRule(style, container.getStyleSheet().getLength());
	}
}
