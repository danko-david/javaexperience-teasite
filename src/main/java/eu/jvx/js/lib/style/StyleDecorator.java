package eu.jvx.js.lib.style;

import org.teavm.jso.dom.html.HTMLElement;

public interface StyleDecorator
{
	public void setStyle(HTMLElement elem, boolean trueSet_falseRemove);
	public boolean hasAll(HTMLElement elem);
	public boolean hasAny(HTMLElement elem);
	public default void toggleStyles(HTMLElement elem)
	{
		setStyle(elem, !hasAll(elem));
	}
}
