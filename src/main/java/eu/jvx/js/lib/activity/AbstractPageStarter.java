package eu.jvx.js.lib.activity;

import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.log.JavaExperienceLoggingFacility;
import eu.javaexperience.log.LogLevel;
import eu.javaexperience.log.Loggable;
import eu.javaexperience.log.Logger;
import eu.javaexperience.log.LoggingTools;
import eu.javaexperience.teasite.frontend.tools.TeasiteFrontendTools;
import eu.javaexperience.teavm.templatesite.common.PageStorage;
import eu.javaexperience.teavm.templatesite.frontend.PageStarter;
import eu.jvx.js.lib.HtmlActivity;
import eu.jvx.js.lib.HtmlTools;
import eu.jvx.js.lib.WrapperHtml;
import eu.jvx.js.lib.teavm.NativeJsSupportTeaVM;

public abstract class AbstractPageStarter<P extends StartablePageId> implements PageStarter<P>
{
	public static final Logger LOG = JavaExperienceLoggingFacility.getLogger(new Loggable("AbstractPageStarter"));
	
	protected final PageStorage<? extends P> pageStorage;
	
	public AbstractPageStarter(PageStorage<? extends P> storage)
	{
		this.pageStorage = storage;
		TeasiteFrontendTools.registerInfrastructurePageStater(this);
	}
	
	protected P currentPage;
	protected HtmlActivity currentActivity;
	
	public void startActivity(HtmlActivity act, P id)
	{
		currentActivity = act;
		currentPage = id;
		LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "startActivity(activity: %s, id: %s) | before invoking activity.start()", act, id);
		currentActivity.start();
	}
	
	@Override
	public void startPage(P id)
	{
		try
		{
			LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "startPage(%s)", id);
			HtmlActivity newAct = ((P)id).getActivityClass().newInstance();
			LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "startPage - instance: %s", newAct);
			startActivity(newAct, id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	static
	{
		NativeJsSupportTeaVM.init();
		new String[0].getClass().toString();
	}
	
	protected WrapperHtml root;
	
	public HTMLElement getContentContainer()
	{
		if(null == root)
		{
			root = initPage();
			addOnPageLinkHandler();
		}
		
		return (HTMLElement) root.getContent();
	}
	
	public HTMLElement getResettedContainer()
	{
		LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "getResettedContainer()");
		HTMLElement ret = getContentContainer();
		HtmlTools.removeChilds(ret);
		LoggingTools.tryLogFormat(LOG, LogLevel.DEBUG, "getResettedContainer() - after removeChilds()");
		return ret;
	}
	
	protected abstract WrapperHtml initPage();
	
	protected void addOnPageLinkHandler()
	{
		TeasiteFrontendTools.addActivityStarterListener
		(
			(HTMLElement) root.getRoot(),
			(PageStorage)pageStorage,
			this
		);
		
		TeasiteFrontendTools.addActivityHistoryBackListener((PageStorage)pageStorage, this);
	}
	
	@Override
	public P getCurrentPage()
	{
		return currentPage;
	}
	
	@Override
	public HtmlActivity getCurrentActivity()
	{
		return currentActivity;
	}
}