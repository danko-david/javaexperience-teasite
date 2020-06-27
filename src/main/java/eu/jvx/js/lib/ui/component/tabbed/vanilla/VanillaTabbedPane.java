package eu.jvx.js.lib.ui.component.tabbed.vanilla;

import static eu.jvx.js.lib.bindings.VanillaTools.getChildren;
import static eu.jvx.js.lib.bindings.VanillaTools.inlineCreateElement;

import java.util.List;

import org.teavm.jso.core.JSArray;
import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.text.StringTools;
import eu.jvx.js.lib.SimpleWrapperHtml;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.ui.component.tabbed.TabEntry;
import eu.jvx.js.lib.ui.component.tabbed.TabStructureManager;

public class VanillaTabbedPane implements TabStructureManager
{
	//make available to set the style from outside class
	//protected static final StyleCollection STYLE = StyleCollection.createStyleDomCollection();
	
	static
	{
	//	STYLE.addStyle(".jvx-tabbed-root .jvx-tabbed-tabs ul {display:inline-block; list-style:none}");
	//	STYLE.addStyle(".jvx-tabbed-root .jvx-tabbed-tabs ul li {display:inline-block;}");
	/*
		display: inline-block;
		margin: 8px;
		border: 1px solid black;
		padding: 5px;
		border-radius: 5px;
	 */
	}
	
	
	@Override
	public HTMLElement createTabbedPaneSkel()
	{
		return new H("div").attrs("class", "jvx-tabbed-root").addChilds
		(
			new H("ul").attrs("class", "jvx-tabbed-tabs"),
			new H("div").attrs("class", "jvx-tabbed-frames")
		).getHtml();
	}

	@Override
	public TabEntry addTabToPoz(HTMLElement structureRoot, int absolutePosition)
	{
		HTMLElement nav = structureRoot.querySelector(".jvx-tabbed-tabs");
		HTMLElement container = structureRoot.querySelector(".jvx-tabbed-frames");
		
		String id = "tab_"+StringTools.randomString(50);
		
		H rn = new H("li").attrs("data-tab-id", id);
		rn.on("click", e->setActive(rn));
		
		SimpleWrapperHtml nnav = new SimpleWrapperHtml(rn.getHtml(), rn.getHtml());
		
		HTMLElement c = inlineCreateElement("div", "id", id);
		SimpleWrapperHtml ncont = new SimpleWrapperHtml(c, c);
		
		TabEntry ent = new TabEntry(nnav, ncont);
		
		JSArray<HTMLElement> chs = VanillaTools.getChildren(container);
		
		if(0 == chs.getLength())
		{
			rn.getStyleClasses().add("active");
		}
		else
		{
			new H(c).setVisible(false);
		}
		
		if(chs.getLength() <= absolutePosition)
		{
			nav.appendChild(rn.getHtml());
			container.appendChild(c);
		}
		else
		{
			nav.insertBefore(rn.getHtml(), getChildren(nav).get(absolutePosition));
			
			container.insertBefore(c, getChildren(container).get(absolutePosition));
		}
		
		return ent;
	}
	
	protected void setActive(H tab)
	{
		H root = tab.queryParentOrThis(".jvx-tabbed-root");
		if(null != root)
		{
			//remove all active tab then set the only active tab 
			for(H li: root.queryChildAll(".jvx-tabbed-tabs li"))
			{
				li.getStyleClasses().remove("active");
			}
			tab.getStyleClasses().add("active");
			
			//hide all frames and show the referenced
			for(H t:root.queryChild(".jvx-tabbed-frames").listChilds())
			{
				t.setVisible(false);
			}
			
			String id = tab.getHtml().getAttribute("data-tab-id");
			
			H dst = root.queryChild("#"+id);
			if(null != dst)
			{
				dst.setVisible(true);
			}
		}
	}

	@Override
	public void setTabIndexActive(HTMLElement structureRoot, int absolutePosition)
	{
		H root = new H(structureRoot).queryParentOrThis(".jvx-tabbed-root");
		if(null != root)
		{
			List<H> chs = root.queryChild(".jvx-tabbed-tabs").listChilds();
			if(absolutePosition < chs.size())
			{
				setActive(chs.get(absolutePosition));
			}
		}
	}

	@Override
	public boolean isTabIndexActive(HTMLElement structureRoot, int tabIndex)
	{
		H root = new H(structureRoot).queryParentOrThis(".jvx-tabbed-root");
		if(null != root)
		{
			List<H> chs = root.queryChild(".jvx-tabbed-tabs").listChilds();
			if(tabIndex < chs.size())
			{
				return chs.get(tabIndex).getStyleClasses().contains("active");
			}
		}
		
		return false;
	}

	@Override
	public void initialize(HTMLElement root)
	{
		
	}

	@Override
	public void destroy(HTMLElement root)
	{
		
	}
}
