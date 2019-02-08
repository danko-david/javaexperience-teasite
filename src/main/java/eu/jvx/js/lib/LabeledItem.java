package eu.jvx.js.lib;

import eu.jvx.js.generic.Translation;

public class LabeledItem
{
	public String id;
	public Translation label;
	
	public LabeledItem(String id, Translation label)
	{
		this.id = id;
		this.label = label;
	}
}
