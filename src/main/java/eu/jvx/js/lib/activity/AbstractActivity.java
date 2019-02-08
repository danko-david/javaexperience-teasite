package eu.jvx.js.lib.activity;

import org.teavm.jso.dom.html.HTMLElement;

import eu.jvx.js.lib.HtmlActivity;
import eu.jvx.js.lib.activity.AbstractPageStarter;

public abstract class AbstractActivity implements HtmlActivity
{
	protected AbstractPageStarter ps;
	
	public AbstractActivity(AbstractPageStarter ps)
	{
		this.ps = ps;
	}
	
	@Override
	public void start()
	{
		ps.getResettedContainer().appendChild(createRootElement());
		initAfterInsert();
	}
	
	public void initAfterInsert()
	{}
	
	protected abstract HTMLElement createRootElement();
	
	public void reload()
	{}
	
	@Override
	public void destroy()
	{
		ps.getResettedContainer();
	}
}
