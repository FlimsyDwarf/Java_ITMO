package markup;

import java.util.List;

public abstract class AbstractList implements InList {
	private List<ListItem> listOfElements;

	protected AbstractList(List<ListItem> listOfElements) {
		this.listOfElements = listOfElements;
	}

	public abstract String getOpenTag();
	public abstract String getCloseTag();

	@Override
	public void toHtml (StringBuilder text) {
		text.append(getOpenTag());
		for (ListItem cur : listOfElements) {
			cur.toHtml(text);
		}
		text.append(getCloseTag());
	}
}
