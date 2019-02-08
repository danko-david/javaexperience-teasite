package eu.jvx.js.lib.ui;

import java.util.Collection;

import org.teavm.jso.JSObject;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLInputElement;

import eu.javaexperience.collection.map.PublisherMap;
import eu.javaexperience.datareprez.DataCommon;
import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.SimpleCall;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.javaexperience.log.JavaExperienceLoggingFacility;
import eu.javaexperience.log.LogLevel;
import eu.javaexperience.log.Loggable;
import eu.javaexperience.log.Logger;
import eu.javaexperience.log.LoggingDetailLevel;
import eu.javaexperience.log.LoggingTools;
import eu.javaexperience.teavm.datareprez.DataObjectTeaVMImpl;
import eu.javaexperience.verify.LanguageTranslatableValidationEntry;
import eu.jvx.js.lib.HtmlTools;
import eu.jvx.js.lib.NativeJsSupport;
import eu.jvx.js.lib.TeaVmTools;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.ui.component.func.HtmlDataContainer;

public class FrontendTools
{
	public static final Logger LOG = JavaExperienceLoggingFacility.getLogger(new Loggable("FrontendTools"));
	
	public static void bindFormClickTrigger(HTMLElement area, String triggerSelector, SimplePublish1<DataObject> areaData)
	{
		bindFormTrigger(area, triggerSelector, "click", areaData);
	}
	
	public static DataObject serializeInputsInArea(HTMLElement area)
	{
		return serializeInputsInArea(area, DataObjectTeaVMImpl.INSTANCE);
	}
	
	public static DataObject serializeInputsInArea(HTMLElement area, DataCommon proto)
	{
		final DataObject obj = proto.newObjectInstance();
		HtmlTools.serializeInputs(new PublisherMap<String, String>()
		{
			@Override
			public String put(String key, String value)
			{
				if(!obj.has(key))//XXX hotfix
				{
					obj.putString(key, value);
				}
				return null;
			}
		}, area);
		
		return obj;
	}
	
	public static void bindFormTrigger(final HTMLElement area, String triggerSelector, String action, final SimplePublish1<DataObject> areaData)
	{
		VanillaTools.bindListenerToArea(area, action, triggerSelector, new EventListener<Event>()
		{
			@Override
			public void handleEvent(Event arg0)
			{
				final DataObject obj = serializeInputsInArea(area, DataObjectTeaVMImpl.INSTANCE);
				areaData.publish(obj);
			}
		});
	}
	
	public static <T extends Event> void processEventWithThread(EventListener<T> listener, T event)
	{
		if(TeaVmTools.isValuable((JSObject) NativeJsSupport.getSupport().eval("$rt_nativeThread()")))
		{
			LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "processEventWithThread: dispatch on this thread");
			listener.handleEvent(event);
		}
		else
		{
			LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "processEventWithThread: dispatch on NEW thread");
			new Thread()
			{
				public void run()
				{
					listener.handleEvent(event);
				};
			}.start();
		}
	}
	
	public static <T extends Event> EventListener<T> wrapProcessEventWithThread(EventListener<T> listener)
	{
		return new EventListener<T>()
		{
			@Override
			public void handleEvent(T arg0)
			{
				if(TeaVmTools.isValuable((JSObject) NativeJsSupport.getSupport().eval("$rt_nativeThread()")))
				{
					LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "processEventWithThread: dispatch on this thread");
					listener.handleEvent(arg0);
				}
				else
				{
					LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "processEventWithThread: dispatch on NEW thread");
					new Thread()
					{
						public void run()
						{
							listener.handleEvent(arg0);
						};
					}.start();
				}
			}
		};
	}

	public static void runOnThread(SimpleCall simpleCall)
	{
		if(TeaVmTools.isValuable((JSObject) NativeJsSupport.getSupport().eval("$rt_nativeThread()")))
		{
			LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "processEventWithThread: dispatch on this thread");
			simpleCall.call();
		}
		else
		{
			new Thread()
			{
				public void run()
				{
					LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "processEventWithThread: dispatch on NEW thread");
					simpleCall.call();
				};
			}.start();
		}
	}
	
	public static <A,B> SimplePublish2<A, B> wrapDispatchWithThread(final SimplePublish2<A, B> param)
	{
		return new SimplePublish2<A, B>()
		{
			@Override
			public void publish(A a, B b)
			{
				if(TeaVmTools.isValuable((JSObject) NativeJsSupport.getSupport().eval("$rt_nativeThread()")))
				{
					LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "processEventWithThread: dispatch on this thread");
					param.publish(a, b);
				}
				else
				{
					LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "processEventWithThread: dispatch on NEW thread");
					new Thread()
					{
						public void run()
						{
							param.publish(a, b);
						};
					}.start();
				}
			}
		};
	}
	
	public static String renderEntry(LanguageTranslatableValidationEntry ent)
	{
		return ent.getDescription().propertyName+" "+ent.getDescription().translationSymbol;
	}
	
	public static String renderMessages
	(
		Collection<LanguageTranslatableValidationEntry> reportEntries
	)
	{
		StringBuilder sb = new StringBuilder();
		for(LanguageTranslatableValidationEntry e:reportEntries)
		{
			sb.append(renderEntry(e));
		}
		return sb.toString();
	}
	
	public static void log(LoggingDetailLevel level, String format, Object... vars)
	{
		LoggingTools.tryLogFormat(LOG, level, format, vars);
	}
	
	public static void log(LoggingDetailLevel level, Throwable t, String format, Object... vars)
	{
		LoggingTools.tryLogFormatException(LOG, level, t, format, vars);
	}

	public static HTMLInputElement addHiddenSerializer(String fieldName, HTMLElement container, HtmlDataContainer<String> ret)
	{
		H in = new H("input").attrs("name", fieldName, "type", "hidden"); 
		in.on
		(
			"serialize",
			new EventListener<Event>()
			{
				@Override
				public void handleEvent(Event arg0)
				{
					String ser = ret.getData();
					((HTMLInputElement)in.getHtml()).setValue(ser);
				}
			}
		);
		container.appendChild(in.getHtml());
		
		return (HTMLInputElement) in.getHtml();
	}
}
