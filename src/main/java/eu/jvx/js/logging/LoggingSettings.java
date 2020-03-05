package eu.jvx.js.logging;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.KeyboardEvent;
import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.collection.enumerations.EnumTools;
import eu.javaexperience.log.JavaExperienceLoggingFacility;
import eu.javaexperience.log.LogLevel;
import eu.jvx.js.lib.HtmlTools;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.javaexperience.log.Logger;
import eu.javaexperience.log.LoggingDetailLevel;

public class LoggingSettings
{
	protected static class LoggerSettings
	{
		public H root = new H("div").attrs
		(
			"style",
			"margin-bottom: 4px;text-align: right;border-bottom: 1px solid black;padding: 4px;"
		);
		
		protected Logger logger;
		
		protected H level = createLogLevelChooser();
		protected H customLevel = new H("span");
		
		protected H priority = new H("span");
		
		protected void setLevel(LogLevel ll)
		{
			logger.setLogLevel(ll);
			VanillaTools.setContent(level.getHtml(), ll.getLabel());
			priority.attrs("#text", String.valueOf(ll.getLevel()));
			HtmlTools.setVisible(customLevel.getHtml(), false);
			HtmlTools.setVisible(level.getHtml(), true);
		}
		
		public LoggerSettings(Logger log)
		{
			this.logger = log;
			root.addChilds
			(
				new H("span").attrs("#text", log.getFacilityName()).attrs("style", ""),
				level,
				customLevel,
				priority
			);
			
			LoggingDetailLevel l = log.getLogLevel();
			if(l instanceof LogLevel)
			{
				VanillaTools.setContent(level.getHtml(), ((LogLevel) l).name());
				HtmlTools.setVisible(customLevel.getHtml(), false);
			}
			else
			{
				customLevel.attrs("#text", l.getLabel());
				HtmlTools.setVisible(level.getHtml(), false);
			}
			
			priority.attrs("#text", String.valueOf(l.getLevel()));
			
			level.on
			(
				"change",
				e->
				{
					String lvl = VanillaTools.getContent(level.getHtml());
					LogLevel ll = EnumTools.recogniseSymbol(LogLevel.class, lvl);
					if(null != ll)
					{
						setLevel(ll);
					}
				}
			);
		}
	}
	
	public static H createLogLevelChooser()
	{
		H ret = new H("select").attrs("style", "margin:6px");
		for(LogLevel l:LogLevel.values())
		{
			ret.addChilds(new H("option").attrs("#text", l.name(), "value", l.name()));
		}
		return ret;
	}
	
	public static void openLoggingSettings()
	{
		Window w = Window.current().open("", "", "width=400,height=500");
		HtmlTools.setPageTitle(w.getDocument(), "Javaexperience logging facility settings");
		
		H b = new H(w.getDocument().getBody());
		
		H loggersContainer = new H("div");
		
		Set<Logger> added = new HashSet<>();
		List<LoggerSettings> regLoggers = new ArrayList<>();
		
		H refresh = new H("button").attrs("#text", "Refresh list of loggers").on
		(
			"click",
			e->
			{
				List<Logger> loggers = new ArrayList<>();
				JavaExperienceLoggingFacility.listIssuedLoggers(loggers);
				for(Logger l:loggers)
				{
					if(added.add(l))
					{
						LoggerSettings ls = new LoggerSettings(l);
						regLoggers.add(ls);
						loggersContainer.addChilds(ls.root);
					}
				}
			}
		);
		
		H setAllLevel = new H("div");
		{
			H level = createLogLevelChooser();
			LoggingDetailLevel ll = JavaExperienceLoggingFacility.getDefaultLogLevel();
			
			VanillaTools.setContent(level.getHtml(), ll.getLabel());
			
			setAllLevel.addChilds
			(
				new H("span").attrs("#text", "Set all to level:"),
				level,
				new H("button").attrs("#text", "Set").onClick
				(
					e->
					{
						String cnt = VanillaTools.getContent(level.getHtml());
						LogLevel lvl = EnumTools.recogniseSymbol(LogLevel.class, cnt);
						if(null != lvl)
						{
							JavaExperienceLoggingFacility.setAllFacilityLoglevel(lvl);
							JavaExperienceLoggingFacility.setFutureDefaultLoglevel(lvl);
							for(LoggerSettings l:regLoggers)
							{
								l.setLevel(lvl);
							}
						}
					}
				)
			);
		}
		
		b.addChilds
		(
			new H("h4").attrs("#text", "List of loggers"),
			new H("br"),
			loggersContainer,
			new H("div").addChilds(refresh),
			setAllLevel
		);
		
		refresh.getHtml().click();
	}
	
	protected static boolean KEY_BOUND = false;
	
	public static void boundLoggingSettingsHotkeyCtrlShiftL()
	{
		if(!KEY_BOUND)
		{
			KEY_BOUND = true;
			new H((HTMLElement)VanillaTools.getDom()).on("keyup", e->
			{
				KeyboardEvent ke = (KeyboardEvent) e;
				if(ke.isCtrlKey() && "L".equals(ke.getKey()))
				{
					openLoggingSettings();
				}
			});
		}
	}
}
