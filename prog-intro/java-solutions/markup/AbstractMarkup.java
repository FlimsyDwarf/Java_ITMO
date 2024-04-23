package markup;

import java.lang.StringBuilder;
import java.util.List;

abstract class AbstractMarkup implements Markup {

	private List<Markup> listOfMarkup;

	protected AbstractMarkup(List<Markup> listOfMarkup) {
		this.listOfMarkup = listOfMarkup;
	}

	public abstract String getOpenTag();
	public abstract String getCloseTag();
	public abstract String getType();

	@Override
	public void toMarkdown(StringBuilder text) {
		text.append(getType());
		for (Markup cur : listOfMarkup) {
			cur.toMarkdown(text);
		}
		text.append(getType());
	}
	@Override
	public void toHtml(StringBuilder text) {
		text.append(getOpenTag());
		for (Markup cur : listOfMarkup) {
			cur.toHtml(text);
		}
		text.append(getCloseTag());
	}

}
