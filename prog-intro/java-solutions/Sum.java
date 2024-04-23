public class Sum {
	public static void main(String args[]) {
		int ans = 0;
		for (String arg : args) {
			int curStart = 0;
			for (int j = 0; j < arg.length(); j++) {
				if (Character.isWhitespace(arg.charAt(j))) {
					String s = arg.substring(curStart, j);
					if (s.length() != 0) {
						ans += Integer.parseInt(s);
					}
					curStart = j + 1;
				}
			}
			String s = arg.substring(curStart);
			if (!s.isEmpty())
				ans += Integer.parseInt(s);
		}
		System.out.println(ans);
	}
}