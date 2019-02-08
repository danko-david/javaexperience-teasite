package eu.jvx.js.lib.activity;

import static eu.jvx.js.lib.bindings.H.H;

import java.util.ArrayList;
import java.util.Collection;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.patterns.creational.builder.PublisherBuilderTools;
import eu.javaexperience.teasite.frontend.tools.TeasiteFrontendTools;
import eu.javaexperience.teavm.templatesite.common.PageStorage;
import eu.jvx.js.lib.SimpleWrapperHtml;
import eu.jvx.js.lib.WrapperHtml;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.tbs.ui.TbsLayoutTools;

public class DefaultPageStarter<P extends StartablePageId> extends AbstractPageStarter<P>
{
	protected String siteTitle;
	
	public DefaultPageStarter(PageStorage<P> storage, String siteTitle)
	{
		super(storage);
		this.siteTitle = siteTitle;
		getResettedContainer();
	}
	
	protected boolean startDefaultActivity()
	{
		HTMLElement el = Window.current().getDocument().querySelector(".page_identifier_data");
		if(null != el)
		{
			if(tryStartActivity(el.getAttribute("data-page_id")))
			{
				return true;
			}
		}
		
		return false;
	}
	
	protected boolean tryStartActivity(String act)
	{
		if(null != act)
		{
			P activity = pageStorage.getById(act);
			if(null != activity)
			{
				startPage(activity);
				return true;
			}
		}
		return false;
	}

	protected WrapperHtml initPage()
	{
		HTMLElement contentContainer = H("div").attrs("id", "content_container", "class", "well").getHtml();
		HTMLElement mainFrame;
		
		VanillaTools.addToHeader(H("meta", "content", "width=device-width, initial-scale=1.0", "name", "viewport").getHtml());
		
		H(Window.current().getDocument().getBody()).
		addChilds
		(
			H("div").attrs("class", "container").addChilds
			(
				H("div").attrs("class", "row").addChilds
				(
					mainFrame = H("div").attrs("id", "site_mainframe").addChilds
					(
						null == siteTitle?null:H("div").attrs("class", "alert alert-success text-center", "role", "alert").addChilds(H("h1").attrs("#text", siteTitle)),
						H("header").attrs("class", "alert").addChilds
						(
							renderNav()
						),
						contentContainer
					).getHtml()
				)
			)
		);
		
		return new SimpleWrapperHtml(mainFrame, contentContainer);
	}

	public Collection<HTMLElement> getLinks()
	{
		ArrayList<HTMLElement> ret = new ArrayList<>();
		for(P p:pageStorage.getPages())
		{
			ret.add(TeasiteFrontendTools.createActivityLink(p, p.getLabel(), "btn btn-default"));
		}
		return ret;
	}
	
	protected HTMLElement renderNav()
	{
		return PublisherBuilderTools.buildAll(TbsLayoutTools.buildHorizontalPills(), null, getLinks());
	}
}
