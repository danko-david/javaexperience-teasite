package eu.jvx.js.lib.ui.component.func;

import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Element;

import eu.jvx.js.lib.ImpersonalisedHtml;

public abstract class ToggleState<T> implements ImpersonalisedHtml
{
	public HTMLElement root; 
	
	protected T state;
	
	public ToggleState(HTMLElement root, T startState)
	{
		this.root = root;
		this.state = startState;
		updateState();
	}
	
	public void toggle()
	{
		state = toggleState();
		updateState();
	}
	
	protected abstract T toggleState();
	
	public T getState()
	{
		return state;
	}
	
	public void forceState(T state)
	{
		this.state = state;
		updateState();
	}
	
	public abstract void updateState();


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
}