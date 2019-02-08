package eu.jvx.js.lib.ui.component.table;

import eu.javaexperience.interfaces.simple.getBy.GetBy1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.jvx.js.lib.NativeJsSupport;

public class TableCellConfig
{
	public TableCellVisualSettings config;
	
	public GetBy1<Object, TableCell> getter;
	public SimplePublish2<TableCell, Object> setter;
	
	public TableCellConfig
	(
		TableCellVisualSettings vis,
		GetBy1<Object, TableCell> getter,
		SimplePublish2<TableCell, Object> setter
	)
	{
		this.config = vis;
		this.getter = getter;
		this.setter = setter;
	}
	
	public static final GetBy1<Object, TableCell> GET_INNER_TEXT_CONTENT = new GetBy1<Object, TableCell>()
	{
		@Override
		public Object getBy(TableCell arg0)
		{
			return NativeJsSupport.getSupport().getProp(arg0.cell, "innerText");
			//return ((VanillaNode)arg0.cell).innerText;
		}
	};
	
	public static final SimplePublish2<TableCell, Object> SET_INNER_TEXT_CONTENT = new SimplePublish2<TableCell, Object>()
	{
		@Override
		public void publish(TableCell arg0, Object arg1)
		{
			NativeJsSupport.getSupport().setProp(arg0.cell, "innerText", arg1);
		}
	};
	
	public static TableCellConfig DEFAULT_1x1_String_set_get = new TableCellConfig
	(
		TableCellVisualSettings.DEFAULT_1x1,
		GET_INNER_TEXT_CONTENT,
		SET_INNER_TEXT_CONTENT
	);
}
