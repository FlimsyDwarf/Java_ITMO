package markup;

import java.util.List;


public class UnorderedList extends AbstractList implements InList {

	public UnorderedList(List<ListItem> listOfItems) {
		super(listOfItems);
	}

	@Override
	public String getOpenTag() {
		return "<ul>";
	}

	@Override
	public String getCloseTag() {
		return "</ul>";
	}
}
