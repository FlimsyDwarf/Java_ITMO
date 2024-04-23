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

public class WordStatInput {
	public static void main(String args[]) {
		Map<String, Integer> mp = new LinkedHashMap<String, Integer>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(args[0]), StandardCharsets.UTF_8.name()));
			try {
				int read;
				read = reader.read();
				StringBuilder buffer = new StringBuilder();
				while (read != -1 || buffer.length() > 0) {
					if (Character.DASH_PUNCTUATION == Character.getType(read) ||
							Character.isAlphabetic(read) || read == '\'') {
						buffer.append(Character.toLowerCase((char) read));
					} else if (buffer.length() > 0) {
						Integer curVal = mp.get(buffer.toString());
						if (curVal == null) {
							mp.put(buffer.toString(), 1);
						} else {
							mp.put(buffer.toString(), curVal + 1);
						}
						buffer = new StringBuilder();
					} else {
						buffer = new StringBuilder();
					}
					read = reader.read();
				}
			} finally {
				reader.close();
			}
			try {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(args[1]), "UTF-8"));
				try {
					for (String k : mp.keySet()) {
						writer.write(k + ' ' + (int) mp.get(k) + '\n');
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
