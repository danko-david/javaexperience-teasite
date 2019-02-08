package eu.jvx.js.tbs.ui;

import org.teavm.jso.JSObject;
import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.patterns.creational.builder.PublisherBuilder;
import eu.jvx.js.lib.bindings.H;
import static eu.jvx.js.lib.bindings.H.*;

public class TbsLayoutTools
{
	public static PublisherBuilder<HTMLElement, HTMLElement, Void> initWith(PublisherBuilder<HTMLElement, HTMLElement, String[]> root, final String... initWith)
	{
		PublisherBuilder<HTMLElement, HTMLElement, String[]> in = HtmlLayoutTools.ulLi();
		return new PublisherBuilder<HTMLElement, HTMLElement, Void>()
		{
			@Override
			public void publish(HTMLElement a)
			{
				in.publish(a);
			}

			@Override
			protected HTMLElement internalInitialize(Void init)
			{
				in.initialize(initWith);
				return in.getResult();
			}
		};
	}
	
	
	public static PublisherBuilder<HTMLElement, HTMLElement, Void> buildHorizontalPills()
	{
		return initWith(HtmlLayoutTools.ulLi(), "class", "nav nav-pills");
	}
	
	public static class SimpleFormRow
	{
		public final String id;
		public final String label;
		public final HTMLElement entry;
		
		public SimpleFormRow(String id, String label, HTMLElement entry)
		{
			this.id = id;
			this.label = label;
			this.entry = entry;
		}
	}
	
	public static PublisherBuilder<SimpleFormRow, HTMLElement, Void> buildForm()
	{
		return new PublisherBuilder<SimpleFormRow, HTMLElement, Void>()
		{
			@Override
			public void publish(SimpleFormRow a)
			{
				result.appendChild
				(
					H("div").attrs("class", "form-group").addChilds
					(
						H("label").attrs("for", a.id, "class", "col-sm-4 control-label", "#text", a.label),
						H("div").attrs("class", "col-sm-8 clearfix").addChilds(a.entry),
						H("div").attrs("class", "clearfix")
					).getHtml()
				);
			}

			@Override
			protected HTMLElement internalInitialize(Void init)
			{
				return H("div").attrs("class", "row").getHtml();
			}
		};
	}


	public static HTMLElement sm(Object... size_html)
	{
		H ret = new H("div").attrs("class", "row");
		for(int i=0;i<size_html.length;i+=2)
		{
			ret.addChilds
			(
				new H("div").attrs("class", "col-sm-"+size_html[i].toString()).addChilds
				(
					(JSObject)size_html[i+1]
				)
			);
		}
		
		return ret.getHtml();
	}
}
