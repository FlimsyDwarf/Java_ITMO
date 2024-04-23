package search;

public class BinarySearch {

	//Контракт
	//pre: for i in a a[i] >= a[i + 1] && a != null && l + 1 <= r && существует эллемент массива принадлежащий (l; r] <= x (далее этот эллемент будет называться mn)
	//post: a[r] <= x
	public static int recursiveSearch(int[] a, int x, int l, int r) {
		//l + 1 <= r && for i in a a[i] >= a[i + 1] && mn лежит в (l; r]
		if (l + 1 == r) {
			//l + 1 == r && mn лежит в (l; r]
			//так как l не входит в область mn -> mn > a[l] -> r - минимальный индекс, удволетворяющий контракту
			return r;
		}
		// l + 1 < r && for i in a a[i] >= a[i + 1]
		// l < r && 2l < l + r < 2r -> l < (l + r) / 2 < r
		int m = (l + r) / 2;
		//l + 1 < r && l < (l + r) / 2 < r && m == (l + r) / 2 && for i in a a[i] >= a[i + 1]
		if (a[m] > x) {
			//a[m] > x && l + 1 < r && m == (l + r) / 2 && for i in a a[i] >= a[i + 1]
			//a[m] > x && for i in a a[i] >= a[i + 1] -> mn лежит в (m; r]
			return recursiveSearch(a, x, m , r);
		} else {
			//a[m] <= x && l + 1 < r && m == (l + r) / 2 && for i in a a[i] >= a[i + 1]
			//a[m] <= x && for i in a a[i] >= a[i + 1] -> mn лежит в (l; m]
			return recursiveSearch(a, x, l, m);
		}
	}

	//Контракт
	//pre: for i in a a[i] >= a[i + 1] && a != null && существует эллемент массива <= x (далее этот эллемент будет называться mn)
	//post: a[r] <= x причем r минимальный
	public static int iterativeSearch(int[] a, int x) {
		//for i in a a[i] >= a[i + 1]
		int l = -1;
		// l == -1 && l' == l && for i in a a[i] >= a[i + 1]
		int r = a.length;
		//l == -1 && l' == l && r == a.length && r' == r && mn лежит в (l; r] && for i in a a[i] >= a[i + 1]
		//будем считать, что a[-1] == inf
		while (l + 1 < r) {
			//l' + 1 < r' && mn лежит в (l'; r'] && for i in a a[i] >= a[i + 1]
			int m = (l + r) / 2;
			//l' + 1 < r' && m == (l' + r') / 2 && mn лежит в (l'; r'] && for i in a a[i] >= a[i + 1]
			if (a[m] > x) {
				//l' + 1 < r && m == (l' + r') / 2 && a[m] > x && mn лежит в (l'; r'] && for i in a a[i] >= a[i + 1]
				//a[m] > x && for i in a a[i] >= a[i + 1] -> mn лежит в (m; r']
				l = m;
				//l' == m && m == (l' + r') / 2 && a[m] > x && mn лежит в (l'; r'] && for i in a a[i] >= a[i + 1]
			} else {
				//l' + 1 < r && m == (l' + r') / 2 && a[m] <= x && mn лежит в (l'; r'] && for i in a a[i] >= a[i + 1]
				//a[m] <= x && for i in a a[i] >= a[i + 1] -> mn лежит в (l'; r]
				r = m;
				//r' == m && m == (l' + r') / 2 && a[m] <= x && mn лежит в (l'; r']
			}
			//mn лежит в (l'; r']
		}
		//l + 1 >= r && mn лежит в (l; r]
		//так как l не входит в область mn -> mn > a[l] -> r - минимальный индекс, удволетворяющий контракту
		return r;
	}

	//:note: pre and post
	// Pre: args != null && for i in range [1,args.length - 2] : args[i] >= args[i+1]
	//Post: args[x] <= args[0] при чем x минимальный
	public static void main(String[] args) {
		int x = Integer.parseInt(args[0]);
		int[] a = new int[args.length - 1];
		int sum = 0;
		for (int i = 1; i < args.length; i++) {
			a[i - 1] = Integer.parseInt(args[i]);
			sum += a[i - 1];
		}
		//for i in range [1,args.length - 1]: a[i-1] = (int) args[i]
		if (sum % 2 == 0) {
			System.out.println(recursiveSearch(a, x, -1, a.length));
		} else {
			System.out.println(iterativeSearch(a, x));
		}
	}
}
