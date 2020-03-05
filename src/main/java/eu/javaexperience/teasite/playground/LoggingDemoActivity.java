package eu.javaexperience.teasite.playground;

import eu.javaexperience.collection.enumerations.EnumTools;
import eu.javaexperience.log.JavaExperienceLoggingFacility;
import eu.javaexperience.log.LogLevel;
import eu.javaexperience.log.Loggable;
import eu.javaexperience.log.Logger;
import eu.javaexperience.log.LoggingTools;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.logging.LoggingSettings;

public class LoggingDemoActivity implements PlaygroundActivity
{
	@Override
	public String getName()
	{
		return "Logging";
	}

	@Override
	public String getUrlActivity()
	{
		return "logging";
	}
	
	protected static final Logger LOG = JavaExperienceLoggingFacility.getLogger(new Loggable("LoggingDemoActivity"));
	
	protected static final Logger LOG_DEMO = JavaExperienceLoggingFacility.getLogger(new Loggable("DemoLogger"));
	
	protected static class LogTester
	{
		public H root = new H("div");
		
		public H logger = new H("select");
		
		public H level = LoggingSettings.createLogLevelChooser();
		
		public H format = new H("input").attrs("style", "width:250px", "value", "Logging test: `%s`, `%s`, `%s`, `%s`");
		
		public H invoke = new H("button").attrs("#text", "Log");
		
		public LogTester()
		{
			logger.addChilds
			(
				new H("option").attrs("#text", "LoggingDemoActivity", "value", "LOG"),
				new H("option").attrs("#text", "DemoLogger", "value", "LOG_DEMO")
			);
			
			invoke.onClick(e->
			{
				Logger log = "LOG".equals(VanillaTools.getContent(logger))?LOG:LOG_DEMO;
				LogLevel l = EnumTools.recogniseSymbol(LogLevel.class, VanillaTools.getContent(level));
				String f = VanillaTools.getContent(format);
				
				if(null != log && null != l && null != f)
				{
					LoggingTools.tryLogFormat(log, l, f, "Hello", 13.5, true, 'C');
				}
			});
			
			root.addChilds
			(
				logger,
				level,
				format,
				new H("span").attrs("#text", "\"Hello\", 13.5, true, 'C'"),
				invoke
			);
		}
	}
	
	@Override
	public void start(H container)
	{
		LoggingSettings.boundLoggingSettingsHotkeyCtrlShiftL();
		
		container.addChilds
		(
			new H("span").attrs("#text", "To add javascript console as log output click this: "),
			new H("button").attrs("#text", "Add console output").onClick(e->JavaExperienceLoggingFacility.addStdOut()),
			new H("br"),
			new H("br"),
			new H("button").attrs("#text", "Open log settings").onClick(e->LoggingSettings.openLoggingSettings()),
			new H("span").attrs("#text", "Also bound to Ctrl+Shift+L"),
			new H("br"),
			new H("br"),
			new LogTester().root
			//TODO add test log exception
		);
	}

}
