package eu.jvx.js.lib.ui.component.input;

import static eu.jvx.js.lib.bindings.VanillaTools.inlineCreateElement;

import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLOptionElement;
import org.teavm.jso.dom.xml.Element;

import eu.jvx.js.lib.ImpersonalisedHtml;
import eu.jvx.js.lib.bindings.VanillaTools;

public class SelectOption implements ImpersonalisedHtml
{
	protected SelectList owner;
	protected HTMLOptionElement option;
	
	
	protected SelectOption(SelectList selectList, String value, String label)
	{
		this.owner = selectList;
		option = (HTMLOptionElement) inlineCreateElement
		(
			"option",
			"value", value,
			"#text", label
		);
	}
	
	protected SelectOption(SelectList selectList, String value, HTMLElement label)
	{
		this.owner = selectList;
		option = (HTMLOptionElement) inlineCreateElement
		(
			"option",
			"value", value
		);
		option.appendChild(label);
	}

	public boolean isSelected()
	{
		return true == option.isSelected();
	}
	
	public void setEnabled(boolean enabled)
	{
		if(isSelected())
		{
			owner.resetSelection();
		}
		option.setDisabled(!enabled);
	}
	
	public void setHidden(boolean hidden)
	{
		setEnabled(!hidden);
		if(hidden)
		{
			option.getStyle().setProperty("display", "hidden");
		}
		else
		{
			option.getStyle().setProperty("display", "");
		}
	}
	
	public void remove()
	{
		owner.remove(this);
		VanillaTools.remove(option);
	}
	
	public void deselect()
	{
		option.setSelected(false);
	}
	
	public void selectThis()
	{
		owner.resetSelection();
		option.setSelected(true);
	}
	
	public String getValue()
	{
		return option.getValue();
	}

	public String getLabel()
	{
		return option.getInnerHTML();
	}
	
	@Override
	public Object getImpersonator()
	{
		return this;
	}

	@Override
	public Element getHtml()
	{
		return option;
	}
}
