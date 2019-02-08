package eu.jvx.js.lib.ui.form;

import java.util.List;
import java.util.Map.Entry;

public class FormEntryBuilder
{
	public static enum FormEntryType
	{
		
		
		
	}
	
	protected FormEntryType type;
	
	protected boolean required = false;
	
	//name in the form
	protected String name;
	
	//it may be a translation id
	protected String label;
	
	//it may be a translation id
	protected String placeholder;
	
	protected String helpText;
	
	protected List<String> classes;
	
	protected String value;
	
	protected List<Entry<String,String>> entries;
	
}
