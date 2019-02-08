package eu.jvx.js.lib.ui.container.impl;

import org.teavm.jso.core.JSArray;
import org.teavm.jso.dom.css.CSSStyleDeclaration;
import org.teavm.jso.dom.html.HTMLElement;

import eu.jvx.js.lib.HtmlContainer;
import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.bindings.VanillaTools;

public class SimpleRowStackContainer implements HtmlContainer
{
	protected HTMLElement root;
	
	public SimpleRowStackContainer()
	{
		root = VanillaTools.inlineCreateElement("div");
		ImpTools.appendImp(root, this);
	}
	
	public void addNode(HTMLElement node)
	{
		root.appendChild(node);
		updateBoxWidths();
	}
	
	protected double spacingBetween = 0.0;
	
	protected double spacingBeforeAfter = 0.0;
	
	public void updateBoxWidths()
	{
		JSArray<HTMLElement> ch = getSubNodes();
		if(0 == ch.getLength())
		{
			return;
		}
		
		double width = 1.0;
		width -= 2*spacingBeforeAfter;
		
		int num = ch.getLength()-1;
		width -= num*spacingBetween;
		
		width /= ch.getLength();
		width *= 100;
		
		String w = width+"%";
		String s = spacingBetween*100+"%";
		for(int i=0;i< ch.getLength();++i)
		{
			HTMLElement n = ch.get(i);
			
			CSSStyleDeclaration style = n.getStyle();
			
			style.setPropertyValue("marginLeft", s);
			style.setPropertyValue("marginRight", s);
			
			style.setProperty("display", "inline-block");
			style.setProperty("width", w);

			if(0 == i)
			{
				style.setPropertyValue("marginLeft", spacingBeforeAfter*100+"%");
			}
			
			if(0 == ch.getLength()-1)
			{
				style.setPropertyValue("marginRight", spacingBeforeAfter*100+"%");
			}
		}
	}
	
	@Override
	public JSArray<HTMLElement> getSubNodes()
	{
		return VanillaTools.getChildren(root);
	}

	@Override
	public Object getImpersonator()
	{
		return this;
	}

	@Override
	public HTMLElement getHtml()
	{
		return root;
	}

	public void setSpacingBeforeAfter(double d, boolean update)
	{
		spacingBeforeAfter = d;
		if(update)
		{
			updateBoxWidths();
		}
	}

	public void setSpacingBetween(double d, boolean update)
	{
		spacingBetween = d;
		if(update)
		{
			updateBoxWidths();
		}
	}
}
