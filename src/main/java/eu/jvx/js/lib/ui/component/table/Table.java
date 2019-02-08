package eu.jvx.js.lib.ui.component.table;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.collection.map.KeyVal;
import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.ImpersonalisedHtml;

/**
 * Model for tables: gives a simplified model of an (probably multi row typed)
 * table and 
 * */
public class Table implements ImpersonalisedHtml
{
	public TableStructureManager tm;
	
	public HTMLElement table;
	
	public Map<String, Entry<String, TableCellConfig>[]> rowTypes;
	
	public Table(TableStructureManager tm)
	{
		this.tm = tm;
		this.table = ImpTools.appendImp(tm.createEmptyTable(), this);
		this.rowTypes = new HashMap<>();
	}
	
	public boolean isRowTypeRegistered(String name)
	{
		return null != rowTypes.get(name);
	}
	
	public void assertRowTypeRegistered(String name)
	{
		if(!isRowTypeRegistered(name))
		{
			System.out.println(name+" row type not registered.");
			throw new RuntimeException(name+" row type not registered.");
		}
	}
	
	public void assertRowTypeNotRegistered(String name)
	{
		if(isRowTypeRegistered(name))
		{
			System.out.println(name+" row type already registered.");
			throw new RuntimeException(name+" row type already registered.");
		}
	}
	
	public static Entry<String, TableCellConfig>[] createSimpleConfig(List<String> colNames)
	{
		Entry<String, TableCellConfig>[] cre = new Entry[colNames.size()];
		
		for(int i=0;i<colNames.size();++i)
		{
			cre[i] = new KeyVal<>(colNames.get(i), TableCellConfig.DEFAULT_1x1_String_set_get);
		}
		
		return cre;
	}
	
	public static Entry<String, TableCellConfig>[] createSimpleConfig(String... colNames)
	{
		Entry<String, TableCellConfig>[] cre = new Entry[colNames.length];
		
		for(int i=0;i<colNames.length;++i)
		{
			cre[i] = new KeyVal<>(colNames[i], TableCellConfig.DEFAULT_1x1_String_set_get);
		}
		
		return cre;
	}
	
	public void registerSimpleRowConfig(String name, String... colNames)
	{
		assertRowTypeNotRegistered(name);
		registerRowType(name, createSimpleConfig(colNames));
	}
	
	public void registerSimpleRowConfig(String name, List<String> colNames)
	{
		assertRowTypeNotRegistered(name);
		registerRowType(name, createSimpleConfig(colNames));
	}
	
	public void registerRowType(String name, Entry<String, TableCellConfig>... cfg)
	{
		assertRowTypeNotRegistered(name);
		rowTypes.put(name, cfg);
	}
	
	public int getColIndexByName(String rowType, String fieldName)
	{
		assertRowTypeRegistered(rowType);
		
		Entry<String, TableCellConfig>[] cfg = this.rowTypes.get(rowType);
		
		for(int i=0;i<cfg.length;++i)
		{
			if(fieldName.equals(cfg[i].getKey()))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public static <T> Table createTableFrom
	(
		TableStructureManager mngr,
		Tabifier<T> processor,
		List<T> data
	)
	{
		Table ret = new Table(mngr);
		
		for(int i=0;i<data.size();++i)
		{
			T d = data.get(i);
			String type = processor.getRecordType.getBy(d);
			
			if(null == type)
			{
				throw new RuntimeException("Can't fetch table row type of data: "+d);
			}
			
			Entry<String, TableCellConfig>[] cfg = processor.getRecordTypeConfig.getBy(type);
			
			if(null == cfg)
			{
				throw new RuntimeException("Not table row type configuration specified for: "+type);
			}
			
			if(!ret.isRowTypeRegistered(type))
			{
				ret.registerRowType(type, cfg);
			}
			
			TableRow tr = ret.createRow(type);
			
			for(int c=0;c<cfg.length;++c)
			{
				Entry<String, TableCellConfig> tcc = cfg[i];
				String name = tcc.getKey();
				TableCell tc = tr.getCellByName(name);

				Object value = processor.getRecordCellValue.getBy(d, name);
				tcc.getValue().setter.publish(tc, value);
			}
		}
		
		return ret;
	}
	
	public TableRow createRow(String name)
	{
		assertRowTypeRegistered(name);
		int next = tm.getNumberOfRows(this);
		
		return tm.createRowAtIndex(this, name, next);
	}
	
	public TableRow getRowAt(int index)
	{
		return tm.getRowAtIndex(this, index);
	}
	
	public Entry<String, TableCellConfig>[] getRowType(String name)
	{
		return rowTypes.get(name);
	}

	public int getNumberOfRows()
	{
		return tm.getNumberOfRows(this);
	}
	
	@Override
	public Object getImpersonator()
	{
		return this;
	}

	@Override
	public HTMLElement getHtml()
	{
		return table;
	}
}
