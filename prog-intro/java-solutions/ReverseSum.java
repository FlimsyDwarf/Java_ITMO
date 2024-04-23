import java.util.Scanner;
import java.util.Arrays;

public class ReverseSum {    
    private static int[][] addSpace(int[][] a) {
    	return Arrays.copyOf(a, a.length << 1);
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        int[][] ints = new int[1][];
        int mx = 0;
        int sz = 0;
        
        while (scan.hasNextLine()) {
        	if (ints.length == sz) {
        		ints = addSpace(ints);
        	}
            String s = scan.nextLine();
            Scanner intCnt = new Scanner(s);
            int curSz = 0;
            while (intCnt.hasNextInt()) {
				curSz += 1;
                intCnt.nextInt();
			}
			if (curSz > mx) {
				mx = curSz;
			}
            intCnt.close();
			ints[sz] = new int[curSz];
			Scanner intPars = new Scanner(s);
			for (int i = 0; i < curSz; i++) {
				ints[sz][i] = intPars.nextInt();
			}
            intPars.close();
            sz += 1;
        } 
        scan.close();

        int[] sumCol = new int[mx];
        int[] sumStr = new int[sz];
        
        for (int i = 0; i < sz; i++) {
        	for (int j = 0; j < ints[i].length; j++) {
        		sumCol[j] += ints[i][j];
        		sumStr[i] += ints[i][j];
        	}
        }

        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < ints[i].length; j++) {
                System.out.print(-ints[i][j] + sumCol[j] + sumStr[i] + " ");
            }
            System.out.println();
        }
    }
}