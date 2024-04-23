package markup;

import java.util.List;

public class Emphasis extends AbstractMarkup implements Markup {

	public Emphasis (List<Markup> listOfMarkup) {
		super(listOfMarkup);
	}

	@Override
	public String getOpenTag() {
		return "<em>";
	}

	@Override
	public String getCloseTag() {
		return "</em>";
	}

	@Override
	public String getType() {
		return "*";
	}
}
