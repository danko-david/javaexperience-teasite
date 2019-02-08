package eu.jvx.js.lib.resource;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.NodeList;

import eu.javaexperience.exceptions.UnimplementedCaseException;
import eu.javaexperience.interfaces.simple.SimpleCall;
import eu.javaexperience.interfaces.simple.getBy.GetBy1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.jvx.js.lib.bindings.VanillaTools;

public class FrontendResourceTools
{
	protected static final GetBy1<HTMLElement, String> CREATE_CSS = new GetBy1<HTMLElement, String>()
	{
		@Override
		public HTMLElement getBy(String url)
		{
			return VanillaTools.inlineCreateElement
			(
				"link",
				"rel", "stylesheet",
				"href", url
			);
		}
	};

	protected static final GetBy1<HTMLElement, String> CREATE_JS = new GetBy1<HTMLElement, String>()
	{
		@Override
		public HTMLElement getBy(String url)
		{
			return VanillaTools.inlineCreateElement
			(
				"script",
				"type", "text/javascript",
				"src", url
			);
		}
	};

	public static void requireResource(FrontendResource resource, final SimpleCall onReady)
	{
		switch (resource.type)
		{
		case CSS:
			ensureHeadResource
			(
				"link[rel='stylesheet']",
				"href",
				resource.url,
				CREATE_CSS,
				onReady
			);
			return;
			
		case JAVASCRIPT:
			ensureHeadResource
			(
				"script[type='text/javascript']",
				"src",
				resource.url,
				CREATE_JS,
				onReady
			);
			return;
			
		default: throw new UnimplementedCaseException(resource.type);
		}
	}
	
	public static final SimplePublish2<SimplePublish1<FrontendResource>, FrontendResource> ENSURE_RESOURCE = new SimplePublish2<SimplePublish1<FrontendResource>, FrontendResource>()
	{
		@Override
		public void publish(final SimplePublish1<FrontendResource> a, final FrontendResource b)
		{
			requireResource(b, new SimpleCall()
			{
				@Override
				public void call()
				{
					a.publish(b);
				}
			});
		}
	};

	
	public static void ensureHeadResource
	(
		String selector,
		String attr,
		String url,
		GetBy1<HTMLElement, String> nodeCreator,
		final SimpleCall onReady
	)
	{
		NodeList<? extends HTMLElement> lnks = Window.current().getDocument().querySelectorAll(selector);
		for(int i=0;i<lnks.getLength();++i)
		{
			if(url.equals(lnks.get(i).getAttribute(attr)))
			{
				if(null != onReady)
				{
					onReady.call();
				}
				return;
			}
		}
		
		HTMLElement elem = nodeCreator.getBy(url);
		
		if(null != onReady)
		{
			elem.listenLoad(new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event arg0)
				{
					onReady.call();
				}
			});
		}
		
		getOrCreateHead().appendChild(elem);
	}
	
	public static HTMLElement getOrCreateHead()
	{
		HTMLElement head = Window.current().getDocument().getHead();
		if(null == head)
		{
			head = VanillaTools.inlineCreateElement("head");
			Window.current().getDocument().appendChild(head);
		}
		
		return head;
	}
	
}
