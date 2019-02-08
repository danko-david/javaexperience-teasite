package eu.jvx.js.lib.ui.component.func;

import org.teavm.jso.dom.html.HTMLElement;

import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.ImpersonalisedHtml;
import eu.jvx.js.lib.TeaVmTools;
import eu.jvx.js.lib.ui.component.input.SelectList;

public abstract class HtmlDataContainer<D> implements ImpersonalisedHtml
{
	protected HTMLElement root;
	protected abstract HTMLElement construct();
	
	public HtmlDataContainer()
	{
		root = construct();
		ImpTools.appendImp(root, this);
	}
	
	public abstract D getData();
	public abstract void setData(D data);
	
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
	
	static
	{
		if(TeaVmTools.isUrchin())
		{
			HtmlDataContainer<String> hdc = new SelectList();
			System.out.println(hdc.getData());
			hdc.setData("something");
		}
	}
}
