package eu.jvx.js.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.teavm.jso.core.JSArray;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Element;
import org.teavm.jso.dom.xml.Node;
import org.teavm.jso.dom.xml.NodeList;

import eu.javaexperience.log.JavaExperienceLoggingFacility;
import eu.javaexperience.log.LogLevel;
import eu.javaexperience.log.Loggable;
import eu.javaexperience.log.Logger;
import eu.javaexperience.log.LoggingTools;
import eu.javaexperience.text.StringTools;
import eu.jvx.js.CustomEvent;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;

public class HtmlTools
{
	public static final Logger LOG = JavaExperienceLoggingFacility.getLogger(new Loggable("HtmlTools"));
	
	public static void setTextContentShowHide(HTMLElement node, boolean changeVisibility, String/*String, VanillaNode, */ content)
	{
		setContentReal(node, changeVisibility, 0, content);
	}
	
	public static void setHtmlContentShowHide(HTMLElement node, boolean changeVisibility, Node/*String, VanillaNode, */ content)
	{
		setContentReal(node, changeVisibility, 1, content);
	}
	
	public static void setImpersinalisedHtmlContentShowHide(HTMLElement node, boolean changeVisibility, ImpersonalisedHtml/*String, VanillaNode, */ content)
	{
		setContentReal(node, changeVisibility, 1, content);
	}
	
	protected static void setContentReal(HTMLElement node, boolean changeVisibility, int mode, Object/*String, VanillaNode, */ content)
	{
		//reset content anyway
		node.setInnerHTML("");
		
		if(null != content)
		{
			switch(mode)
			{
				case 0:
					node.setInnerHTML(content.toString());
					break;
				case 1:
					node.appendChild((Node) content);
					break;
				case 2:
					node.appendChild((Node) ((ImpersonalisedHtml)content).getHtml());
					break;
			};
		}
		
		if(changeVisibility)
		{
			if(null == content)
			{
				node.getStyle().setProperty("display", "none");
				//node.style.display = "none";
			}
			else
			{
				node.getStyle().setProperty("display", "");
				//node.style.display = "";
			}
		}
	}
	
	public static int childIndex(Element e)
	{
		if(null != e.getParentNode())
		{
			JSArray<HTMLElement> cn = VanillaTools.getChildren((HTMLElement) (e.getParentNode()));
			for(int i=0;i<cn.getLength();++i)
			{
				if(e == cn.get(i))
				{
					return i;
				}
			}
		}
		return -1;
	}

	public static List<Node> removeChilds(HTMLElement target)
	{
		List<Node> ret = new ArrayList<>();
		JSArray<HTMLElement> cn = VanillaTools.getChildren(target);
		for(int i=0;i<cn.getLength();++i)
		{
			Node e = cn.get(i);
			target.removeChild(e);
			ret.add(e);
		}
		
		return ret;
	}
	
	public static void addChilds(Element target, List<? extends Element> childs)
	{
		for(int i=0;i<childs.size();++i)
		{
			target.appendChild(childs.get(i));
		}
	}

	public static void replaceTo(HTMLElement from, HTMLElement to)
	{
		from.getParentNode().insertBefore(to, from);
		VanillaTools.remove(from);
	}

	public static void setChild(HTMLElement owner, HTMLElement onlyChild)
	{
		owner.setInnerHTML("");
		owner.appendChild(onlyChild);
	}

	public static boolean toggleVisiblity(HTMLElement el)
	{
		return setVisible(el, !isVisible(el));
	}
	
	public static boolean isVisible(HTMLElement el)
	{
		String d = el.getStyle().getPropertyValue("display");
		return !d.equals("none") && !d.equals("hidden");
	}
	
	public static boolean setVisible(HTMLElement el, boolean show)
	{
		if(show)
		{
			el.getStyle().setProperty("display", "");
		}
		else
		{
			el.getStyle().setProperty("display", "none");
		}
		
		return show;
	}
	
	public static void serializeInputs(Map<String, String> dst, HTMLElement in)
	{
		NodeList<? extends HTMLElement> els = in.querySelectorAll("input, textarea, select, output");
		for(int i=0;i < els.getLength();++i)
		{
			HTMLElement el = els.get(i);
			String name = el.getAttribute("name");
			if(!StringTools.isNullOrTrimEmpty(name))
			{
				boolean executed = el.dispatchEvent(CustomEvent.create("serialize"));
				String cont = VanillaTools.getContent(el);
				if(LOG.mayLog(LogLevel.DEBUG))
				{
					LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "dispatch serailize event, executed: %s, element: %s, name: %s, val: %s", executed, VanillaTools.getDomPath(el), name, cont);
				}
				dst.put(name, cont);
			}
		}
	}
	
	public static void setPageTitle(String title)
	{
		setPageTitle(VanillaTools.getDom(), title);
	}

	public static void setPageTitle(HTMLDocument document, String title)
	{
		H h = null;
		HTMLElement t = document.querySelector("title");
		if(null == t)
		{
			new H(document.getHead()).addChilds(h = new H("title"));
		}
		else
		{
			h = new H(t);
		}
		h.attrs("#text", title);
	}
}
