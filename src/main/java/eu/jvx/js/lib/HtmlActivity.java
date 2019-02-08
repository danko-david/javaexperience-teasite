package eu.jvx.js.lib;

/**
 * Start (of course) used to boot up the current activity after creating a 
 * 	new HtmlActivity concrete instance.
 * 
 * 
 * Destroy called when switching to other activity and must implement
 * 	this purposes:
 * 	- release javascript object references.
 * 		(Free browser's javascript heap from the currently used objects)
 *  - delete associated (and only the associated) section from the DOM.
 *  - unload global EventListener-s associated with activity. 
 * 
 * 
 *  Other actions will be done automatically:
 *  	- releasing the HtmlActivity instance.
 *			(therefore objects referenced by a concrete HtmlActivity instance
 *			will be automatically released)
 */
public interface HtmlActivity
{
	public void start();
	/**
	 * If url modifies (push/popstate) parameters in url might modify and in
	 * this case this function is called to refresh (but not reload) the state
	 * of the activity 
	 * */
	public void reload();
	public void destroy();
}
