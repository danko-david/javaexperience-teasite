package eu.jvx.js.lib.activity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import eu.javaexperience.collection.CollectionTools;
import eu.javaexperience.teavm.templatesite.common.PageStorage;
import eu.javaexperience.text.StringTools;

public class PageStorageTools
{
	public static <T extends EnumStartablePageId> T getFromEnumCalss(Class<T> cls, String path)
	{
		path = StringTools.getSubstringAfterFirstString(path, "/");
		path = StringTools.getSubstringBeforeFirstString(path, "/");
		path = path.toUpperCase();
		
		for(T e:cls.getEnumConstants())
		{
			if(e.name().equals(path))
			{
				return e;
			}
		}
		
		return null;
	}
	
	
	public static <T extends EnumStartablePageId> PageStorage<T> wrap(Class<T> cls)
	{
		final ArrayList<T> vals = new ArrayList<>();
		CollectionTools.copyInto(cls.getEnumConstants(), vals);
		final T[] arr = (T[]) Array.newInstance(cls, 0);
		
		return new PageStorage<T>()
		{
			@Override
			public T[] getPages()
			{
				return vals.toArray(arr);
			}

			@Override
			public T getById(String id)
			{
				for(T e:vals)
				{
					if(e.name().equalsIgnoreCase(id))
					{
						return e;
					}
				}
				return null;
			}

			@Override
			public T getByUrl(String path)
			{
				path = StringTools.getSubstringAfterFirstString(path, "/");
				path = StringTools.getSubstringBeforeFirstString(path, "/");
				path = path.toUpperCase();
				return getById(path);
			}
		};
	}
}
