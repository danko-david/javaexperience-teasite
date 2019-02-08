package eu.jvx.js.lib.activity;

import java.util.HashMap;

import org.teavm.jso.dom.xml.Element;

import eu.javaexperience.collection.map.MapTools;
import eu.javaexperience.io.file.FileTools;
import eu.jvx.js.lib.ImpersonalisedHtml;
import eu.jvx.js.lib.NativeJsSupport;
import eu.jvx.js.lib.Refreshable;
import eu.jvx.js.lib.bindings.ChromeOnlyJsMemory;
import eu.jvx.js.lib.utils.CyclicRefresh;
import eu.jvx.js.tbs.TbsWeight;
import eu.jvx.js.tbs.TbsWeightEntry;
import eu.jvx.js.tbs.ui.TbsTitlePanel;
import eu.jvx.js.ui.MultiBar;

public class BrowserJsMemoryMonitorActivity implements Refreshable, ImpersonalisedHtml
{
	public static String getAdhocPlacerName()
	{
		return "BrowserJsMemoryMonitorActivity";
	}
	
	public TbsTitlePanel panel;
	public MultiBar jsbar;
	
	public CyclicRefresh refresher;
	
	public static long _1Kb = 1024;
	public static long _1Mb = _1Kb*1024;
	public static long _1Gb = _1Mb*1024;
	public static long _1Tb = _1Gb*1024;
	
	public BrowserJsMemoryMonitorActivity()
	{
		refresher = new CyclicRefresh(this);
		jsbar = new MultiBar
		(
			new TbsWeightEntry("used", TbsWeight.Danger, null),
			new TbsWeightEntry("unallocated", TbsWeight.Warning, null),
			new TbsWeightEntry("allocateable", TbsWeight.Success, null)
		);
		
		panel = new TbsTitlePanel(TbsWeight.Primary);
		panel.getTitle().setInnerHTML("Browser js memory usage");
		panel.getContent().appendChild
		(
			jsbar.getHtml()
		);
	}
	
	@Override
	public void refresh()
	{
		ChromeOnlyJsMemory mem = (ChromeOnlyJsMemory) NativeJsSupport.getSupport().eval("window.performance.memory");
		double max = mem.getJsHeapSizeLimit();
		
		if(mem.getTotalJSHeapSize()/max < 0.5)
		{
			max = 2*mem.getTotalJSHeapSize();
		}
		
		jsbar.updatePercentById
		(
			MapTools.inlineFill
			(
				new HashMap<String, Double>(),
				"used", ((double)mem.getUsedJSHeapSize())/max,
				"unallocated", ((double)(mem.getTotalJSHeapSize() - mem.getUsedJSHeapSize()))/max,
				"allocateable", ((double)(max- mem.getTotalJSHeapSize()))/max
			)
		);
		
		jsbar.updateLabelsById
		(
			MapTools.inlineFill
			(
				new HashMap<String, String>(),
				"used", FileTools.toBytesKbMbGbOrTb((long)mem.getUsedJSHeapSize()),
				"unallocated", FileTools.toBytesKbMbGbOrTb((long)(mem.getTotalJSHeapSize() - mem.getUsedJSHeapSize())),
				"allocateable", FileTools.toBytesKbMbGbOrTb((long)(mem.getJsHeapSizeLimit() - mem.getTotalJSHeapSize()))
			)
		);
	}

	@Override
	public Object getImpersonator()
	{
		return this;
	}

	@Override
	public Element getHtml()
	{
		return panel.getHtml();
	}
}
