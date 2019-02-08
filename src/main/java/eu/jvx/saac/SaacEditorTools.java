package eu.jvx.saac;

import org.teavm.jso.JSObject;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.json.JSON;

import eu.javaexperience.collection.map.KeyVal;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.log.LogLevel;
import eu.javaexperience.log.LoggingTools;
import eu.javaexperience.teavm.datareprez.DataObjectTeaVMImpl;
import eu.javaexperience.text.StringTools;
import eu.jvx.js.lib.ImpTools;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.ui.FrontendTools;
import eu.jvx.js.lib.ui.component.func.HtmlDataContainer;
import eu.jvx.saac.SaacEditor.SaacConfig;
import eu.jvx.saac.SaacEditor.SaacMenuVisiblity;
import eu.jvx.saac.SaacEditor.SaacRootType;

public class SaacEditorTools
{
	public static SaacConfig createSimpleEmbeddedConfig(SaacApi api, String fixture, Integer fixtureIndex)
	{
		SaacConfig config = new SaacConfig();
		
		config.api = api;
		config.embedConsole = false;
		
		if(null != fixture && null != fixtureIndex)
		{
			config.rootType = new SaacRootType(fixture, fixtureIndex);
		}
		
		SaacMenuVisiblity show = config.show;
		show.browser = false;
		show.console = false;
		show.execute = false;
		show.save = false;
		
		return config;
	}

	public static HtmlDataContainer<String> createEmbeddedContainer(String fieldName, SaacApi api, String fixture, Integer fixtureIndex, Object value)
	{
		return createEmbeddedContainerKv(fieldName, api, fixture, fixtureIndex, value).getKey();
	}
	
	public static KeyVal<HtmlDataContainer<String>, SaacEditor> createEmbeddedContainerKv(String fieldName, SaacApi api, String fixture, Integer fixtureIndex, Object value)
	{
		final HTMLElement container = new H("div").getHtml();
		SaacEditor editor = SaacEditor.saac_createEntryPoint(container, SaacEditorTools.createSimpleEmbeddedConfig(api, fixture, fixtureIndex));
		
		HtmlDataContainer<String> ret = new HtmlDataContainer<String>()
		{
			@Override
			protected HTMLElement construct()
			{
				return container;
			}

			@Override
			public String getData()
			{
				return JSON.stringify((JSObject) editor.serialize().getImpl());
			}

			@Override
			public void setData(String data)
			{
				try
				{
					if(StringTools.isNullOrTrimEmpty(data))
					{
						return;
					}
					editor.saac_restore_function(new DataObjectTeaVMImpl(JSON.parse(data)));
				}
				catch(Exception e)
				{
					LoggingTools.tryLogFormatException(FrontendTools.LOG, LogLevel.ERROR, e, "Error while restore function editor state ine SaacEditorTools");
				}
			}
		};
		
		editor.ensureInitalized(new SimplePublish1<SaacEditor>()
		{
			@Override
			public void publish(SaacEditor a)
			{
				if(null != value)
				{
					String val = (String) value;
					if(null != val && val.length() > 0)
					{
						try
						{
							ret.setData(val);
						}
						catch(Throwable t){}
					}
				}
			}
		});
		
		if(null != fieldName)
		{
			ImpTools.appendImp(FrontendTools.addHiddenSerializer(fieldName, container, ret), ret);
		}
		return new KeyVal(ret, editor);
	}
}
