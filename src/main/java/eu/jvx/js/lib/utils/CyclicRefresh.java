package eu.jvx.js.lib.utils;

import org.teavm.jso.browser.TimerHandler;
import org.teavm.jso.browser.Window;

import eu.jvx.js.lib.Refreshable;

public class CyclicRefresh
{
	protected Refreshable refreshable;
	
	public int refreshIntervalMs = 1000;
	public Integer th;
	protected TimerHandler refresh;
	
	public CyclicRefresh(final Refreshable refreshable)
	{
		this.refreshable = refreshable;
		
		refresh = new TimerHandler()
		{
			
			@Override
			public void onTimer()
			{
				refreshable.refresh();
				
			}
		};
	}
	
	public void setUpdateInterval(Integer ms)
	{
		
		if(null != th)
		{
			Window.clearTimeout(th);
		}
		
		if(null != ms)
		{
			refreshIntervalMs = ms;
			th = Window.setInterval(refresh, refreshIntervalMs);
		}
	}

	public void startRefresh()
	{
		this.setUpdateInterval(refreshIntervalMs);
	}
	
	public void stopRefresh()
	{
		this.setUpdateInterval(null);
	}
}
