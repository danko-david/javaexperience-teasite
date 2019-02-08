package eu.jvx.js.ui;

import java.util.HashMap;
import java.util.Map;

import org.teavm.jso.dom.html.HTMLElement;

import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.ImpersonalisedHtml;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.tbs.TbsWeightEntry;

/**
 * Overseer alap menük:
 * 	- CPU, Memória használat, load,
 *  - mysql processlist
 *  - SysVinit service monitor (start, stop, restart, állapot(pid)) (jelenlegi szint és a beavatkozó gombok letilthatósága)
 * 
 * */
public class MultiBar implements ImpersonalisedHtml/*really it's a VanillaNode, implementation comes from the Node's prototype at javascript side*/
{
	protected HTMLElement htmlRoot;
	
	protected Map<String, HTMLElement> bars;
	protected Map<String, TbsWeightEntry> extraInfo;
	
	
	public MultiBar(TbsWeightEntry... arguments)
	{
		bars = new HashMap<>();
		extraInfo = new HashMap<>();
		
		htmlRoot = VanillaTools.inlineCreateElement
		(
			"div",
			"class", "progress"
		);
		
		ImpTools.appendImp(htmlRoot, this);
		//htmlRoot.$put(ImpTools.NODE_IMPERSONATOR, this);
		
		for(int i=0;i<arguments.length;++i)
		{
			TbsWeightEntry a = arguments[i];
			HTMLElement vn = createBar(a.weight.getCssName());
			
			extraInfo.put(a.id, a);
			bars.put(a.id, vn);
			
			htmlRoot.appendChild
			(
				vn
			);
		}
	}
	
	public void updatePercentById(Map<String, Double> vals)
	{
		for(String id:vals.keySet())
		{
			HTMLElement vn = bars.get(id);
			if(null != vn)
			{
				double val = (vals.get(id)*100);
				vn.getStyle().setProperty("width", val+"%");
				//vn.put("style", "width: "+val+"%");
				//TODO vn.put("aria-valuenow", val);
			}
		}
	}
	
	public void updateLabelsById(Map<String, String> vals)
	{
		for(String id:vals.keySet())
		{
			HTMLElement vn = bars.get(id);
			if(null != vn)
			{
				VanillaTools.setContent(vn, vals.get(id));
				//vn.setContent(vals.get(id));
			}
		}
	}
	
	public void setAnimation()
	{
		//TODO
	}
	
	protected static final HTMLElement createBar(String label)
	{
		return VanillaTools.inlineCreateElement
		(
			"div",
			"class", "progress-bar progress-bar-"+label,
			"role", "progressbar",
			"aria-valuemin", "0",
			"aria-valuemax", "100"
		);
	}
	
	@Override
	public Object getImpersonator()
	{
		return this;
	}

	@Override
	public HTMLElement getHtml()
	{
		return htmlRoot;
	}
}
