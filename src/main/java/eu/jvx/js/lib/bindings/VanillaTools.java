package eu.jvx.js.lib.bindings;

import java.util.Collection;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSIndexer;
import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;
import org.teavm.jso.browser.Window;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLInputElement;
import org.teavm.jso.dom.html.HTMLSelectElement;
import org.teavm.jso.dom.xml.Element;
import org.teavm.jso.dom.xml.Node;
import org.teavm.jso.dom.xml.NodeList;

import eu.javaexperience.reflect.CastTo;
import eu.javaexperience.text.StringTools;
import eu.jvx.js.interfaces.StyleClassManager;
import eu.jvx.js.lib.NativeJsSupport;
import eu.jvx.js.lib.resource.FrontendResourceTools;
import eu.jvx.js.lib.teavm.NativeJsSupportTeaVM.Direct;
import eu.jvx.js.lib.ui.style.CssStyleSheet;

public class VanillaTools
{
	public static HTMLElement inlineCreateElement(String nodeName, String... attrs)
	{
		HTMLElement ret = Window.current().getDocument().createElement(nodeName);
		stringAttrs(ret, attrs);
		return ret;
	}
	
	public static HTMLElement stringAttrs(HTMLElement ret, String... attrs)
	{
		for(int i = 0;i<attrs.length;i+=2)
		{
			String key = attrs[i];
			String value = attrs[i+1];
			if(null != key && null != value)
			{
				stringAttrs(ret, key, value);
			}
		}
		
		return ret;
	}
	
	public static HTMLElement stringAttrs(HTMLElement ret, String key, String value)
	{
		if(null == key || null == value)
		{
			return ret;
		}
		
		if("#text".equals(key))
		{
			NativeJsSupport.getSupport().setProp(ret, "innerText", value);
		}
		else if("#html".equals(key))
		{
			ret.setInnerHTML(value);
		}
		else if("style".equals(key))
		{
			ret.getStyle().setCssText(value);
		}
		else
		{
			ret.setAttribute(key, value);
		}
		
		return ret;
	}

	public static void setContent(H vn, String val)
	{
		setContent(vn.getHtml(), val);
	}
	
	public static void setContent(HTMLElement vn, String val)
	{
		switch(vn.getNodeName())
		{
			case "SELECT":
			
				//select option
				JSArray<HTMLElement> opts = VanillaTools.getChildren(vn);
				for(int i=0;i<opts.getLength();++i)
				{
					HTMLElement o = opts.get(i);
					if(null != val && val.equals(o.getAttribute("value")))
					{
						o.setAttribute("selected", "selected");
					}
					else
					{
						o.removeAttribute("selected");
					}
				}
				break;
			
			case "OPTION":
			case "INPUT":
				if("checkbox".equals(vn.getAttribute("type")) || "radio".equals(vn.getAttribute("type")))
				{
					if(Boolean.TRUE == CastTo.Boolean.cast(val))
					{
						vn.setAttribute("checked", "checked");
					}
					else
					{
						vn.removeAttribute("checked");
					}
					return;
				}
				
				((HTMLInputElement)vn).setValue(val);
			
				break;
				
			case "TEXTAREA":
			default:
				vn.setInnerHTML(val);
			break;
		}
	}
	
	public static HTMLElement appendChilds(HTMLElement subject, HTMLElement... elems)
	{
		for(int i=0;i<elems.length;++i)
		{
			HTMLElement el = elems[i];
			if(null != el)
			{
				subject.appendChild(el);
			}
		}

		return subject;
	}
	
	public static String getContent(H vn)
	{
		return getContent(vn.getHtml());
	}
	
	public static String getContent(HTMLElement vn)
	{
		//input: text, checkbox, radio
		//option
		//textarea:
		//etc
		
		switch(vn.getNodeName())
		{
			case "SELECT":
				HTMLSelectElement sel = ((HTMLSelectElement)vn);
				int index = sel.getSelectedIndex();
				if(index < 0)
				{
					return null;
				}
				return getContent(((HTMLElement)sel.getOptions().get(index)));
			
			case "OPTION":
			case "TEXTAREA":
			case "INPUT":
			if("checkbox".equals(vn.getAttribute("type")) || "radio".equals(vn.getAttribute("type")))
			{
				return ((HTMLInputElement)vn).isChecked()?"true":"false";
			}
			
			return ((HTMLInputElement)vn).getValue();
		}
		
		return vn.getInnerHTML();
	}
	
	public static int index(HTMLElement node)
	{
		Node p = node.getParentNode();
		if(null != p)
		{
			JSArray<HTMLElement> children = VanillaTools.getChildren((HTMLElement) p);
			int num = 0;
			for(int i=0; i<children.getLength(); ++i)
			{
				Node c = children.get(i);
				if(node == c)
				{
					return num;
				}
				
				if(c.getNodeType() == 1)
				{
					++num;
				}
			}
		}
		return -1;
	}
	
	public static void remove(Element elem)
	{
		Node parent = elem.getParentNode();
		if(null != parent)
		{
			parent.removeChild(elem);
		}
	}

	public static HTMLDocument getDom()
	{
		return Window.current().getDocument();
	}
	
	protected static abstract class Matches implements JSObject
	{
		@JSBody(params = {"selector"}, script = "return this.matches(selector);")
		public abstract boolean matches(String selector);
	}
	
	public static boolean is(HTMLElement elem, String selector)
	{
		return ((Matches)elem).matches(selector);
	}
	
	public static HTMLElement whereParent(HTMLElement elem, String selector)
	{
		elem = (HTMLElement) elem.getParentNode();
		//console.log(["where_parent start: ",elem]);
		while(null != elem)
		{
			if(is(elem, selector))
			{
				return elem;
			}
			elem = (HTMLElement) elem.getParentNode();
		}
		
		return null;
	}
	
	public static HTMLElement whereParentOrThis(HTMLElement elem, String selector)
	{
		while(null != elem)
		{
			if(is(elem, selector))
			{
				return elem;
			}
			elem = (HTMLElement) elem.getParentNode();
		}
		
		return null;
	}
	
	public static HTMLElement getNeightbor(HTMLElement from, String borderSelector, String targetSelector)
	{
		HTMLElement p = whereParent(from, borderSelector);
		if(null == p)
		{
			return p;
		}
		
		return p.querySelector(targetSelector);
	}
	
	public abstract static class EtcNodeSupport implements JSObject
	{
		@JSProperty
		public abstract String getTagName();
		
		@JSProperty
		public abstract void setInnerText(String content);
		
		@JSProperty
		public abstract String getInnerText();
		
		public abstract void select();
		
		public abstract void execCommand(String cmd);
		
		@JSProperty
		public abstract JSArray<HTMLElement> getChildren();
		
		@JSProperty
		public abstract int getSelectionEnd();
		
		@JSProperty
		public abstract int getSelectionStart();

		@JSProperty
		public abstract HTMLElement getActiveElement();
		
		@JSProperty
		public abstract JSArray<HTMLElement> getChildNodes();

		@JSProperty
		public abstract CssStyleSheet getSheet();
		
		@JSProperty
		public abstract JSObject getDetail();

		public abstract void insertBefore(HTMLElement add, HTMLElement relativeTo);
	}
	
	public static void copyToClipboard(String txt)
	{
		HTMLDocument document = Window.current().getDocument();
		HTMLElement elem = inlineCreateElement("textarea");
		((EtcNodeSupport)elem).setInnerText(txt);
		document.getBody().appendChild(elem);
		((EtcNodeSupport)(JSObject)elem).select();
		((EtcNodeSupport)(JSObject)document).execCommand("copy");
		remove(elem);
	}

	public static HTMLElement alterTag(HTMLElement f_ac, String string) {
		/*Node.prototype.alterTag = function(dst)
				{
					var tar = document.createElement(dst);
					
					var prot = Object.getPrototypeOf(this);
					
					tar.classList = this.classList;
					tar.dataset = this.dataset;
					tar.setContent(this.getContent());
					
					this.parentNode.replaceChild(tar, this);
					
					return tar;
				}
		*/
		return null;
	}
	
	public static HTMLElement whereParentWithBoundary(HTMLElement start, String selector, String boundary)
	{
		HTMLElement elem = (HTMLElement) start.getParentNode();
		//console.log(["where_parent start: ",elem]);
		while(null != elem)
		{
			if(is(elem, selector))
			{
				return elem;
			}
			
			if(is(elem, boundary))
			{
				return null;
			}
			
			elem = (HTMLElement) elem.getParentNode();
		}
		
		return null;
	}

	public static void setInnerText(HTMLElement elem, String content)
	{
		((EtcNodeSupport)(JSObject)elem).setInnerText(content);
	}
	
	public abstract static class ClassList implements JSObject, StyleClassManager
	{
		public abstract boolean contains(String cls);
		public abstract void add(String cls);
		public abstract void remove(String cls);
	}
	
	public static ClassList getClassList(HTMLElement elem)
	{
		return (ClassList) NativeJsSupport.getSupport().getProp(elem, "classList");
	}

	public static int getChildElementCount(HTMLElement elem)
	{
		JSArray<HTMLElement> nodes = VanillaTools.getChildren(elem);
		int count = 0;
		for(int i=0;i<nodes.getLength();++i)
		{
			if(1 == nodes.get(i).getNodeType())
			{
				++count;
			}
		}
         
		return count;
	}
	
	public static JSArray<HTMLElement> getChildren(HTMLElement elem)
	{
		return ((EtcNodeSupport)elem).getChildren();
	}
	
	public static JSArray<HTMLElement> getChildNodes(HTMLElement elem)
	{
		return ((EtcNodeSupport)elem).getChildNodes();
	}
	
	public static HTMLElement parseHtml(String txt)
	{
		HTMLElement wrapper = inlineCreateElement("div");
		wrapper.setInnerHTML(txt);
		return getChildren(wrapper).get(0);
	}
	
	public static String getInnerText(HTMLElement elem)
	{
		return ((EtcNodeSupport)(JSObject)elem).getInnerText();
	}
	
	
	public static int getSelectionStart(HTMLElement elem)
	{
		return ((EtcNodeSupport)(JSObject)elem).getSelectionStart();
	}
	
	public static int getSelectionEnd(HTMLElement elem)
	{
		return ((EtcNodeSupport)(JSObject)elem).getSelectionEnd();
	}
	
	public static void changeClass(HTMLElement elem, String c1, String c2)
	{
		ClassList clist = getClassList(elem);
		boolean h1 = clist.contains(c1);
		boolean h2 = clist.contains(c2);
		
		//none one of both, initalize with the first class
		if(!h1 && ! h2)
		{
			clist.add(c1);
		}
		//have both, set initial with only the first remaining
		else if(h1 && h2)
		{
			clist.remove(c2);
		}
		else if(h1)
		{
			clist.remove(c1);
			clist.add(c2);
		}
		else
		{
			clist.remove(c2);
			clist.add(c1);
		}
	}

	public static void copyTo(NodeList<? extends HTMLElement> els, Collection<HTMLElement> dst)
	{
		for(int i=0;i<els.getLength();++i)
		{
			dst.add(els.get(i));
		}
	}

	public static HTMLElement getActiveElement()
	{
		return ((EtcNodeSupport) Window.current().getDocument()).getActiveElement();
	}

	protected static CssStyleSheet sheet;
	static
	{
		HTMLElement style = inlineCreateElement("style");
		style.appendChild(Window.current().getDocument().createTextNode(""));
		FrontendResourceTools.getOrCreateHead().appendChild(style);

		sheet = ((EtcNodeSupport)style).getSheet();
	}
	
	public static void addToHeader(HTMLElement elem)
	{
		FrontendResourceTools.getOrCreateHead().appendChild(elem);
	}
	
	public static void addCssRule(String rule)
	{
		sheet.insertRule(rule, sheet.getLength());
	}
	
	public static void addCssRules(String... rule)
	{
		for(String s:rule)
		{
			addCssRule(s);
		}
	}

	public static void bindListenerToArea
	(
		HTMLElement area,
		String eventType,
		final String targetElementIs,
		final EventListener<Event> action
	)
	{
		area.addEventListener(eventType, new EventListener<Event>()
		{
			@Override
			public void handleEvent(Event event)
			{
				if(is((HTMLElement) event.getTarget(), targetElementIs))
				{
					action.handleEvent(event);
				}
			}
		});
	}

	public static boolean isDetached(HTMLElement container)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public static interface DataSet extends JSObject
	{
		//@JSIndexer
		@JSBody(params = "key",script = "var ret = this[key]; if(undefined == ret) return null; return ret;")
		public String get(String key);
		
		@JSIndexer
		public void put(String key, String value);
	}
	
	public static DataSet getDataSet(HTMLElement root)
	{
		return (DataSet)((Direct)root).rawGet("dataset");
	}

	public static void insertBefore(HTMLElement relativeTo, HTMLElement add)
	{
		((EtcNodeSupport)relativeTo.getParentNode()).insertBefore(add, relativeTo);
	}

	public static void replaceTo(HTMLElement e, HTMLElement set)
	{
		if(e == set)
		{
			return;
		}
		
		HTMLElement p = (HTMLElement) e.getParentNode();
		p.replaceChild(set, e);
	}

	public static void removeAllChild(HTMLElement root)
	{
		Node ch;
		while(null != (ch = root.getFirstChild()))
		{
			root.removeChild(ch);
		}
	}

	public static boolean isHtmlElement(HTMLElement elem)
	{
		return 1 == elem.getNodeType();
	}

	
	
	public static String getDomCssReprez(HTMLElement elem)
	{
		String ret = ((EtcNodeSupport)elem).getTagName();
		ret = ret.toLowerCase();
		
		String id = elem.getAttribute("id");
		if(null != id)
		{
			ret = ret+"#"+id;
		}

		String cls = elem.getClassName();
		if(null != cls && cls.length() > 0)
		{
			ret = ret+"."+StringTools.replaceAllStrings(cls, " ", ".");
		}
		
		return ret;
	}
	
	public static String getDomPath(HTMLElement elem)
	{
		if(null == elem || elem == getDom())
		{
			return null;
		}
		
		String ret = getDomCssReprez(elem);
		
		HTMLElement par = (HTMLElement) elem.getParentNode();
		
		if(null != par)
		{
			String cc = getDomPath(par);
			if(null != cc)
			{
				return cc +" > "+ret;
			}
		}
		
		return ret;
	}

	public static HTMLElement toHtmlElement(Object o)
	{
		if(o instanceof HTMLElement)
		{
			return (HTMLElement) o;
		}
		else if(o instanceof H)
		{
			return ((H) o).getHtml();
		}
		
		return new H("span").attrs("#text", null == o?"":o.toString()).getHtml();
	}
}
