package eu.jvx.js.lib;

import org.teavm.jso.core.JSArray;
import org.teavm.jso.dom.html.HTMLElement;

public interface HtmlContainer extends HtmlNode, ImpersonalisedHtml
{
	public JSArray<? extends HTMLElement> getSubNodes();
}
