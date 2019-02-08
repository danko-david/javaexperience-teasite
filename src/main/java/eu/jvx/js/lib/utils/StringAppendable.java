package eu.jvx.js.lib.utils;

public class StringAppendable implements Appendable
{
	public String data = "";
	
	@Override
	public Appendable append(String csq)
	{
		data += csq;
		return this;
	}
}
