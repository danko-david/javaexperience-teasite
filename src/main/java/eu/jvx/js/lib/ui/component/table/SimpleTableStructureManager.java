package eu.jvx.js.lib.ui.component.table;

import static eu.jvx.js.lib.bindings.VanillaTools.*;

import java.util.Map.Entry;

import org.teavm.jso.core.JSArray;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Element;
import org.teavm.jso.dom.xml.Node;
import org.teavm.jso.dom.xml.NodeList;

import eu.jvx.js.lib.HtmlTools;
import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.NativeJsSupport;
import eu.jvx.js.lib.bindings.VanillaTools;

public class SimpleTableStructureManager implements TableStructureManager
{
	public static final SimpleTableStructureManager INSTANCE = new SimpleTableStructureManager();
	
	public SimpleTableStructureManager(){}

	@Override
	public HTMLElement createEmptyTable()
	{
		return	appendChilds
				(
					inlineCreateElement("table"),
						inlineCreateElement("tbody")
				);
	}
	
	protected HTMLElement locateRoot(HTMLElement e)
	{
		return e.querySelector("tbody");
	}
	
	@Override
	public TableCell accessRowCell(TableRow row, int col)
	{
		HTMLElement vn = (HTMLElement) VanillaTools.getChildren(((HTMLElement)row.cfg)).get(col);
		
		if(null == vn)
		{
			return null;
		}
		
		return new TableCell(row, vn);
	}

	@Override
	public int getNumberOfRows(Table table)
	{
		return VanillaTools.getChildren(locateRoot(table.getHtml())).getLength();
	}
	
	@Override
	public TableRow createRowAtIndex(Table owner, String name, int index)
	{
		Entry<String, TableCellConfig>[] type = owner.getRowType(name);
		
		HTMLElement root = locateRoot(owner.getHtml());
		JSArray<HTMLElement> ch = VanillaTools.getChildren(root);
		
		if(index > ch.getLength())
		{
			throw new RuntimeException("Can't create new table row to position: "+index+", when have only "+ch.getLength()+" rows");
		}

		HTMLElement add = inlineCreateElement("tr");
		
		for(int i=0;i<type.length;++i)
		{
			HTMLElement td = inlineCreateElement("td");
			TableCellVisualSettings cfg = type[i].getValue().config;
			NativeJsSupport.getSupport().setProp(td, "colSpan", ""+cfg.colspan);
			NativeJsSupport.getSupport().setProp(td, "rowSpan", ""+cfg.rowspan);
			//td.colSpan =  cfg.colspan;
			//td.rowSpan =  cfg.rowspan;
			
			add.appendChild(td);
		}
		
		VanillaTools.getDataSet(add).put("rowtype", name);
		//NativeJsSupport.getSupport().setProp(add, "data-rowtype", name);
		//add.dataset.$put("rowtype", name);
		TableRow tr = new TableRow(owner);
		tr.cfg = add;
		
		ImpTools.appendImp(add, tr);
		
		if(index == ch.getLength())
		{
			root.appendChild(add);
		}
		else
		{
			root.insertBefore(add, ch.get(index));
		}
		
		return tr;
	}

	@Override
	public TableRow getRowAtIndex(Table table, int index)
	{
		Element elem = (Element) VanillaTools.getChildren(locateRoot(table.getHtml())).get(index);
		
		if(null == elem)
		{
			return null;
		}
		
		TableRow tr = new TableRow(table);
		tr.cfg = elem;
		return tr;
	}

	@Override
	public int getRowIndex(TableRow tableRow)
	{
		return HtmlTools.childIndex(((Element)tableRow.cfg));
	}

	@Override
	public String getRowType(TableRow tableRow)
	{
		//NativeJsSupport.getSupport().log("getRowType", tableRow, (String) NativeJsSupport.getSupport().getProp(tableRow.cfg, "data-rowtype"));
		//return (String) NativeJsSupport.getSupport().getProp(tableRow.cfg, "data-rowtype");
		return VanillaTools.getDataSet((HTMLElement) tableRow.cfg).get("rowtype");
		//return ((HTMLElement)tableRow.cfg).dataset.$get("rowtype");
	}
}
