package eu.javaexperience.teavm.templatesite.common;

public interface PageStorage<P extends PageId>
{
	public P[] getPages();

	public P getById(String id);

	public P getByUrl(String path);
}
