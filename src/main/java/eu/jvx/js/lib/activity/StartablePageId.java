package eu.jvx.js.lib.activity;

import eu.javaexperience.teavm.templatesite.common.PageId;
import eu.jvx.js.lib.HtmlActivity;

public interface StartablePageId extends PageId
{
	public String getLabel();
	public String url();
	public Class<HtmlActivity> getActivityClass();
}
