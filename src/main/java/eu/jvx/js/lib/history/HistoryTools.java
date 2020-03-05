package eu.jvx.js.lib.history;

import java.util.Map;
import java.util.Map.Entry;

import org.teavm.jso.browser.Window;

import eu.javaexperience.collection.map.SmallMap;
import eu.javaexperience.text.StringTools;
import eu.javaexperience.url.UrlBuilder;
import eu.javaexperience.url.UrlTools;
import eu.javaexperience.web.HttpTools;

public class HistoryTools
{
	protected static Map<String, String[]> GET_PARAMS = null;
	protected static String PREV_GET = null;
	
	protected static Map<String, String[]> getCurrentGetParams()
	{
		String get = Window.current().getLocation().getFullURL();
		if(!get.equals(PREV_GET))
		{
			GET_PARAMS = null;
		}
		
		if(null == GET_PARAMS)
		{
			GET_PARAMS = new UrlBuilder(get).getParams();
			PREV_GET = get;
		}
		return GET_PARAMS;
	}
	
	public static String $_GET(String key)
	{
		return UrlTools.getParam(getCurrentGetParams(), key);
	}
	
	public static String[] $_GET_ALL(String key)
	{
		return getCurrentGetParams().get(key);
	}

	public static void pushUrl(String url)
	{
		Window.current().getHistory().pushState(null, null, url);
	}
	
	/*TODO public static void pushDifferentUrl(String url)
	{
		Window.current().getLocation().getFullURL()
	}*/

	public static String getPath()
	{
		return Window.current().getLocation().getPathName();
	}
	
	public static void pushUpdateParameter(String key, String value)
	{
		//pushUpdatedParameters(new OneShotMap<>(key, value)); TODO Uncaught ReferenceError: juc_ConcurrentMap is not defined
		SmallMap<String, String> map = new SmallMap<String, String>();
		map.put(key, value);
		pushUpdatedParameters(map);
	}
	
	public static void pushUpdatedParameters(Map<String, String> params)
	{
		Map<String, String[]> crnt = getCurrentGetParams();
		SmallMap<String, String[]> newer = new SmallMap<String, String[]>(crnt);
		for(Entry<String, String> p:params.entrySet())
		{
			newer.put(p.getKey(), new String[]{p.getValue()});
		}
		
		pushParameters(params);
	}
	
	public static void pushParameters(Map<String, String> params)
	{
		pushUrl(getCurrentUrl().withExactParameters(HttpTools.convMapToMulti(params)).getUrl().toString());
	}

	public static UrlBuilder getCurrentUrl()
	{
		return new UrlBuilder(Window.current().getLocation().getFullURL());
	}

	public static void go(String url)
	{
		Window.current().getLocation().setFullURL(url);
	}
	
	public static String getDispatchPath()
	{
		String ret = HistoryTools.getPath();
		if(ret.endsWith("/"))
		{
			ret = StringTools.getSubstringBeforeLastString(ret, "/");
		}
		return ret;
	}
}
