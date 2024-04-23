package markup;

import java.util.List;

public class Strikeout extends AbstractMarkup implements Markup {
	public Strikeout (List<Markup> listOfMarkup) {
		super(listOfMarkup);
	}
	@Override
	public String getOpenTag() {
		return "<s>";
	}

	@Override
	public String getCloseTag() {
		return "</s>";
	}

	@Override
	public String getType() {
		return "~";
	}
}
