package eu.jvx.js.tbs.ui;

import org.teavm.jso.core.JSArray;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.NodeList;

import eu.jvx.js.lib.HtmlContainer;
import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.tbs.TbsWeight;

public class TbsTitlePanel implements HtmlContainer
{
	protected final HTMLElement root;
	
	protected final HTMLElement head;
	protected final HTMLElement content;
	protected final HTMLElement footer;
	
	public TbsTitlePanel(TbsWeight weight)
	{
		root = VanillaTools.inlineCreateElement("div", "class", "panel panel-"+weight.getCssName());
		ImpTools.appendImp(root, this);
		VanillaTools.appendChilds
		(
			root,
				head = VanillaTools.inlineCreateElement("div", "class", "panel-heading"),
				content = VanillaTools.inlineCreateElement("div", "class", "panel-body"),
				footer = VanillaTools.inlineCreateElement("div", "class", "panel-footer", "style", "display:none")
		);
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
	
	public HTMLElement getTitle()
	{
		return head;
	}
	
	public HTMLElement getContent()
	{
		return content;
	}
	
	public HTMLElement getFooter()
	{
		return footer;
	}
	
	@Override
	public JSArray<HTMLElement> getSubNodes()
	{
		//search the tab
		return VanillaTools.getChildren(content);
	}
}
