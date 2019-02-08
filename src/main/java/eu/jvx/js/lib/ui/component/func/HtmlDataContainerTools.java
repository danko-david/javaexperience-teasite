package eu.jvx.js.lib.ui.component.func;


import java.util.Date;

import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLInputElement;

import eu.javaexperience.text.Format;
import eu.javaexperience.text.Format.strtotime;
import eu.jvx.js.generic.LiveTranslation;
import eu.jvx.js.lib.LabeledItem;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.ui.component.input.SelectList;
import eu.jvx.js.lib.ui.component.input.SelectOption;

public class HtmlDataContainerTools
{
	public static HtmlDataContainer<String> tagInnerHtml(final String tag, final String... attrs)
	{
		return innerHtml(VanillaTools.inlineCreateElement(tag, attrs));
	}
	
	/**
	 * span. pre, div, textarea
	 * */
	public static HtmlDataContainer<String> innerHtml(final HTMLElement elem)
	{
		return new HtmlDataContainer<String>()
		{
			@Override
			public void setData(String data)
			{
				root.setInnerHTML(data);
			}
			
			@Override
			public String getData()
			{
				return root.getInnerHTML();
			}
			
			@Override
			protected HTMLElement construct()
			{
				return elem;
			}
		};
	}
	
	public static HtmlDataContainer<String> tagInnerText(final String tag, final String... attrs)
	{
		return innerText(VanillaTools.inlineCreateElement(tag, attrs));
	}
	
	
	public static HtmlDataContainer<String> innerText(final HTMLElement elem)
	{
		return new HtmlDataContainer<String>()
		{
			@Override
			public void setData(String data)
			{
				VanillaTools.setInnerText(root, data);
			}
			
			@Override
			public String getData()
			{
				return VanillaTools.getInnerText(root);
			}
			
			@Override
			protected HTMLElement construct()
			{
				return elem;
			}
		};
	}
	
	
	public static HtmlDataContainer<String> input(final String type, String... attrs)
	{
		return new HtmlDataContainer<String>()
		{
			@Override
			public void setData(String data)
			{
				((HTMLInputElement)root).setValue(data);
			}
			
			@Override
			public String getData()
			{
				return ((HTMLInputElement)root).getValue();
			}
			
			@Override
			protected HTMLElement construct()
			{
				HTMLElement el = VanillaTools.inlineCreateElement("input", "type", type);
				VanillaTools.stringAttrs(el, attrs);
				return el;
			}
		};
	}
	
	public static HtmlDataContainer<String> select(final LabeledItem[] lis, final String... attrs)
	{
		return new HtmlDataContainer<String>()
		{
			@Override
			public void setData(String data)
			{
				sl.setSelectedValue(data);
			}
			
			@Override
			public String getData()
			{
				SelectOption it = sl.getSelectedItem();
				if(null == it)
				{
					return null;
				}
				return it.getValue();
			}
			
			SelectList sl;
			
			@Override
			protected HTMLElement construct()
			{
				sl = new SelectList();
				for(LabeledItem li:lis)
				{
					sl.addOption(li.id, (HTMLElement) LiveTranslation.register(li.label, "span").getHtml());
				}
				
				VanillaTools.stringAttrs((HTMLElement) sl.getHtml(), attrs);
				
				return (HTMLElement) sl.getHtml();
			}
		};
	}
	
	public static HtmlDataContainer<String> combined(final HtmlDataContainer<String> master, final HtmlDataContainer<String> slave, String tag, String... attrs )
	{
		return new HtmlDataContainer<String>()
		{
			@Override
			public void setData(String data)
			{
				master.setData(data);
				slave.setData(data);
			}
			
			@Override
			public String getData()
			{
				return master.getData();
			}
			
			@Override
			public HTMLElement construct()
			{
				return new H(tag).attrs(attrs).addChilds(master, slave).getHtml();
			}
		};
	}

	public static HtmlDataContainer<String> wrapUnfunctional(final HTMLElement html)
	{
		return new HtmlDataContainer<String>()
		{
			@Override
			public void setData(String data)
			{
			}
			
			@Override
			public String getData()
			{
				return "";
			}
			
			@Override
			protected HTMLElement construct()
			{
				return html;
			}
		};
	}
	
	public static HtmlDataContainer<String> hiddenSpan()
	{
		final HTMLElement hidden = VanillaTools.inlineCreateElement("span", "style", "display:none");
		return new HtmlDataContainer<String>()
		{
			@Override
			public void setData(String data)
			{
				VanillaTools.setInnerText(hidden, data);
			}
			
			@Override
			public String getData()
			{
				return VanillaTools.getInnerText(hidden);
			}
			
			@Override
			protected HTMLElement construct()
			{
				return new H("span").addChilds(hidden).getHtml();
			}
		};
	}
	
	public static HtmlDataContainer<String> browserDatetimeLocal(HTMLInputElement e)
	{
		return new HtmlDataContainer<String>()
		{
			@Override
			protected HTMLElement construct()
			{
				return e;
			}

			@Override
			public String getData()
			{
				return ((HTMLInputElement) e).getValue();
			}

			@Override
			public void setData(String data)
			{
				Date d = strtotime.strtotime(data);
				if(null != d)
				{
					((HTMLInputElement) e).setValue(Format.UTC_SQL_TIMESTAMP_MS.format(d));
				}
				else
				{
					((HTMLInputElement) e).setValue("");
				}
			}
		};
	}

	public static HtmlDataContainer<String> wrapInput(final HTMLInputElement e)
	{
		return new HtmlDataContainer<String>()
		{
			@Override
			public void setData(String data)
			{
				((HTMLInputElement)root).setValue(data);
			}
			
			@Override
			public String getData()
			{
				return ((HTMLInputElement)root).getValue();
			}
			
			@Override
			protected HTMLElement construct()
			{
				return e;
			}
		};
	}
}
