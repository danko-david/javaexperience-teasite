package eu.jvx.js.lib.ui.component.func;

import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Element;

import eu.jvx.js.lib.HtmlTools;
import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.ImpersonalisedHtml;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.style.StyleTools.StyleAlaCarte;

public class ViewOrEdit<D> implements ImpersonalisedHtml
{
	protected HTMLElement root = ImpTools.appendImp(new H("div").style(StyleAlaCarte.DISPLAY_INLINE_BLOCK).getHtml(), this);
	
	protected HtmlDataContainer<D> view;
	protected HtmlDataContainer<D> edit;
	
	public ViewOrEdit(D value, HtmlDataContainer<D> view, HtmlDataContainer<D> edit)
	{
		this.view = view;
		this.edit = edit;
		root.appendChild(view.getHtml());
		root.appendChild(edit.getHtml());
		HtmlTools.setVisible((HTMLElement) view.getHtml(), true);
		HtmlTools.setVisible((HTMLElement) edit.getHtml(), false);
		view.setData(value);
	}
	
	@Override
	public Object getImpersonator()
	{
		return this;
	}

	@Override
	public Element getHtml()
	{
		return root;
	}
	
	public void setValue(D value)
	{
		view.setData(value);
		edit.setData(value);
	}
	
	public D getValue()
	{
		if(isModeView())
		{
			return view.getData();
		}
		else
		{
			return edit.getData();
		}
	}
	
	public boolean isModeView()
	{
		return HtmlTools.isVisible((HTMLElement) view.getHtml());
	}
	
	public ViewOrEdit<D> enterMode(boolean toView, boolean copyValue)
	{
		boolean now = isModeView();
		if(now != toView)
		{
			D data = null;
			if(now)
			{
				data = view.getData();
				HtmlTools.setVisible((HTMLElement) view.getHtml(), false);
				HtmlTools.setVisible((HTMLElement) edit.getHtml(), true);
				if(copyValue)
				{
					edit.setData(data);
				}
			}
			else
			{
				data = edit.getData();
				HtmlTools.setVisible((HTMLElement) view.getHtml(), true);
				HtmlTools.setVisible((HTMLElement) edit.getHtml(), false);
				if(copyValue)
				{
					view.setData(data);
				}
			}
		}
		
		return this;
	}

	public HtmlDataContainer<D> getView()
	{
		return view;
	}
	
	public HtmlDataContainer<D> getEdit()
	{
		return edit;
	}
}
