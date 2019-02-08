package eu.jvx.js.lib;

public class JvxClientException extends RuntimeException
{
	public String message;
	public String details;
	
	public JvxClientException(){}
	
	public JvxClientException(String msg, String details)
	{
		this.message = msg;
		this.details = details;
	}
}
