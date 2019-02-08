package eu.jvx.js.lib.bindings;

import org.teavm.jso.JSProperty;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Node;

import eu.jvx.js.lib.HtmlNode;

/**
 * Functions from VanillaTools
 * */
/*
public abstract class VanillaNode implements HTMLElement, HtmlNode
{
	public abstract boolean is(String selector);
	
	public abstract VanillaNode whereParent(String selector);
	
	public abstract VanillaNode whereParentWithBoundary(String selector, String boundary);
	
	//public abstract VanillaNode firstParentWhere(GetBy1<Boolean, VanillaNode> sel_function);
	
	public abstract int index();
	
	public abstract void changeClass(String from ,String to);
	
	public abstract String getContent();
	
	public abstract void setContent(String content);
	
	public abstract void alterTag(String newTagName);
	
	public VanillaNode appendChilds(Node... ns)
	{
		for(Node n:ns)
		{
			if(null != n)
			{
				this.appendChild(n);
			}
		}
		return this;
	}
	
	public void remove()
	{
		getParentNode().removeChild(this);
	}
	
	//public Map<String, String> dataset;
	
	
	/*@Template("get")
	public abstract Object $get(String key);

	@Template("put")
	public abstract void $put(String key, Object value);

	@Template("delete")
	public abstract void $delete(String key);*/
	
	//TODO back to StJs Node
	/*public DomStyle style;
	
	public String innerText;

	public Array<VanillaNode> children;

	public abstract void requestFullscreen();
}
*/
