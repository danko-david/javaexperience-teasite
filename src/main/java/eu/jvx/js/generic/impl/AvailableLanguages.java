package eu.jvx.js.generic.impl;

import eu.jvx.js.generic.Language;

public enum AvailableLanguages implements Language
{
	en("en", "English", "English"),
	de("de", "Deutch", "German"),
	hu("hu", "Magyar", "Hungary"),
	
	;
	
	private final String iso;
	private final String nat;
	private final String eng;
	
	private AvailableLanguages(String iso, String Native, String english)
	{
		this.iso = iso;
		this.nat = Native;
		this.eng = english;
	}
	
	
	@Override
	public String getIso639_1()
	{
		return iso;
	}

	@Override
	public String getNativeName()
	{
		return nat;
	}

	@Override
	public String getEnglishName()
	{
		return eng;
	}
}
