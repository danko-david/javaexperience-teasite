package eu.jvx.js.lib.ui.component.table;

import java.util.Map.Entry;

import eu.javaexperience.interfaces.simple.getBy.GetBy1;
import eu.javaexperience.interfaces.simple.getBy.GetBy2;

public class Tabifier<Data>
{
	public GetBy1<String, Data> getRecordType;
	public GetBy1<Entry<String, TableCellConfig>[], String> getRecordTypeConfig;
	public GetBy2<Object, Data, String> getRecordCellValue;
}
