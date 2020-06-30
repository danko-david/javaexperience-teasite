package eu.jvx.js.trigger;

import java.util.HashMap;
import java.util.Map;

import org.teavm.jso.JSObject;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.json.JSON;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.log.JavaExperienceLoggingFacility;
import eu.javaexperience.log.LogLevel;
import eu.javaexperience.log.Loggable;
import eu.javaexperience.log.Logger;
import eu.javaexperience.log.LoggingTools;
import eu.javaexperience.teavm.datareprez.DataObjectTeaVMImpl;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.bindings.VanillaTools.EtcNodeSupport;

public class TriggerRegistry
{
	protected static Logger LOG = JavaExperienceLoggingFacility.getLogger(new Loggable("TriggerRegistry"));
	
	protected String namespace;
	
	protected Map<String, SimplePublish1<DataObject>> triggers = new HashMap<>();
	
	protected EventListener<Event> listener =  new EventListener<Event>()
	{
		@Override
		public void handleEvent(Event e)
		{
			DataObjectTeaVMImpl det = new DataObjectTeaVMImpl(((EtcNodeSupport)e).getDetail());
			String name = det.getString("name");
			if(null != name)
			{
				SimplePublish1<DataObject> call = triggers.get(name);
				if(null != call)
				{
					call.publish(det);
				}
				else
				{
					LoggingTools.tryLogFormat(LOG, LogLevel.WARNING, "TeaVm trigger not found: %s", name);
				}
			}
			else
			{
				LoggingTools.tryLogFormat(LOG, LogLevel.WARNING, "No name specified in TeaVm trigger (CustomEvent.detail.name). CustomEvent.detail: %s", JSON.stringify((JSObject) det.getImpl()));
			}
		}
	};
	
	public TriggerRegistry()
	{
		VanillaTools.getDom().addEventListener(namespace, listener);
	}
	
	public void uninstall()
	{
		VanillaTools.getDom().removeEventListener(namespace, listener);
	}
}
