package eu.teasite.frontend.api.transfers;

import org.teavm.jso.JSObject;
import org.teavm.jso.ajax.ReadyStateChangeHandler;
import org.teavm.jso.ajax.XMLHttpRequest;
import org.teavm.jso.json.JSON;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.teavm.datareprez.DataObjectTeaVMImpl;

public class AjaxTransfer extends ApiPacketTransfer
{
	protected String url;
	
	public AjaxTransfer(String url)
	{
		this.url = url;
	}
	
	@Override
	protected void transmitAsync(DataObject req, SimplePublish1<DataObject> resp)
	{
		final XMLHttpRequest xhr = XMLHttpRequest.create();
		xhr.open("POST", url);
		xhr.setOnReadyStateChange
		(
			new ReadyStateChangeHandler()
			{
				@Override
				public void stateChanged()
				{
					if(XMLHttpRequest.DONE == xhr.getReadyState() && 200 == xhr.getStatus())
					{
						resp.publish(new DataObjectTeaVMImpl(JSON.parse(xhr.getResponseText())));
					}
				}
			}
		);
		
		xhr.send(JSON.stringify((JSObject)req.getImpl()));
	}
}