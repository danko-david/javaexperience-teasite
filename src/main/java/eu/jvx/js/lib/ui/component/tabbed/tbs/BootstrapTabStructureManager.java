package eu.jvx.js.lib.ui.component.tabbed.tbs;

import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.NodeList;

import eu.javaexperience.semantic.references.MayNull;
import eu.javaexperience.text.StringTools;
import eu.jvx.js.lib.SimpleWrapperHtml;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.bindings.VanillaTools.ClassList;
import eu.jvx.js.lib.ui.component.tabbed.TabEntry;
import eu.jvx.js.lib.ui.component.tabbed.TabStructureManager;
import static eu.jvx.js.lib.bindings.VanillaTools.*;

public class BootstrapTabStructureManager implements TabStructureManager
{
	public static final BootstrapTabStructureManager DEFAULT_INSTANCE = new BootstrapTabStructureManager();
	
	@Override
	public HTMLElement createTabbedPaneSkel()
	{
		return appendChilds
		(
			inlineCreateElement("div"),
				inlineCreateElement("ul", "class", "tabed-panel-nav-selector nav nav-tabs"),
				inlineCreateElement("div", "class", "tabed-panel-container-selector tab-content")
		);
	}

	@Override
	public TabEntry addTabToPoz(HTMLElement structureRoot, int absolutePosition)
	{
		//.tabed-panel-nav-selector
		//.tabed-panel-container-selector
		HTMLElement nav = structureRoot.querySelector(".tabed-panel-nav-selector");
		HTMLElement container = structureRoot.querySelector(".tabed-panel-container-selector");
		
		String id = "tab_"+StringTools.randomString(50);
		
		HTMLElement rn = inlineCreateElement("li");
		HTMLElement ra = inlineCreateElement("a", "data-toggle", "tab", "href", "#"+id);
		rn.appendChild(ra);
		SimpleWrapperHtml nnav = new SimpleWrapperHtml(rn, ra);
		
		HTMLElement c = inlineCreateElement("div", "id", id, "class", "tab-pane fade");
		SimpleWrapperHtml ncont = new SimpleWrapperHtml(c, c);
		
		TabEntry ent = new TabEntry(nnav, ncont);
		
		if(VanillaTools.getChildren(container).getLength() == 0)
		{
			nav.appendChild(rn);
			container.appendChild(c);
		}
		else
		{
			nav.insertBefore(rn, getChildren(nav).get(absolutePosition));
			container.insertBefore(c, getChildren(container).get(absolutePosition));
		}
		
		if(null == structureRoot.querySelector(".nav-tabs li.active"))
		{
			getClassList(rn).add("active");
		}
		
		return ent;
	}
	
	protected static void switchClassFrom(HTMLElement root, String select, String[] cls, @MayNull String[] to)
	{
		NodeList<? extends HTMLElement> lst = root.querySelectorAll(select);
		for(int i=0;i<lst.getLength();++i)
		{
			ClassList cl = getClassList(lst.get(i));
			for(String s:cls)
			{
				cl.remove(s);
			}
			
			if(null != to)
			{
				for(String s:to)
				{
					cl.add(s);
				}
			}
		}
	}
	
	protected static void switchToActive(HTMLElement elem)
	{
		ClassList cl = getClassList(elem);
		for(String s:CLASS_HIDDEN)
		{
			cl.remove(s);
		}
		
		for(String s:CLASS_ACTIVE)
		{
			cl.add(s);
		}
	}
	
	protected static final String[] CLASS_ACTIVE = new String[]{"active", "in"};
	
	protected static final String[] CLASS_HIDDEN = new String[]{"hidden"};
	
	@Override
	public void setTabIndexActive(HTMLElement structureRoot, int absolutePosition)
	{
		switchClassFrom(structureRoot, ".nav-tabs li.active", CLASS_ACTIVE, null);
		switchToActive(getChildren(structureRoot.querySelector(".tabed-panel-nav-selector")).get(absolutePosition));
		
		switchClassFrom(structureRoot, ".tab-pane", CLASS_ACTIVE, CLASS_HIDDEN);
		switchToActive(getChildren(structureRoot.querySelector(".tabed-panel-container-selector")).get(absolutePosition));
	}

	@Override
	public boolean isTabIndexActive(HTMLElement html, int tabIndex)
	{
		return getClassList(getChildren(html.querySelector(".tabed-panel-nav-selector")).get(tabIndex)).contains("active");
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
