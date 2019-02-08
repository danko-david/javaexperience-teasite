package eu.jvx.js.lib.ui.component.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Element;

import eu.javaexperience.interfaces.simple.getBy.GetBy1;
import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.ImpersonalisedHtml;
import eu.jvx.js.lib.TeaVmTools;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.ui.component.func.HtmlDataContainer;

public class SelectList extends HtmlDataContainer<String> implements ImpersonalisedHtml
{
	protected List<SelectOption> opts;
	
	public SelectList()
	{
		opts = new ArrayList<>();
	}
	
	public static SelectList createFrom
	(
		List<Map<String, Object>> data,
		GetBy1<String, Map<String,Object>> getValue,
		GetBy1<String, Map<String,Object>> getLabel
	)
	{
		SelectList ret = new SelectList();
		
		for(int i=0;i<data.size();++i)
		{
			Map<String, Object> d = data.get(i);
			String value = getValue.getBy(d);
			String label = getLabel.getBy(d);
			
			if(null != value && null != label)
			{
				ret.addOption(value, label);
			}
		}
		return ret;
	}
	
	public SelectOption getOptionByValue(String name)
	{
		for(int i=0;i<opts.size();++i)
		{
			SelectOption so = opts.get(i);
			if(name.equals(so.getValue()))
			{
				return so;
			}
		}
		
		return null;
	}
	
	public SelectOption setSelectedValue(String value)
	{
		SelectOption opt = getOptionByValue(value);
		if(null != opt)
		{
			opt.selectThis();
		}
		
		return opt;
	}
	
	public SelectOption addOption(String value, String label)
	{
		if(null != getOptionByValue(value))
		{
			return null;
		}
		
		SelectOption opt = new SelectOption(this, value, label);
		opts.add(opt);
		root.appendChild(opt.option);
		return opt;
	}	
	
	public SelectOption addOption(String value, HTMLElement label)
	{
		if(null != getOptionByValue(value))
		{
			return null;
		}
		
		SelectOption opt = new SelectOption(this, value, label);
		opts.add(opt);
		root.appendChild(opt.option);
		return opt;
	}
	
	public void resetSelection()
	{
		for(int i=0;i<opts.size();++i)
		{
			opts.get(i).deselect();
		}
	}
	
	public SelectOption getSelectedItem()
	{
		for(int i=0;i<opts.size();++i)
		{
			SelectOption so = opts.get(i);
			if(so.isSelected())
			{
				return so;
			}
		}
		
		return null;
	}
	
	public List<SelectOption> getOptions()
	{
		return opts;
	}
	
	@Override
	public Object getImpersonator()
	{
		return this;
	}

	@Override
	public HTMLElement getHtml()
	{
		return root;
	}

	protected void remove(SelectOption selectOption)
	{
		for(int i=0;i<opts.size();++i)
		{
			if(opts.get(i) == selectOption)
			{
				opts.remove(i);
				root.removeChild(selectOption.option);
			}
		}
	}

	public void clear()
	{
		opts.clear();
		VanillaTools.removeAllChild((HTMLElement) root);
	}

	@Override
	protected HTMLElement construct()
	{
		return ImpTools.appendImp(VanillaTools.inlineCreateElement("select"), this);
	}

	@Override
	public String getData()
	{
		SelectOption si = getSelectedItem();
		if(null != si)
		{
			return si.getValue();
		}
		return null;
	}

	@Override
	public void setData(String data)
	{
		SelectOption opt = getOptionByValue(data);
		if(null != opt)
		{
			opt.selectThis();
		}
	}
	
	static
	{
		if(TeaVmTools.isUrchin())
		{
			SelectList sl = new SelectList();
			sl.addOption("something", "something");
			sl.setData("something");
			System.out.println(sl.getData());
		}
	}
}
