package markup;

import java.util.List;

public class Strong extends AbstractMarkup implements Markup {

	public Strong (List<Markup> listOfMarkup) {
		super(listOfMarkup);
	}

	@Override
	public String getOpenTag() {
		return "<strong>";
	}

	@Override
	public String getCloseTag() {
		return "</strong>";
	}

	@Override
	public String getType() {
		return "__";
	}
}
