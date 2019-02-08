package eu.jvx.js.lib.bindings;

import org.teavm.jso.JSObject;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.MouseEvent;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Node;

import eu.jvx.js.lib.HtmlTools;
import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.ImpersonalisedHtml;
import eu.jvx.js.lib.style.StyleDecorator;
import eu.jvx.js.lib.style.StyleDecoratorSource;
import eu.jvx.js.lib.ui.FrontendTools;

public class H implements JSObject
{
	protected HTMLElement e;
	
	public H(String tag)
	{
		e = Window.current().getDocument().createElement(tag);
	}
	
	public H(HTMLElement elem)
	{
		e = elem;
	}

	public H attrs(String... args)
	{
		for(int i = 0;i<args.length;i+=2)
		{
			String key = args[i];
			String value = args[i+1];
			if(null != key)
			{
				VanillaTools.stringAttrs(e, key, value);
			}
		}
		
		return this;
	}
	
	public H addChilds(HTMLElement... els)
	{
		for(HTMLElement el:els)
		{
			if(null != el)
			{
				e.appendChild(el);
			}
		}
		
		return this;
	}
	
	public H addChilds(JSObject... els)
	{
		for(Object el:els)
		{
			if(null != el)
			{
				if(el instanceof H)
				{
					e.appendChild(((H)el).e);
				}
				else 
				{
					try
					{
						e.appendChild((Node)el);
					}
					catch(Throwable e){}
				}
			}
		}
		
		return this;
	}
	
	public H addChilds(ImpersonalisedHtml... els)
	{
		for(Object el:els)
		{
			e.appendChild(((ImpersonalisedHtml)el).getHtml());
		}
		
		return this;
	}
	
	
	
	public H addChilds(H... els)
	{
		for(H el:els)
		{
			if(null != el)
			{
				e.appendChild(el.e);
			}
		}
		
		return this;
	}
	
	public static H H(String elem)
	{
		return new H(elem);
	}
	
	public static H H(String elem, String... attrs)
	{
		return new H(elem).attrs(attrs);
	}
	
	public static H H(HTMLElement elem)
	{
		return new H(elem);
	}
	
	public HTMLElement getHtml()
	{
		return e;
	}

	public H addChilds(Iterable<HTMLElement> els)
	{
		for(HTMLElement el:els)
		{
			if(null != el)
			{
				e.appendChild(el);
			}
		}
		
		return this;
	}

	public H onClick(EventListener<MouseEvent> eventListener)
	{
		e.listenClick(FrontendTools.wrapProcessEventWithThread(eventListener));
		return this;
	}
	
	public H insertFirstChild(H el)
	{
		if(null != el)
		{
			insertFirstChild(el.getHtml());
		}
		return this;
	}

	public H insertFirstChild(HTMLElement el)
	{
		if(null == el)
		{
			return this;
		}
		
		if(e.hasChildNodes())
		{
			e.insertBefore(el, e.getFirstChild());
		}
		else
		{
			e.appendChild(el);
		}
		
		return this;
	}
	
	public H listen(String event, EventListener<Event> listener)
	{
		e.addEventListener(event, FrontendTools.wrapProcessEventWithThread(listener));
		return this;
	}
	
	public H on(String event, final String isSelector, final EventListener<Event> listener)
	{
		listen(event, new EventListener<Event>()
		{
			@Override
			public void handleEvent(Event arg0)
			{
				if(VanillaTools.is((HTMLElement) arg0.getTarget(), isSelector))
				{
					FrontendTools.processEventWithThread(listener, arg0);
				}
			}
		});
		return this;
	}
	
	public H on(String event, final EventListener<Event> listener)
	{
		listen(event, listener);
		return this;
	}
	
	public H style(StyleDecorator... dec)
	{
		for(StyleDecorator d:dec)
		{
			if(null != d)
			{
				d.setStyle(e, true);
			}
		}
		
		return this;
	}
	
	public H style(StyleDecoratorSource... dec)
	{
		for(StyleDecoratorSource d:dec)
		{
			if(null != d)
			{
				d.getDecorator().setStyle(e, true);
			}
		}
		
		return this;
	}

	public H setVisible(boolean b)
	{
		HtmlTools.setVisible(e, b);
		return this;
	}

	public H imp(ImpersonalisedHtml owner)
	{
		ImpTools.appendImp(e, owner);
		return this;
	}
	
	/*public H style(StyleClassSource... scs)
	{
		ClassList cl = VanillaTools.getClassList(e);
		for(StyleClassSource d:scs)
		{
			if(null != d)
			{
				cl.add(d.getCssClassName());
			}
		}
		
		return this;
	}*/
}
