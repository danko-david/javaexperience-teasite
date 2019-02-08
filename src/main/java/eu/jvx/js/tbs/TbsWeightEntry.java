package eu.jvx.js.tbs;

import eu.jvx.js.generic.Translation;
import eu.jvx.js.lib.LabeledItem;

public class TbsWeightEntry extends LabeledItem
{
	public TbsWeight weight;
	public TbsWeightEntry(String id, TbsWeight weight, Translation label)
	{
		super(id, label);
		this.weight = weight;
	}
}
