package eu.jvx.js.lib;

import org.teavm.jso.dom.xml.Element;

public class SimpleWrapperHtml implements WrapperHtml
{
	protected Element root;
	protected Element content;
	public SimpleWrapperHtml(Element root, Element content)
	{
		this.root = root;
		this.content = content;
	}
	
	@Override
	public Element getRoot()
	{
		return root;
	}

	@Override
	public Element getContent()
	{
		return content;
	}
}
