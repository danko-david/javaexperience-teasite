package eu.teasite.frontend.api.transfers;

import org.teavm.jso.websocket.WebSocket;

import eu.javaexperience.patterns.behavioral.mediator.EventMediator;
import eu.javaexperience.reflect.Mirror;

public class ManagedWebSocket
{
	protected String url;
	protected WebSocket websocket;
	
	public ManagedWebSocket(String url)
	{
		websocket = WebSocket.create(url);
		websocket.onOpen(msg->onConnectionStateChange(true));
		websocket.onClose(msg->onConnectionStateChange(false));
	}
	
	protected Boolean connected;
	
	private void onConnectionStateChange(boolean b)
	{
		connected = b;
		connectionStateChange.dispatchEvent(connected);
	}
	
	protected EventMediator<Boolean> connectionStateChange = new EventMediator<>();
	
	protected static void sleep(int t)
	{
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			Mirror.propagateAnyway(e);
		}
	}
	
	public boolean waitConnect()
	{
		while(true)
		{
			sleep(100);
			synchronized (this)
			{
				if(null != connected)
				{
					return connected;
				}
			}
		}
	}
	
	public WebSocket getWebSocket()
	{
		return websocket;
	}
	
	public static ManagedWebSocket openConnection(String url)
	{
		ManagedWebSocket ret = new ManagedWebSocket(url);
		boolean conn = ret.waitConnect();
		if(conn)
		{
			return ret;
		}
		return null;
	}
	
	public EventMediator<Boolean> getConnectionStateListener()
	{
		return connectionStateChange;
	}
	
}
