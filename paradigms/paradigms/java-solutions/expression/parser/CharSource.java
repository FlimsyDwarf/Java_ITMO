package expression.parser;

public interface CharSource {
	boolean hasNext();
	char next();

	char getBack(int back);
	IllegalArgumentException error(String message);
}
