package eu.javaexperience.teasite.playground;

import eu.jvx.js.lib.bindings.H;

public interface PlaygroundActivity
{
	public String getName();
	public String getUrlActivity();
	public void start(H container);
}
