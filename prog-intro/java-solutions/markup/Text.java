package markup;

public class Text implements Markup {
	private StringBuilder text;
	public Text (String text) {
		this.text =  new StringBuilder(text);
	}

	private void fillIn(StringBuilder text) {
		text.append(this.text);
	}

	@Override
	public void toMarkdown(StringBuilder text) {
		fillIn(text);
	}

	@Override
	public void toHtml(StringBuilder text) {
		fillIn(text);
	}
}
