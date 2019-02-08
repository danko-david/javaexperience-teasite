package eu.jvx.js.lib.ui.component.table;

import org.teavm.jso.dom.html.HTMLElement;

public interface TableStructureManager
{
	public HTMLElement createEmptyTable();

	public int getNumberOfRows(Table table);
	
	public TableRow getRowAtIndex(Table table, int index);
	
	public TableRow createRowAtIndex(Table table, String name, int index);
	
	public TableCell accessRowCell(TableRow tableRow, int index);

	public int getRowIndex(TableRow tableRow);

	public String getRowType(TableRow tableRow);
}
