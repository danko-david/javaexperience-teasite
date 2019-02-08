package eu.jvx.js.generic;

public class TranslationTools
{
	public static Translation createFixedTranslation(final String id, final String content)
	{
		return new Translation()
		{
			@Override
			public String getLabel()
			{
				return id;
			}
			
			@Override
			public String getContent(Language lang)
			{
				return content;
			}
		};
	}
}
