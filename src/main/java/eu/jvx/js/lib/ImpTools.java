package eu.jvx.js.lib;

import java.util.ArrayList;
import java.util.List;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Element;
import org.teavm.jso.dom.xml.Node;
import org.teavm.jso.dom.xml.NodeList;

import eu.jvx.js.lib.bindings.VanillaTools;

public class ImpTools
{
	public static final String NODE_IMPERSONATOR = "NODE_IMPERSONATOR";
	
	public abstract static class ImpNode implements JSObject
	{
		@JSProperty
		public abstract JSObject getImp();
		
		@JSProperty
		public abstract void setImp(JSObject imp);
	}
	
	public abstract static class SImpNode implements JSObject
	{
		@JSBody(params ={"elem"}, script= "var ret= elem[\""+NODE_IMPERSONATOR+"\"]; if(undefined ===ret) return null; return ret;")
		public static native JSObject getImp(Element elem);
		
		//@JSProperty
		@JSBody(params ={"elem", "imp"}, script= "elem[\""+NODE_IMPERSONATOR+"\"] = imp;")
		public static native void setImp(Element elem, JSObject imp);
	}
	
	public static <T extends Element> T appendImp(T elem, ImpersonalisedHtml imp)
	{
		//NativeJsSupport.getSupport().setProp(elem, NODE_IMPERSONATOR, imp);
		//((Map<String, Object>) (Object) elem).$put(NODE_IMPERSONATOR, imp);
		//((ImpNode) elem).setImp((JSObject)imp);
		SImpNode.setImp(elem, (JSObject) imp);
		return elem;
	}

	public static <T extends ImpersonalisedHtml> T getImp(Element elem)
	{
		//return (T) NativeJsSupport.getSupport().getProp(elem, NODE_IMPERSONATOR);
		//return (T) ((Map<String, Object>) (Object) elem).$get(NODE_IMPERSONATOR);
		//return (T) ((ImpNode) elem).getImp();
		return SImpNode.getImp(elem).cast();
	}
	/*
	public static <T extends Element> T addModel(T elem, String key, Object model)
	{
		NativeJsSupport.getSupport().setProp(elem, key, model);
		return elem;
	}

	public static <T extends ImpersonalisedHtml> T getModel(Node elem, String name)
	{
		return (T) NativeJsSupport.getSupport().getProp(elem, name);
	}
	*/
	public static <T extends ImpersonalisedHtml> List<T> selectAllImp(String selector)
	{
		//Window.current().getDocument().getBody().querySelector
		NodeList<? extends HTMLElement> nodes = VanillaTools.getDom().querySelectorAll(selector);
		
		List<T> ret = new ArrayList<>();
		for(int i=0;i<nodes.getLength();++i)
		{
			T add = getImp(nodes.get(i));
			if(null != add)
			{
				ret.add(add);
			}
		}
		
		return ret;
	}
	
	public static <T extends ImpersonalisedHtml> List<T> selectAllImp(Class<T> cls, String selector)
	{
		//Window.current().getDocument().getBody().querySelector
		NodeList<? extends HTMLElement> nodes = VanillaTools.getDom().querySelectorAll(selector);
		
		List<T> ret = new ArrayList<>();
		for(int i=0;i<nodes.getLength();++i)
		{
			T add = getImp(nodes.get(i));
			if(null != add && add instanceof Object && cls.isAssignableFrom(add.getClass()))
			{
				ret.add(add);
			}
		}
		
		return ret;
	}
	
	public static <T extends ImpersonalisedHtml> T getThisOrUpperImp(Element e)
	{
		while(null != e)
		{
			T r = getImp(e);
			if(null != r)
			{
				return r;
			}
			
			e = (Element) e.getParentNode();
		}
		return null;
	}
	
	public static <T extends ImpersonalisedHtml> T getThisOrUpperImp(Class<T> of, Element e)
	{
		while(null != e)
		{
			T r = getImp(e);
			if(null != r && r instanceof Object && of.isAssignableFrom(r.getClass()))
			{
				return r;
			}
			
			e = (Element) e.getParentNode();
		}
		return null;
	}

	public static <T> T getModel(HTMLElement cfg, String key)
	{
		return (T) NativeJsSupport.getSupport().getProp(cfg, key);
	}

	public static <T> HTMLElement addModel(HTMLElement cfg, String key, T model)
	{
		NativeJsSupport.getSupport().setProp(cfg, key, model);
		return cfg;
	}
}
