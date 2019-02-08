package eu.jvx.js.ui;

import java.util.ArrayList;
import java.util.List;

import org.teavm.jso.browser.TimerHandler;
import org.teavm.jso.browser.Window;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.dom.css.CSSStyleDeclaration;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.EventTarget;
import org.teavm.jso.dom.events.KeyboardEvent;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLInputElement;
import org.teavm.jso.dom.html.TextRectangle;
import org.teavm.jso.dom.xml.NodeList;

import eu.javaexperience.interfaces.simple.getBy.GetBy2;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.javaexperience.interfaces.simple.publish.SimplePublish3;
import eu.javaexperience.text.StringTools;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.bindings.VanillaTools.ClassList;
import eu.jvx.js.lib.ui.FrontendTools;
import static eu.jvx.js.lib.bindings.VanillaTools.*;

/**
 * TODO select item on click
 * */
//port of https://github.com/Pixabay/JavaScript-autoComplete
public class Autocomplete<T>
{
	static
	{
		VanillaTools.addCssRule
		(
			".autocomplete-suggestions {\n" + 
			"		    text-align: left; cursor: default; border: 1px solid #ccc; border-top: 0; background: #fff; box-shadow: -1px 1px 3px rgba(0,0,0,.1);\n" + 
			"\n" + 
			"		    position: absolute; display: none; z-index: 9999; max-height: 254px; overflow: hidden; overflow-y: auto; box-sizing: border-box;\n" + 
			"		}\n" + 
			""
		);
		VanillaTools.addCssRule(".autocomplete-suggestion { position: relative; padding: 0 .6em; line-height: 23px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; font-size: 1.02em; color: #333; }");
		VanillaTools.addCssRule(".autocomplete-suggestion b { font-weight: normal; color: #1f8dd6; }");
		VanillaTools.addCssRule(".autocomplete-suggestion.selected { background: #f0f0f0; }");
	}
	
	public static class AutocompleteSettings<T>
	{
		public HTMLInputElement target;
		public String selector = null;
		
		public int minChars = 3;

		public int delay = 150;
		public int offsetLeft = 0;
		public int offsetTop = 1;
		public String menuClass = "";
		
		public SimplePublish3<Event, String, HTMLElement> onSelect;

		public SimplePublish2<String, SimplePublish1<List<T>>> source;
		
		public GetBy2<HTMLElement, T, String> renderItem = (GetBy2)DEFAULT_ITEM_RENDERER;
		
		public AutocompleteSettings
		(
			String selector,
			SimplePublish2<String, SimplePublish1<List<T>>> source,
			SimplePublish3<Event, String, HTMLElement> onSelect
		)
		{
			this.selector = selector;
			this.source = source;
			this.onSelect = onSelect;
		}
		
		public AutocompleteSettings
		(
			HTMLElement elem,
			SimplePublish2<String, SimplePublish1<List<T>>> source,
			SimplePublish3<Event, String, HTMLElement> onSelect
		)
		{
			this.target = (HTMLInputElement) elem;
			this.source = source;
			this.onSelect = onSelect;
		}
		
		public static final GetBy2<HTMLElement, Object, String> DEFAULT_ITEM_RENDERER = new GetBy2<HTMLElement, Object, String>()
		{
			public HTMLElement getBy(Object item, String search)
			{
				/*				
				// escape special characters
				search = search.replace(/[-\/\\^$*+?.()|[\]{}]/g, "\\$&");
				var re = new RegExp("(" + search.split(" ").join("|") + ")", "gi");
				return "<div class="autocomplete-suggestion" data-val="" + item + "">" + item.replace(re, "<b>$1</b>") + "</div>";
				*/
				String i = String.valueOf(item);
				return new H("div").attrs("class", "autocomplete-suggestion", "data-val", i, "#text", i).getHtml();//"<div class=\"autocomplete-suggestion\" data-val=\""+item+"\"><b>"+item+"</b></div>";
			}
		};
	}
	
	protected AutocompleteSettings<T> options;
	protected List<AutocompleteElement> elems;
	
	public Autocomplete(AutocompleteSettings<T> settings)
	{
		this.options = settings;
		initalize();
	}
	
	protected void initalize()
	{
		if(null == elems)
		{
			ArrayList<HTMLElement> es = new ArrayList<>();
			if(null != options.target)
			{
				es.add(options.target);
			}
			
			if(null != options.selector)
			{
				NodeList<? extends HTMLElement> els = Window.current().getDocument().querySelectorAll(options.selector);
				VanillaTools.copyTo(els, es);
			}
			
			ArrayList<AutocompleteElement> aes = new ArrayList<>();
			for(HTMLElement e:es)
			{
				aes.add(new AutocompleteElement(e));
			}
			
			elems = aes;
		}
	}
	
	protected class AutocompleteElement
	{
		protected HTMLInputElement targetElement;
		
		protected HTMLElement sc;
		protected String autocompleteAttr;
		protected String last_val;
		
		protected EventListener<Event> updateSC;
		protected EventListener<Event> blurHandler;
		protected EventListener<KeyboardEvent> keydownHandler;
		protected EventListener<KeyboardEvent> keyupHandler;
		
		protected EventListener<Event> focusHandler;
		
		protected int timer;
		
		public AutocompleteElement(HTMLElement target)
		{
			this.targetElement = (HTMLInputElement) target;
			initalize();
		}
		
		protected void initalize()
		{
			final HTMLDocument document = Window.current().getDocument();
			final HTMLElement body = Window.current().getDocument().getBody();
			final Window window = Window.current();
			
			// create suggestions container "sc"
			sc = VanillaTools.inlineCreateElement("div");
			sc.setClassName("autocomplete-suggestions "+options.menuClass);

			autocompleteAttr = targetElement.getAttribute("autocomplete");
			targetElement.setAttribute("autocomplete", "off");
			last_val = "";
			
			updateSC = new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event resize)
				{
					TextRectangle rect = targetElement.getBoundingClientRect();
					CSSStyleDeclaration style = sc.getStyle();
					int pox = -body.getBoundingClientRect().getLeft();
					int poy = -body.getBoundingClientRect().getTop();
					
					style.setProperty("left", (rect.getLeft() + pox + options.offsetLeft) + "px");
					style.setProperty("top", (rect.getBottom() + poy + options.offsetTop) + "px");
					//style.setProperty("width", (rect.getRight() - rect.getLeft()) + "px"); // outerWidth
					//if(null != resize)
					{
						style.setProperty("display", "block");
						if(null == style.getPropertyValue("maxHeight"))
						{
							style.setProperty("maxHeight", "250px");
						}
						
						/*if(!targetElement.sc.suggestionHeight)
						{
							targetElement.sc.suggestionHeight = targetElement.sc.querySelector(".autocomplete-suggestion").offsetHeight;
						}*/
						
						if(null != style.getPropertyValue("suggestionHeight"))
						{
							HTMLElement next = sc.querySelector(".autocomplete-suggestion.selected");
							if(null == next)
							{
								style.setProperty("scrollTop", "0");
							}
							else
							{
								int scrTop = sc.getScrollTop();
								int selTop = next.getBoundingClientRect().getTop() - sc.getBoundingClientRect().getTop();
								
								/*if (selTop + targetElement.sc.suggestionHeight - sc.getMaxHeight() > 0)
								{
									sc.setScrollTop(selTop + targetElement.sc.suggestionHeight + scrTop - targetElement.sc.maxHeight;
								}
								else*/if (selTop < 0)
								{
									sc.setScrollTop(selTop + scrTop);
								}
							}
						}
					}
				}
			};
			
			addEvent(window, "resize", updateSC);
			body.appendChild(sc);
			
			live("autocomplete-suggestion", "mouseleave", new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event arg0)
				{
					HTMLElement sel = sc.querySelector(".autocomplete-suggestion.selected");
					if(null != sel)
					{
						Window.setTimeout(new TimerHandler()
						{
							@Override
							public void onTimer()
							{
								getClassList(sel).remove("selected");
							}
						}, 20);
					}
				}
			}, sc);
			
			live("autocomplete-suggestion", "mouseover", new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event arg0)
				{
					HTMLElement sel = sc.querySelector(".autocomplete-suggestion.selected");
					if(null != sel)
					{
						getClassList(sel).remove("selected");
					}
					HTMLElement elem = VanillaTools.whereParentOrThis(arg0.getTarget().cast(), ".autocomplete-suggestion");
					if(null != elem)
					{
						VanillaTools.getClassList(elem).add("selected");
					}
				}
			}, sc);

			live("autocomplete-suggestion", "mousedown", new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event e)
				{
					HTMLElement elem = VanillaTools.whereParentOrThis(e.getTarget().cast(), ".autocomplete-suggestion");
					if(null != elem)// else outside click
					{
						String v = elem.getAttribute("data-val");
						targetElement.setValue(v);
						options.onSelect.publish(e, v, targetElement);
						sc.getStyle().setProperty("display", "none");
					}
				}
			}, sc);
			
			blurHandler = new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event arg0)
				{
					HTMLElement over_sb = null;
					try
					{
						over_sb = document.querySelector(".autocomplete-suggestions");
					}
					catch(Exception e)
					{
						over_sb = null;
					}
					
					System.out.println("blur: "+(null == over_sb?null:VanillaTools.getDomPath(over_sb)));
					
					if(null != over_sb)
					{
						last_val = targetElement.getValue();
						sc.getStyle().setProperty("display", "none");
						Window.setTimeout(new TimerHandler()
						{
							@Override
							public void onTimer()
							{
								sc.getStyle().setProperty("display", "none");
							}
						}, 350);
					}
					/*else if(targetElement != VanillaTools.getActiveElement())
					{
						Window.setTimeout(new TimerHandler()
						{
							@Override
							public void onTimer()
							{
								targetElement.focus();
							}
						}, 20);
					}*/
				}
			};
					
			addEvent(targetElement, "blur", blurHandler);

			final SimplePublish1<List<T>> suggest = new SimplePublish1<List<T>>()
			{
				@Override
				public void publish(List<T> data)
				{
					String val = targetElement.getValue();
					if(data.size() > 0 && val.length() >= options.minChars)
					{
						sc.clear();
						//StringBuilder sb = new StringBuilder();
						for(int i=0;i<data.size();i++)
						{
							sc.appendChild(options.renderItem.getBy(data.get(i), val));
						}
						//sc.setInnerHTML(sb.toString());
						
						updateSC.handleEvent(null);
					}
					else
					{
						sc.getStyle().setProperty("display", "none");
					}
				}
			};
			
			keydownHandler = new EventListener<KeyboardEvent>()
			{
				@Override
				public void handleEvent(KeyboardEvent e)
				{
					int key = e.getKeyCode();
					if((key == 40 || key == 38) && !StringTools.isNullOrEmpty(sc.getInnerHTML()))
					{
						HTMLElement next = null;
						HTMLElement sel = sc.querySelector(".autocomplete-suggestion.selected");
						if(null == sel)
						{
							JSArray<HTMLElement> lst = getChildNodes(sc);
							next = (key == 40) ? sc.querySelector(".autocomplete-suggestion") :lst.get(lst.getLength() - 1); // first : last
							getClassList(next).add("selected");
							targetElement.setValue(next.getAttribute("data-val"));
						}
						else
						{
							next = ((HTMLElement) ((key == 40) ? sel.getNextSibling() : sel.getPreviousSibling()));
							if(null != next)
							{
								getClassList(sel).remove("selected");
								getClassList(next).add("selected");
								targetElement.setValue(next.getAttribute("data-val"));
							}
							else
							{
								getClassList(sel).remove("selected");
								targetElement.setValue(last_val);
								next = null;
							}
						}
						updateSC.handleEvent(null);
						return;
					}
					// esc
					else if(key == 27)
					{
						targetElement.setValue(last_val);
						sc.getStyle().setProperty("display", "none");
					}
					// enter
					else if(key == 13 || key == 9)
					{
						HTMLElement sel = sc.querySelector(".autocomplete-suggestion.selected");
						if(null != sel && !sc.getStyle().getPropertyValue("display").equals("none"))
						{
							options.onSelect.publish(e, sel.getAttribute("data-val"), sel);
							Window.setTimeout(new TimerHandler()
							{
								@Override
								public void onTimer()
								{
									sc.getStyle().setProperty("display", "none"); 
								}
							}, 20);
						}
					}
				}
			};
			
			addEvent(targetElement, "keydown", keydownHandler);

			keyupHandler = new EventListener<KeyboardEvent>()
			{
				@Override
				public void handleEvent(KeyboardEvent event)
				{
					int key = event.getKeyCode();
					if((key < 35 || key > 40) && key != 13 && key != 9 && key != 27)
					{
						String val = targetElement.getValue();
						if(val.length() >= options.minChars)
						{
							if(!val.equals(last_val))
							{
								last_val = val;
								Window.clearTimeout(timer);
								
								timer = Window.setTimeout(new TimerHandler()
								{
									@Override
									public void onTimer()
									{
										//TODO 
										new Thread()
										{
											public void run()
											{
												options.source.publish(val, suggest);											
											};
										}.start();
									}
								}, options.delay);
								
							}
						}
						else
						{
							last_val = val;
							sc.getStyle().setProperty("display", "none");
						}
					}
				}
			};
					
			addEvent(targetElement, "keyup", keyupHandler);

			focusHandler = new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event e)
				{
					last_val = "\n";
					keyupHandler.handleEvent((KeyboardEvent) e);					
				}
			};
					
			if(options.minChars > 0)
			{
				addEvent(targetElement, "focus", focusHandler);
			}
		}
		
		public void destroy()
		{
			Window window = Window.current();
			
			//TODO
			/*removeEvent(window, "resize", updateSC);
			removeEvent(targetElement, "blur", blurHandler);
			removeEvent(targetElement, "focus", focusHandler);
			removeEvent(targetElement, "keydown", keydownHandler);
			removeEvent(targetElement, "keyup", keyupHandler);*/
			if(null != autocompleteAttr)
			{
				targetElement.setAttribute("autocomplete", autocompleteAttr);
			}
			else
			{
				targetElement.removeAttribute("autocomplete");
			}
			
			window.getDocument().getBody().removeChild(sc);
		}
	}
	
	protected static void addEvent(EventTarget el, String type, EventListener<? extends Event> handler)
	{
		el.addEventListener(type, FrontendTools.wrapProcessEventWithThread(handler), true);
	}
	
	/*protected static void removeEvent(EventTarget el, String type, EventListener<? extends Event> handler)
	{
		el.removeEventListener(type, handler, true);
	}*/
	
	protected static void live(String elClass, String event, EventListener<Event> cb, EventTarget context)
	{
		if(null == context)
		{
			context = Window.current().getDocument().getBody();
		}
		
		addEvent(context, event, new EventListener<Event>()
		{
			@Override
			public void handleEvent(Event e)
			{
				boolean found = false;
				HTMLElement el = (HTMLElement) e.getTarget();
				HTMLDocument doc = Window.current().getDocument();
				while(null != el && el != doc)
				{
					found = hasClass(el, elClass);
					if(found)
					{
						cb.handleEvent(e);
						return;
					}
					el = (HTMLElement) el.getParentNode();
				}
			}
		});
	}
	
	protected static boolean hasClass(HTMLElement elem, String className)
	{
		if(null == elem)
		{
			return false;
		}
		
		ClassList cl = getClassList(elem);
		if(null == cl)
		{
			return false;
		}
		
		return cl.contains(className);
	}
	
	public void destroy()
	{
		for(AutocompleteElement elem:elems)
		{
			elem.destroy();
		}
	}

	/*(function(){
		if (typeof define === "function" && define.amd)
			define("autoComplete", function () { return autoComplete; });
		else if (typeof module !== "undefined" && module.exports)
			module.exports = autoComplete;
		else
			window.autoComplete = autoComplete;
	})();*/
}
