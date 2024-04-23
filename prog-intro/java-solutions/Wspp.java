import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.StringBuilder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;

public class Wspp {
    public static void main(String[] args) {
        Map<String, IntList> mp = new LinkedHashMap<String, IntList>();
        try {
            Scanner scan = new Scanner(args[0], StandardCharsets.UTF_8.name());
            try {
                int cnt = 1;
                while (scan.hasNextWord()) {
                    String curString = scan.nextWord().toLowerCase();
                    IntList curList = mp.get(curString);
                    if (curList == null) {
                        curList = new IntList();
                        curList.add(cnt);
                        mp.put(curString, curList);
                    } else {
						curList.add(cnt);;
                    }
					cnt++;
                }
            } finally {
                scan.close();
            }

            try {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(args[1]), StandardCharsets.UTF_8.name()));
				try {
					for (String k : mp.keySet()) {
                        IntList cur = mp.get(k);
						writer.write(k + ' ' + String.valueOf(cur.size()) + ' ');
                        for (int i = 0; i < cur.size(); i++) {
                            writer.write(String.valueOf(cur.getAt(i)));
							if (i < cur.size() - 1) {
								writer.write(' ');
							}
                        }
                        writer.write(System.lineSeparator());
					}
				} finally {
					writer.close();
				}
			} catch (FileNotFoundException e) {
				System.out.println("The file you are trying to write in does not exist" +
						e.getMessage());
			} catch (IOException e) {
				System.out.println("Output file error" + e.getMessage());
			}
        } catch (IOException e) {
            System.out.println("Input file error " + e.getMessage());
        }
    }
}