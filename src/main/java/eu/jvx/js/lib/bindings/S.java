package eu.jvx.js.lib.bindings;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Element;

public class S extends H
{
	public S(String elem)
	{
		super((HTMLElement) Window.current().getDocument().createElementNS("http://www.w3.org/2000/svg", elem));
	}
	
	public S(Element elem)
	{
		super((HTMLElement)elem);
	}
}
