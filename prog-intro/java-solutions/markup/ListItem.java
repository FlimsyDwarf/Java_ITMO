package markup;

import java.util.List;

public class ListItem implements Markable  {
	private List<InList> listOfInList;

	public ListItem (List<InList> listOfInList) {
		this.listOfInList = listOfInList;
	}


	@Override
	public void toHtml(StringBuilder text) {
		text.append("<li>");
		for (Markable cur : listOfInList) {
			cur.toHtml(text);
		}
		text.append("</li>");
	}
}
