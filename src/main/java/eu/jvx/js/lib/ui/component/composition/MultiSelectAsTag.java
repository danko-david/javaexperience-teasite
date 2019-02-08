package eu.jvx.js.lib.ui.component.composition;

import java.util.ArrayList;
import java.util.Collection;

import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Element;

import eu.javaexperience.arrays.ArrayTools;
import eu.javaexperience.collection.CollectionTools;
import eu.jvx.js.lib.ImpersonalisedHtml;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;

public abstract class MultiSelectAsTag<E>
{
	protected HTMLElement selectedBox = new H("div").getHtml();
	protected ArrayList<SelectedTagElement<E>> elems = new ArrayList<>();
	
	public static class SelectedTagElement<E> implements ImpersonalisedHtml
	{
		public final MultiSelectAsTag<E> owner;
		public final E elem;
		
		protected HTMLElement html;
		
		public SelectedTagElement(MultiSelectAsTag<E> owner, E elem)
		{
			this.owner = owner;
			this.elem = elem;
		}
		
		public void remove()
		{
			owner.removeSelectedElement(this);
		}
		
		@Override
		public Object getImpersonator()
		{
			return this;
		}

		@Override
		public Element getHtml()
		{
			return html;
		}
	}
	
	public HTMLElement getSelectBox()
	{
		return selectedBox;
	}
	
	protected abstract HTMLElement renderItem(SelectedTagElement<E> elem);
	
	
	/**
	 * Adds to the list and call updateSelector to notify the selecto's logic
	 * to revent selecting this element again (if it designed to single select) 
	 * */
	public void selectElement(E elem)
	{
		SelectedTagElement<E> n = new SelectedTagElement<E>(this, elem);
		n.html = renderItem(n);
		selectedBox.appendChild(n.html);
		elems.add(n);
		updateSelector();
	}
	
	public void removeSelectedElement(SelectedTagElement<E> elem)
	{
		elems.remove(elem);
		VanillaTools.remove(elem.html);
		updateSelector();
	}

	public Collection<E> fillSelectedElements(Collection<E> elems)
	{
		CollectionTools.convert(elems, this.elems, (e)->e.elem);
		return elems;
	}
	
	protected abstract void updateSelector();

	public static <E> HTMLElement renderBox(String text, SelectedTagElement<E> toRemove)
	{
		return new H("div").attrs
		(
			"#text", text,
			"class", "btn option",
			"style", "display: inline-block; padding: 4px; margin-right: 5px"
		)
		.addChilds
		(
			new H("div").attrs
			(
				"#text", "x",
				"style", "display: inline-block; color: white; background-color: red; border-radius: 5px; margin-left:5px; padding: 4px 6px 4px 2px"
			).on("click", (e)->toRemove.remove())
		).getHtml();
	}

	public void reset()
	{
		selectedBox.clear();
		elems.clear();
		updateSelector();
	}
}
