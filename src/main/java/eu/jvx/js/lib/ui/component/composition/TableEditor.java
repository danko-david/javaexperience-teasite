package eu.jvx.js.lib.ui.component.composition;

import java.util.List;

import eu.jvx.js.lib.ui.component.table.TableStructureManager;

/**
 * 3 részből áll:
 * 	- szerkesztő
 *  - szűrő
 *  - táblázat
 *  
 *  A szűrővel le lehet kérni az adatokat.
 *  
 *  
 *  
 *  
 * */
public class TableEditor
{
	protected TableEditable editor;
	
	public TableEditor(TableEditable edit)
	{
		this.editor = edit;
	}
	
	public void init()
	{
		
		
	}
	
	/*public Form getEditorForm()
	{
		
	}
	
	public Table getTable()
	{
		
	}*/
	
	
	
	public static TableEditor createFrom
	(
		TableEditable editor,
		TableStructureManager tsm,
		List<UserEditableField> fields
	)
	{
		TableEditor ret = new TableEditor(editor);
		
		
		
		return ret;
	}
}
