package eu.jvx.js.lib.style;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.teavm.jso.dom.html.HTMLElement;

import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.bindings.VanillaTools.ClassList;

public class StyleAlaCarteMenu implements StyleMenuSource, StyleDecorator
{
	protected List<String> classes;
	
	public StyleAlaCarteMenu(String... classes)
	{
		ArrayList<String> proc = new ArrayList<String>();
		for(String s:classes)
		{
			if(null != s)
			{
				proc.add(s);
			}
		}
		this.classes = Collections.unmodifiableList(proc);
	}
	
	public StyleAlaCarteMenu(StyleClassSource... classes)
	{
		ArrayList<String> proc = new ArrayList<String>();
		for(StyleClassSource s:classes)
		{
			if(null != s)
			{
				proc.add(s.getCssClassName());
			}
		}
		this.classes = Collections.unmodifiableList(proc);
	}
	
	public void setStyle(HTMLElement elem, boolean trueSet_falseRemove)
	{
		ClassList cl = VanillaTools.getClassList(elem);
		for(String cls:classes)
		{
			if(trueSet_falseRemove)
			{
				cl.add(cls);
			}
			else
			{
				cl.remove(cls);
			}
		}
	}
	
	public void setStyle(HTMLElement elem)
	{
		setStyle(elem, true);
	}
	
	public void unsetStyle(HTMLElement elem)
	{
		setStyle(elem, false);
	}
	
	public boolean hasAll(HTMLElement elem)
	{
		ClassList cl = VanillaTools.getClassList(elem);
		for(String cls:classes)
		{
			if(!cl.contains(cls))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean hasAny(HTMLElement elem)
	{
		ClassList cl = VanillaTools.getClassList(elem);
		for(String cls:classes)
		{
			if(cl.contains(cls))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void toggleStyles(HTMLElement elem)
	{
		setStyle(elem, !hasAll(elem));
	}

	@Override
	public List<String> getClassNames()
	{
		return classes;
	}
}
