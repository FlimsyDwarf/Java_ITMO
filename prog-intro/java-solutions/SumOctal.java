import static java.lang.Character.toLowerCase;

public class SumOctal {
	public static void main(String args[]) {
		Integer ans = 0;
		for (String arg : args) {
			int curStart = 0;
			for (int j = 0; j < arg.length(); j++) {
				String s = "";
				int radix = 10;
				if (Character.isWhitespace(arg.charAt(j))) {
					s = arg.substring(curStart, j);
					curStart = j + 1;
				} else if (j == arg.length() - 1) {
					s = arg.substring(curStart, j + 1);
				}
				if (s.length() != 0) {
					if (toLowerCase(s.charAt(s.length() - 1)) == 'o') {
						s = s.substring(0, s.length() - 1);
						radix = 8;
					}
					if (s.charAt(0) == '-') {
						ans -= Integer.parseUnsignedInt(s.substring(1), radix);
					} else {
						ans += Integer.parseUnsignedInt(s, radix);
					}
				}
			}
		}
		System.out.println(ans);
	}
}
