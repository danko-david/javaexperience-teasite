package eu.jvx.js.lib;

import org.teavm.jso.JSObject;
import org.teavm.jso.ajax.ReadyStateChangeHandler;
import org.teavm.jso.ajax.XMLHttpRequest;

import eu.javaexperience.interfaces.simple.publish.SimplePublish1;

public class AjaxTools
{
	public static void onReady(final XMLHttpRequest xhr, final SimplePublish1<JSObject> pub)
	{
		xhr.setOnReadyStateChange
		(
			new ReadyStateChangeHandler()
			{
				@Override
				public void stateChanged()
				{
					if(XMLHttpRequest.DONE == xhr.getReadyState() && 200 == xhr.getStatus())
					{
						pub.publish(xhr.getResponse());
					}
				}
			}
		);
	}
}
