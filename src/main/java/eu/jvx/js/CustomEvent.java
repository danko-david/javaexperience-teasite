package eu.jvx.js;

import org.teavm.jso.JSBody;
import org.teavm.jso.dom.events.Event;

public abstract class CustomEvent implements Event
{
	@JSBody(params={"type"}, script = "return new CustomEvent(type);")
	public static native CustomEvent create(String type);
}
