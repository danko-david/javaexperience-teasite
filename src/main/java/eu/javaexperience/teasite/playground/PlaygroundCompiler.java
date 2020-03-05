package eu.javaexperience.teasite.playground;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import eu.javaexperience.collection.CollectionTools;
import eu.javaexperience.collection.set.OneShotList;
import eu.javaexperience.teasite.TeasiteBackendTools;
import eu.javaexperience.teavm.TeaVmWebCompiler;

public class PlaygroundCompiler
{
	public static void main(String[] args) throws Exception
	{
		String targetDir = "/tmp/teasite_playground/";
		
		TeaVmWebCompiler compiler = new TeaVmWebCompiler(PlaygroundApp.class, new File(targetDir));
		compiler.compile(true);
		compiler.emitMergedOutput(targetDir+"/teasite_out.js");
		try(PrintWriter pw = new PrintWriter(targetDir+"/index.html"))
		{
			List<String> css = CollectionTools.inlineArrayList
			(
				"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
			);
			List<String> preJs = CollectionTools.inlineArrayList
			(
				"https://code.jquery.com/jquery-2.2.4.min.js",
				"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
			);
			
			List<String> postJs = new OneShotList<>("./teasite_out.js");
			TeasiteBackendTools.renderBootPage(pw, css, preJs, postJs, null);
			pw.flush();
		}
	}
}
