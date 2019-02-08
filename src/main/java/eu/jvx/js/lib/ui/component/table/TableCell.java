package eu.jvx.js.lib.ui.component.table;

import org.teavm.jso.dom.html.HTMLElement;

import eu.jvx.js.lib.ImpersonalisedHtml;

public class TableCell implements ImpersonalisedHtml
{
	protected TableRow owner;
	protected HTMLElement cell;
	
	public TableCell(TableRow row, HTMLElement cfg)
	{
		this.owner = row;
		this.cell = cfg;
	}

	@Override
	public Object getImpersonator()
	{
		return this;
	}

	@Override
	public HTMLElement getHtml()
	{
		return cell;
	}
}