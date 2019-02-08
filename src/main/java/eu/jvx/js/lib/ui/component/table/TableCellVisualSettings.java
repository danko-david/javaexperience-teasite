package eu.jvx.js.lib.ui.component.table;

import java.util.Map;

public class TableCellVisualSettings
{
	public final int colspan;
	public final int rowspan;
	public final String cssDecoration;
	
	protected final Map<String, Object> etc;
	
	public TableCellVisualSettings(int cs, int rs, String css, Map<String, Object> etc)
	{
		this.colspan = cs;
		this.rowspan = rs;
		this.cssDecoration = css;
		this.etc = etc;
	}
	
	public Object getEtcConfig(String key)
	{
		if(null != etc)
		{
			return etc.get(key);
		}
		
		return null;
	}
	
	public static TableCellVisualSettings DEFAULT_1x1 = new TableCellVisualSettings(1, 1, null, null);
}
