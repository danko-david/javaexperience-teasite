package eu.jvx.js.tbs.ui;

import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.patterns.creational.builder.PublisherBuilder;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;

public class HtmlLayoutTools
{
	public static PublisherBuilder<HTMLElement, HTMLElement, String[]> ulLi()
	{
		return new PublisherBuilder<HTMLElement, HTMLElement, String[]>()
		{
			@Override
			public void publish(HTMLElement a)
			{
				result.appendChild(new H("li").addChilds(a).getHtml());
			}

			@Override
			protected HTMLElement internalInitialize(String[] init)
			{
				return VanillaTools.inlineCreateElement("ul", init);
			}
		};
	}
}
