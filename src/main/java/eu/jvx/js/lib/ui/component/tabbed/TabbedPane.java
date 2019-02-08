package eu.jvx.js.lib.ui.component.tabbed;

import java.util.ArrayList;
import java.util.List;

import org.teavm.jso.dom.html.HTMLElement;

import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.ImpersonalisedHtml;

public class TabbedPane implements ImpersonalisedHtml
{
	protected TabStructureManager tm;
	
	protected HTMLElement root;
	
	protected List<TabEntry> tabs;
	
	public TabbedPane(TabStructureManager builder)
	{
		this.tm = builder;
		root = ImpTools.appendImp(this.tm.createTabbedPaneSkel(), this);
		tabs = new ArrayList<>();
	}
	
	public void initialize()
	{
		this.tm.initialize(root);
	}
	
	public TabEntry createNewTab()
	{
		TabEntry ent = this.tm.addTabToPoz(root, tabs.size());
		ent.setOwner(this);
		tabs.add(ent);
		return ent;
	}
	
	public List<TabEntry> getTabs()
	{
		return tabs;
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
	
	public void resetSelected()
	{
		if(0 != tabs.size())
		{
			tm.setTabIndexActive(root, 0);
		}
	}

	public int getTabIndex(TabEntry tabEntry)
	{
		for(int i=0;i<tabs.size();++i)
		{
			if(tabEntry == tabs.get(i))
			{
				return i;
			}
		}
		return -1;
	}
}
