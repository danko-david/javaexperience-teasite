package eu.javaexperience.teasite.playground;

import org.teavm.jso.json.JSON;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.teavm.datareprez.DataObjectTeaVMImpl;
import eu.jvx.js.lib.bindings.H;

public class TestActivity implements PlaygroundActivity
{
	@Override
	public String getName()
	{
		return "Tests";
	}

	@Override
	public String getUrlActivity()
	{
		return "tests";
	}

	@Override
	public void start(H container)
	{
		DataObject obj = new DataObjectTeaVMImpl(JSON.parse("{\"long\":1594177547644}"));
		System.out.println(obj.getDouble("long"));
		System.out.println(obj.get("long"));
		System.out.println(obj.getLong("long"));
	}
}
