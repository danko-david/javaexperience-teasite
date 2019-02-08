package eu.jvx.js.lib.ui.component.tabbed;

import org.teavm.jso.dom.html.HTMLElement;

public interface TabStructureManager
{
	public HTMLElement createTabbedPaneSkel();
	public TabEntry addTabToPoz(HTMLElement structureRoot, int absolutePosition);
	public void setTabIndexActive(HTMLElement structureRoot, int absolutePosition);
	public boolean isTabIndexActive(HTMLElement html, int tabIndex);
	public void initialize(HTMLElement root);
	public void destroy(HTMLElement root);
}
