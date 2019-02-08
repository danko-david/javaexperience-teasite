package eu.javaexperience.teavm.templatesite.frontend;

import eu.javaexperience.teavm.templatesite.common.PageId;
import eu.jvx.js.lib.HtmlActivity;

public interface PageStarter<T extends PageId>
{
	public void startPage(T id);

	public T getCurrentPage();
	
	public HtmlActivity getCurrentActivity();
}
