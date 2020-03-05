package eu.javaexperience.teasite;

import java.util.List;

import eu.javaexperience.reflect.Mirror;
import eu.javaexperience.web.Context;
import eu.javaexperience.web.facility.SiteFacilityTools;

public class TeasiteBackendTools
{
	public static void renderBootPage(Context ctx, List<String> css, List<String> preJs, List<String> postJs, String page)
	{
		StringBuilder sb = new StringBuilder();
		renderBootPage(sb, css, preJs, postJs, page);
		SiteFacilityTools.finishWithElementSend(ctx, sb.toString());
	}
	
	public static void renderBootPage(Appendable sb, List<String> css, List<String> preJs, List<String> postJs, String page)
	{
		renderBootPage(sb, css, preJs, postJs, null, page);
	}
	
	public static void renderBootPage(Appendable sb, List<String> css, List<String> preJs, List<String> postJs, List<String> extraHeaders, String page)
	{
		try
		{
			sb.append("<html>\n");
			sb.append("\t<head>\n");
			sb.append("\t\t<meta charset=\"utf-8\">\n");
			
			if(null != css)
			{
				for(String c:css)
				{
					sb.append("\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
					sb.append(c);
					sb.append("\" />\n");
				}
			}
			
			if(null != preJs)
			{
				for(String c:preJs)
				{
					sb.append("\t\t<script type=\"text/javascript\" src=\"");
					sb.append(c);
					sb.append("\"></script>\n");
				}
			}
			sb.append("\t</head>\n");
			
			sb.append("\t<body>\n");
			
			if(null != page)
			{
				sb.append("\t\t<div class=\"page_identifier_data\" data-page_id=\"");
				sb.append(page);
				sb.append("\"></div>\n");
			}
			
			sb.append("");
			
			if(null != postJs)
			{
				for(String c:postJs)
				{
					sb.append("\t\t<script type=\"text/javascript\" src=\"");
					sb.append(c);
					sb.append("\"></script>\n");
				}
			}
			
			sb.append("\t\t<script type=\"text/javascript\">main();</script>\n");
			sb.append("\t</body>\n");
			sb.append("</html>");
		}
		catch(Exception e)
		{
			Mirror.propagateAnyway(e);
		}
	}
}
