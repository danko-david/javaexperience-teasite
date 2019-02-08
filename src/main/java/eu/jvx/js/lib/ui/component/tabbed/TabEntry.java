package eu.jvx.js.lib.ui.component.tabbed;

import eu.jvx.js.lib.WrapperHtml;
import eu.jvx.js.lib.bindings.VanillaTools;

public class TabEntry
{
	protected TabbedPane owner;
	protected WrapperHtml tag;
	protected WrapperHtml content;
	
	public TabEntry(WrapperHtml tag, WrapperHtml content)
	{
		this.tag = tag;
		this.content = content;
	}
	
	public TabbedPane getOwner()
	{
		return owner;
	}
	
	public void setOwner(TabbedPane owner)
	{
		this.owner = owner;
	}
	
	public void setSelected()
	{
		this.owner.tm.setTabIndexActive(owner.getHtml(), owner.getTabIndex(this));
	}
	
	public boolean isSelected()
	{
		return this.owner.tm.isTabIndexActive(owner.getHtml(), owner.getTabIndex(this));
	}
	
	public void remove()
	{
		VanillaTools.remove(tag.getRoot());
		VanillaTools.remove(content.getRoot());
		owner.resetSelected();
	}

	public WrapperHtml getContainer()
	{
		return content;
	}

	public WrapperHtml getTag()
	{
		return tag;
	}
}
