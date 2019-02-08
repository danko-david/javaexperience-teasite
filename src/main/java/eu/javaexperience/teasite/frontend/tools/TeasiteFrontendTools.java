package eu.javaexperience.teasite.frontend.tools;

import static eu.jvx.js.lib.bindings.VanillaTools.inlineCreateElement;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.log.JavaExperienceLoggingFacility;
import eu.javaexperience.log.LogLevel;
import eu.javaexperience.log.Loggable;
import eu.javaexperience.log.Logger;
import eu.javaexperience.log.LoggingTools;
import eu.javaexperience.semantic.references.MayNull;
import eu.javaexperience.teavm.templatesite.common.PageId;
import eu.javaexperience.teavm.templatesite.common.PageStorage;
import eu.javaexperience.teavm.templatesite.frontend.PageStarter;
import eu.jvx.js.lib.HtmlActivity;
import eu.jvx.js.lib.activity.AbstractPageStarter;
import eu.jvx.js.lib.activity.StartablePageId;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.history.HistoryTools;

public class TeasiteFrontendTools
{
	public static final Logger LOG = JavaExperienceLoggingFacility.getLogger(new Loggable("TeasiteFrontendTools"));
	
	protected static AbstractPageStarter INFRASTRUCTURE_PAGE_STARTER = null;
	
	public static void registerInfrastructurePageStater(AbstractPageStarter starter)
	{
		LoggingTools.tryLogFormat(LOG, LogLevel.INFO, "registerInfrastructurePageStater: %s", starter);
		INFRASTRUCTURE_PAGE_STARTER = starter;
	}
	
	public static AbstractPageStarter getInfrastructurePageStater()
	{
		return INFRASTRUCTURE_PAGE_STARTER;
	}
	
	public static <T extends StartablePageId> void addActivityHistoryBackListener
	(
		final PageStorage<T> storage,
		final PageStarter<T> starter
	)
	{
		Window.current().addEventListener("popstate", new EventListener<Event>()
		{
			@Override
			public void handleEvent(Event e)
			{
				actualisePage(storage, starter);
			}
		});
	}
	
	protected static <T extends StartablePageId> void actualisePage
	(
		PageStorage<? extends T> storage,
		PageStarter<T> starter
	)
	{
		String path = HistoryTools.getPath();
		T id = storage.getByUrl(path);
		T cp = starter.getCurrentPage();
		HtmlActivity ca = starter.getCurrentActivity();
		LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "actualisePage: id: %s, cp: %s, ca: %s", id, cp, ca);
		if(null != id)
		{
			if(null != cp && null != ca && id.equals(cp))
			{
				LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "actualizing page: reload %s", id);
				ca.reload();
			}
			else
			{
				LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "actualizing page: start new %s", id);
				starter.startPage(id);
			}
		}
	}
	
	public static <T extends StartablePageId> void addActivityStarterListener
	(
		HTMLElement ROOT,
		final PageStorage<T> storage,
		final PageStarter<T> starter
	)
	{
		ROOT.addEventListener("click", new EventListener<Event>()
		{
			@Override
			public void handleEvent(Event e)
			{
				HTMLElement tar = (HTMLElement) e.getTarget();
				if(VanillaTools.getClassList(tar).contains("teavm_onpage_activity_link"))
				{
					e.stopPropagation();
					e.preventDefault();
					
					String id = tar.getAttribute("data-onpage_activity");
					LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "ActivityStarterListener - activity: %s", id);
					if(null != id)
					{
						T pid = storage.getById(id);
						if(null != pid)
						{
							HistoryTools.pushUrl(pid.url());
							actualisePage(storage, starter);
							return;
						}
						else
						{
							Window.current().alert("Unrecognisable activity: "+id);
						}
					}
					else
					{
						Window.current().alert("No activity specified by the link");
					}
				}
			}
		});
	}
	
	public static HTMLElement createActivityLink(PageId page, String label, @MayNull String cssClasses)
	{
		return inlineCreateElement
		(
			"a",
			"#text", label,
			"href", "#",
			"class", "teavm_onpage_activity_link"+(null == cssClasses?"":" "+cssClasses),
			"data-onpage_activity", page.getId()
		);
	}
}
