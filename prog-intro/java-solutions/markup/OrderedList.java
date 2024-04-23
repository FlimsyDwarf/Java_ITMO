package markup;

import java.util.List;

public class OrderedList extends AbstractList {

	public OrderedList (List<ListItem> listOfItems) {
		super(listOfItems);
	}

	@Override
	public String getOpenTag() {
		return "<ol>";
	}

	@Override
	public String getCloseTag() {
		return "</ol>";
	}
}
