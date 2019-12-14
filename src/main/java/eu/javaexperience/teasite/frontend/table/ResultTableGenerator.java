package eu.javaexperience.teasite.frontend.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.collection.map.SmallMap;
import eu.javaexperience.interfaces.ExternalDataAttached;
import eu.javaexperience.interfaces.simple.getBy.GetBy1;
import eu.javaexperience.interfaces.simple.getBy.GetBy3;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.reflect.Mirror;
import eu.jvx.js.lib.ui.component.table.Table;
import eu.jvx.js.lib.ui.component.table.TableRow;
import eu.jvx.js.lib.ui.component.table.TableStructureManager;

/**
 * TODO:
 * 	- alternative name (fieldname => transaltion name)
 * 	- alterantive name rendering method (transaltion name => updatable text)
 * 	- extra column (add edit, delete button)
 * 	- extra row (before Header, first, last) (to search, order by; summarize)
 * */
public class ResultTableGenerator<E>
{
	public static class TableField<E> implements ExternalDataAttached
	{
		public String fieldName;
		
		public GetBy1<HTMLElement, TableField<E>> renderBeforeHeader;
		public GetBy1<HTMLElement, TableField<E>> renderLabel;
		public GetBy1<HTMLElement, TableField<E>> renderVeryFirst;
		public GetBy3<HTMLElement, TableField<E>, TableRow, E> renderField;
		public GetBy1<HTMLElement, TableField<E>> renderLast;
		
		protected Map<String, Object> extraData;
		
		@Override
		public Map<String, Object> getExtraDataMap()
		{
			if(null == extraData)
			{
				extraData = new SmallMap<>();
			}
			return extraData;
		}
	}
	
	public SimplePublish1<List<TableField<E>>> getFields;
	
	public SimplePublish1<List<TableField<E>>> modifyFields;
	
	public static final GetBy1<GetBy1<HTMLElement, TableField>, TableField> RENDERER_GETTER_BEFORE_HEADER = (tf)-> tf.renderBeforeHeader;
	public static final GetBy1<GetBy1<HTMLElement, TableField>, TableField> RENDERER_GETTER_LABEL = (tf)-> tf.renderLabel;
	public static final GetBy1<GetBy1<HTMLElement, TableField>, TableField> RENDERER_GETTER_VERY_FRIST = (tf)-> tf.renderVeryFirst;
	public static final GetBy1<GetBy1<HTMLElement, TableField>, TableField> RENDERER_GETTER_LAST = (tf)-> tf.renderLast;
	
	public GeneratedResultTable<E> generate(TableStructureManager tsm, Iterable<E> elements)
	{
		Table table = new Table(tsm);
		
		ArrayList<TableField<E>> fds = new ArrayList<>();
		getFields.publish(fds);
		
		if(null != modifyFields)
		{
			modifyFields.publish(fds);
		}
		
		{
			ArrayList<String> fields = new ArrayList<>();
			
			for(TableField<E> fd:fds)
			{
				fields.add(fd.fieldName);
			}
			
			table.registerSimpleRowConfig("row", fields.toArray(Mirror.emptyStringArray));
		}
		
		renderRowWithGenerator(table, fds, RENDERER_GETTER_BEFORE_HEADER);
		renderRowWithGenerator(table, fds, RENDERER_GETTER_LABEL);
		renderRowWithGenerator(table, fds, RENDERER_GETTER_VERY_FRIST);
		
		for(E elem: elements)
		{
			TableRow head = table.createRow("row");
			ResultTableGenerator.updateRow(fds, head, elem);
		}
		
		renderRowWithGenerator(table, fds, RENDERER_GETTER_LAST);
		
		GeneratedResultTable<E> ret = new GeneratedResultTable<>();
		
		
		ret.fields = fds;
		ret.generator = this;
		ret.dataSet = elements;
		ret.table = table;
		ret.html = table.getHtml();
		return ret;
	}
	
	public static <E> void updateRow(List<TableField<E>> fields, TableRow head, E elem)
	{
		for(TableField<E> fd:fields)
		{
			if(null != fd.renderField)
			{
				HTMLElement add = fd.renderField.getBy(fd, head, elem);
				if(null != add)
				{
					String key = fd.fieldName;
					HTMLElement f = head.getCellByName(key).getHtml();
					f.setInnerHTML("");
					f.appendChild(add);
				}
			}
		}
	}

	protected void renderRowWithGenerator(Table table, List<TableField<E>> fds, GetBy1<GetBy1<HTMLElement, TableField>, TableField> rendererGetter)
	{
		boolean doRender = false;
		
		for(TableField<E> s:fds)
		{
			if(null != rendererGetter.getBy(s))
			{
				doRender = true;
				break;
			}
		}
		
		if(doRender)
		{
			TableRow head = table.createRow("row");
			for(TableField<E> s:fds)
			{
				GetBy1<HTMLElement, TableField<E>> render = (GetBy1) rendererGetter.getBy(s);
				if(null != render)
				{
					HTMLElement add = render.getBy(s);
					if(null != add)
					{
						head.getCellByName(s.fieldName).getHtml().appendChild(add);
					}
				}
			}
		}
	}
}
