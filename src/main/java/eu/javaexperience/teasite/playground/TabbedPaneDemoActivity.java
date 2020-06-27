package eu.javaexperience.teasite.playground;

import org.teavm.jso.dom.html.HTMLElement;

import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.ui.component.tabbed.TabEntry;
import eu.jvx.js.lib.ui.component.tabbed.TabbedPane;
import eu.jvx.js.lib.ui.component.tabbed.vanilla.VanillaTabbedPane;

public class TabbedPaneDemoActivity implements PlaygroundActivity
{
	@Override
	public String getName()
	{
		return "Tabbed Pane Demo";
	}

	@Override
	public String getUrlActivity()
	{
		return "tabbed_pane_demo";
	}

	@Override
	public void start(H container)
	{
		TabbedPane tp = new TabbedPane(new VanillaTabbedPane());
		{
			TabEntry tab = tp.createNewTab();
			new H((HTMLElement) tab.getTag().getContent()).attrs("#text", "Tab 1");
			new H((HTMLElement) tab.getContainer().getContent()).attrs("#text", "Hello");

		}
		
		{
			TabEntry tab = tp.createNewTab();
			new H((HTMLElement) tab.getTag().getContent()).attrs("#text", "Tab 2");
			new H((HTMLElement) tab.getContainer().getContent()).attrs("#text", "World");
		}
		
		container.addChilds(tp.getHtml());
	}

}
