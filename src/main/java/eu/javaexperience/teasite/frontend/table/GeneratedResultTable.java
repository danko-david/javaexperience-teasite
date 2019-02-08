package eu.javaexperience.teasite.frontend.table;

import java.util.List;

import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.teasite.frontend.table.ResultTableGenerator.TableField;
import eu.jvx.js.lib.ui.component.table.Table;

public class GeneratedResultTable<S>
{
	public ResultTableGenerator<S> generator;
	public Table table;
	public HTMLElement html;
	public Iterable<S> dataSet;
	public List<TableField<S>> fields;
}
