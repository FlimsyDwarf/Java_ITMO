package search;

public class BinarySearchUni {

    //pre: a != null && 0 <= m < a.length
    //post: if a[m] <= a[m + 1] ->true else -> false
    public static boolean chk(int[] a, int m) {
        if (m + 1 == a.length) {
            return false;
        }
        return a[m] <= a[m + 1];
    }

    //Контракт
    // для удобства доказательства примем что a[-1] == a[a.length] == -inf
    //pre: -inf <= a[0] <= ... <= a[i - 1] <= a[i] >= a[i + 1] ... >= -inf
    //post: R == i
    public static int iterativeSearch(int[] a, int x) {
        //-inf <= a[0] <= ... <= a[i] >= a[i + 1] ... >= -inf
        int l = -1;
        int r = a.length;
        //-inf <= a[0] <= ... <= a[i] >= a[i + 1] ... >= -inf &&
        // && l == -1 && r == a.length && l < i <= r
        while (l + 1 < r) {
            //l' + 1 < r'
            int m = (l + r) / 2;
            //l' + 1 < r' && m == (l + r) / 2
            if (chk(a, m)) {
                //a[m] <= a[m + 1]
                l = m;
                //a[l'] <= a[l' + 1] -> for i in (0; l'] a[i] <= a[i + 1]
            } else {
                //a[m] > a[m + 1]
                r = m;
                //a[r'] > a[r' + 1] -> for i in [r'; a.length - 1) a[i] >= a[i + 1]
            }
        }
        //l == r - 1 && a[l] < i <= a[r]
        return r;
    }


    //Контракт
    // для удобства доказательства примем что a[-1] == a[a.length] == -inf
    //pre: -inf <= a[0] <= ... <= a[i] >= a[i + 1] ... >= -inf
    //post: R == i
    public static int recursiveSearch(int[] a, int l, int r) {
        if (l + 1 == r) {
            //l' == r' - 1 && a[l'] < a[i] <= a[r']
            return r;
        }
        // l' < r' - 1 && a[l'] < a[i] <= a[r']
        int m = (l + r) / 2;
        // l' < r' - 1 && a[l'] < a[i] <= a[r'] && m == (l' + r') / 2
        if (chk(a, m)) {
            // l' < r' - 1 && a[l'] < a[i] <= a[r'] && m == (l' + r') / 2 && a[m] <= a[m + 1]
            //a[m] <= a[m + 1] -> for i in (0; m] a[i] <= a[i + 1]
            return recursiveSearch(a, m, r);
        } else {
            //a[m] > a[m + 1] -> for i in [m; a.length - 1) a[i] >= a[i + 1]
            return recursiveSearch(a, l, m);
        }
    }

    /*
    Pre:
    args != null && существует такой x - индекс, такой что for i in range[0,x-1] a[i] < a[x] &&
    for i in range[x+1,len-1] a[x] > a[i] && for i in range[0, x-1] a[i] < a[i+1] &&
    for i in range[x,a.length - 2] a[i] > a[i+1]
    Post
     */
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length];
        int sum = 0;
        for (int i = 0; i < args.length; i++) {
            a[i] = Integer.parseInt(args[i]);
            sum += a[i];
        }
        //for i in range[0, args.length) a[i] = (int) args[i]
        if (sum % 2 == 0) {
            System.out.println(recursiveSearch(a, -1, a.length));
        } else {
            System.out.println(iterativeSearch(a, x));
        }
    }
}
