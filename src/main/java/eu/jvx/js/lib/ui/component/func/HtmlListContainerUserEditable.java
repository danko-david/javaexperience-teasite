package eu.jvx.js.lib.ui.component.func;

import java.util.ArrayList;
import java.util.List;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.MouseEvent;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.json.JSON;

import eu.javaexperience.teavm.datareprez.DataArrayTeaVMImpl;
import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.style.StyleTools.StyleAlaCarte;
import eu.jvx.js.lib.style.TbsStyle;
import eu.jvx.js.tbs.TbsGlyph;

public abstract class HtmlListContainerUserEditable extends HtmlListContainer<String, String>
{
	protected HTMLElement cont;
	
	@Override
	protected HTMLElement construct()
	{
		cont = new H("div").getHtml();
		
		H ret = new H("div");
		ret.addChilds(cont);
		ret.addChilds(new H("span").style(TbsStyle.BTN_SUCCESS, TbsGlyph.PLUS).onClick(new EventListener<MouseEvent>()
		{
			@Override
			public void handleEvent(MouseEvent arg0)
			{
				createNewContainerLastPoz();
			}
		}));
		
		return ret.getHtml();
	}
	
	@Override
	public List<String> extract(String data)
	{
		DataArrayTeaVMImpl arr = new DataArrayTeaVMImpl((JSArray<JSObject>)JSON.parse(data));
		ArrayList<String> ret = new ArrayList<>();
		for(int i=0;i<arr.size();++i)
		{
			ret.add(arr.getString(i));
		}
		return ret;
	}

	@Override
	public String pack(List<String> elemsData)
	{
		DataArrayTeaVMImpl arr = new DataArrayTeaVMImpl();
		for(String s:elemsData)
		{
			arr.putString(s);
		}
		
		return JSON.stringify((JSObject)arr.getImpl());
	}

	@Override
	public void removeContainer(int i)
	{
		VanillaTools.remove(VanillaTools.getChildren(cont).get(i));
	}

	@Override
	public int getNoOfContainers()
	{
		return VanillaTools.getChildren(cont).getLength();
	}

	@Override
	public HtmlDataContainer<String> getNthContainer(int i)
	{
		return ImpTools.getImp(VanillaTools.getChildren(cont).get(i));
	}

	@Override
	public HtmlDataContainer<String> createNewContainerLastPoz()
	{
		HtmlDataContainer<String> n = create();
		HTMLElement add = new H("div").addChilds(n.getHtml()).style(StyleAlaCarte.MARGIN_3).getHtml();
		add.appendChild
		(
			new H("span").style(TbsStyle.BTN_DANGER, TbsGlyph.REMOVE, StyleAlaCarte.FLOAT_RIGHT).onClick(new EventListener<MouseEvent>()
			{
				@Override
				public void handleEvent(MouseEvent arg0)
				{
					VanillaTools.remove(add);
				}
			}).getHtml()
		);
		
		add.appendChild(new H("div").style(StyleAlaCarte.CLEAR_BOTH).getHtml());
		
		ImpTools.appendImp(add, n);
		cont.appendChild(add);
		
		return n;
	}
	
	protected abstract HtmlDataContainer<String> create();
}
