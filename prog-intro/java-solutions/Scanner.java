import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.lang.StringBuilder;
import java.nio.charset.StandardCharsets;

public class Scanner {
    private final int size = 1024;
    private Reader reader;
    private char[] buffer = new char[size];
    private int curId;
    private int isEnd;
    private int lineSkipped = 0;

    public Scanner(InputStream in) throws IOException {
        reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        myRead();
    }

    public Scanner(String fileName, String charset) throws IOException {
        reader = new InputStreamReader(new FileInputStream(fileName), charset);
        myRead();
    }

    private void myRead() throws IOException {
        isEnd = reader.read(buffer);
        curId = 0;
    }

    public boolean isEndOfFile() throws IOException {
        skipSpaces();
        if (lineSkipped > 1) {
            return false;
        }
        return isEnd == -1;
    }

    public void close() throws IOException {
        reader.close();
    }

    private boolean isSep(char c) {
        return c == '\n' || c == '\r';
    }

    private boolean isGoodChar(char ch) {
        return Character.isLetterOrDigit(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\'';
    }

    private int parseInt(String x) {
        x = x.toLowerCase();
        if (x.charAt(x.length() - 1) != 'o') {
            return Integer.parseInt(x);
        }
        return Integer.parseUnsignedInt(x.substring(0, x.length() - 1), 8);
    }

    private void skipSpaces() throws IOException {
        while (isEnd != -1) {
            for (int i = curId; i < isEnd; i++) {
                if (isGoodChar(buffer[i])) {
                    curId = i;
                    return;
                } else if (isSep(buffer[i])) {
                    if (isEnd == -1) {
                        return;
                    }
                    if (buffer[i] == '\r') {
                        if (i != isEnd - 1 && buffer[i + 1] == '\n') {
                            i += 1;
                        } else if (i == isEnd - 1) {
                            myRead();
                            if (isEnd != -1 && buffer[0] == '\n') {
                                i = 0;
                            }
                        }
                    }
                    lineSkipped++;
                }
            }
            myRead();
        }
    }

    public int lineSkipped() {
        return lineSkipped;
    }

    public void resetlineSkipped() {
        lineSkipped = 0;
    }

    private String findNext(int mode) throws IOException {
        StringBuilder s = new StringBuilder();
        skipSpaces();

        while (isEnd != -1) {
            for (int i = curId; i < isEnd; ++i) {
                if (isGoodChar(buffer[i])) {
                    if (mode == 2 && Character.isDigit(buffer[i])) {
                        curId = i;
                        return s.toString();
                    }
                    s.append(buffer[i]);
                } else {
                    curId = i;
                    return s.toString();
                }
            }
            myRead();
        }
        return s.toString();
    }

    public int nextInt() throws IOException {
        return parseInt(findNext(1));
    }

    public String nextWord() throws IOException {
        return findNext(2);
    }

    private boolean hasNext(int mode) throws IOException {
        skipSpaces();
        if (isEnd == -1) {
            return false;
        }
        if (mode == 1 && isGoodChar(buffer[curId])) {
            return true;
        } else if (mode == 2) {
            while (curId < isEnd) {
                skipSpaces();
                if (isGoodChar(buffer[curId]) && !Character.isDigit(buffer[curId])) {
                    return true;
                } else {
                    curId += 1;
                }
                if (curId == isEnd) {
                    myRead();
                }
            }
        }
        return false;
    }

    public boolean hasNextInt() throws IOException {
        return hasNext(1);
    }
    public boolean hasNextWord() throws IOException {
        return hasNext(2);
    }
}