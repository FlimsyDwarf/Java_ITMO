package markup;

import java.util.List;

public class Paragraph implements InList {
	protected List<Markup> listOfMarkup;

	public Paragraph(List<Markup> listOfMarkup) {
		this.listOfMarkup = listOfMarkup;

	}

	public void toMarkdown(StringBuilder text) {
		for (Markup cur : listOfMarkup) {
			cur.toMarkdown(text);
		}
	}
	@Override
	public void toHtml(StringBuilder text) {
		for (Markup cur : listOfMarkup) {
			cur.toHtml(text);
		}
	}
}
