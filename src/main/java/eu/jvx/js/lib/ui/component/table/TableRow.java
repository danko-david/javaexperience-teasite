package eu.jvx.js.lib.ui.component.table;

import org.teavm.jso.dom.xml.Element;

import eu.jvx.js.lib.ImpersonalisedHtml;

/**
 * Can be a header or data row
 * */
public class TableRow implements ImpersonalisedHtml
{
	protected Table owner;
	public Object cfg;
	
	public Table getOwnerTable()
	{
		return owner;
	}
	
	public TableRow(Table table)
	{
		this.owner = table;
	}
	
	public int getRowIndexInTable()
	{
		return owner.tm.getRowIndex(this);
	}
	
	public TableCell getCellByName(String name)
	{
		int index = owner.getColIndexByName(owner.tm.getRowType(this), name);
		
		if(index < 0)
		{
			return null;
		}
		
		return owner.tm.accessRowCell(this, index);
	}

	@Override
	public Object getImpersonator()
	{
		return this;
	}

	@Override
	public Element getHtml()
	{
		//not always true (thinking of multi row table model)
		return (Element) cfg;
	}
}
