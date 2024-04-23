package md2html;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Stack;
import java.util.Set;
import java.util.Map;

public class Parser {
    BufferedReader reader;
    BufferedWriter writer;

    Stack<String> allTags;
    Stack<StringBuilder> blocks;
    StringBuilder html_text;

    private final Set<String> MARKDOWN_TAGS = Set.of("*", "-", "_", "**", "--", "__", "`"); //добавить
    private final Set<String> IMAGE_TAGS = Set.of("<img alt='", "' src='", "'>");

    private final Map<String, String> MARKDOWN_TO_IMAGE = Map.of(
            "![", "<img alt='",
            "](", "' src='",
            ")", "'>");
    private final Map<String, String> OPENING_TAGS = Map.of(
            "*", "<em>",
            "-", "<em>",
            "_", "<em>",
            "**", "<strong>",
            "--", "<s>",
            "__", "<strong>",
            "`", "<code>");
    private final Map<String, String> CLOSING_TAGS = Map.of(
            "*", "</em>",
            "-", "</em>",
            "_", "</em>",
            "**", "</strong>",
            "--", "</s>",
            "__", "</strong>",
            "`", "</code>");

    private final Map<String, String> SPECIAL_SYMBOLS = Map.of(
            "<", "&lt;",
            ">", "&gt;",
            "&", "&amp;");

    public Parser (String inputFile, String outputFile) throws FileNotFoundException {
        html_text = new StringBuilder();
        allTags = new Stack<>();
        blocks = new Stack<>();
        this.reader = new BufferedReader(new InputStreamReader
                (new FileInputStream(inputFile), StandardCharsets.UTF_8));
        this.writer = new BufferedWriter( new OutputStreamWriter(
                new FileOutputStream(outputFile), StandardCharsets.UTF_8));
    }

    private boolean isMarkdownTag(String tag) {
        return MARKDOWN_TAGS.contains(tag);
    }
    private boolean isImageTag(String tag) {
        return IMAGE_TAGS.contains(tag);
    }
    private String toOpeningHtmlTag(String tag) {
        return OPENING_TAGS.get(tag);
    }
    private String toClosingHtmlTag(String tag) {
        return CLOSING_TAGS.get(tag);
    }
    private String toImageTag(String tag) {
        return MARKDOWN_TO_IMAGE.get(tag);
    }
    private boolean isSpecialSymbol(char currentChar) {
        return SPECIAL_SYMBOLS.get(String.valueOf(currentChar)) != null;
    }
    private String getSpecialSymbol(char currentChar) {
        return SPECIAL_SYMBOLS.get(String.valueOf(currentChar));
    }

    private StringBuilder addImageTag(String tag, StringBuilder currentBlock) {
        blocks.add(currentBlock);
        allTags.add(tag);
        return new StringBuilder();
    }

    private void parseBlocks(StringBuilder currentBlock, int lvl, boolean isParagraph) {
        while (!blocks.isEmpty()) {
            StringBuilder previousBlock = blocks.pop();
            if (!allTags.isEmpty()) {
                previousBlock.append(allTags.pop());
            }
            previousBlock.append(currentBlock);
            currentBlock = previousBlock;
        }
        if (isParagraph) {
            currentBlock.append("</p>");
        } else if (lvl > 0) {
            currentBlock.append("</h" + lvl + '>');
        }
        else {
            return;
        }
        currentBlock.append(System.lineSeparator());
        html_text.append(currentBlock);
    }

    public void parse() throws IOException {
        String currentLine;
        boolean isParagraph = false;
        StringBuilder currentBlock = new StringBuilder();
        int lastLvl = 0;
        boolean wasEmpty = false;
        while ((currentLine = reader.readLine()) != null) {
            int lvl = 0;
            int lineLength = currentLine.length();
            if (currentLine.equals("") && wasEmpty) {
                continue;
            } else if (currentLine.equals("")) {
                parseBlocks(currentBlock, lastLvl, isParagraph);
                currentBlock = new StringBuilder();
                isParagraph = false;
                lastLvl = 0;
                wasEmpty = true;
                continue;
            } else if (!isParagraph && lastLvl == 0) {
                while (lvl < lineLength && currentLine.charAt(lvl) == '#') {
                    lvl++;
                }
                if (lvl > 0 && lineLength > lvl && currentLine.charAt(lvl) == ' ' ) {
                    html_text.append("<h").append(lvl).append(">");
                    lastLvl = lvl;
                    lvl++;
                } else {
                    lvl = 0;
                    html_text.append("<p>");
                    isParagraph = true;
                }
                wasEmpty = false;
            } else  {
                currentBlock.append(System.lineSeparator());
            }
            for (int i = lvl; i < lineLength; i++) {
                char currentChar = currentLine.charAt(i);
                char nextChar = i + 1 < lineLength ? currentLine.charAt(i + 1) : 0;
                if (isSpecialSymbol(currentChar)) {
                    currentBlock.append(getSpecialSymbol(currentChar));
                } else if (currentChar == '\\' && nextChar != 0) {
                    i++;
                    currentBlock.append(nextChar);
                } else if (currentChar == '!' && nextChar == '[') {
                    i++;
                    currentBlock = addImageTag(toImageTag(String.valueOf(currentChar) + nextChar), currentBlock);
                } else if (currentChar == ']' && !allTags.isEmpty() && allTags.peek().equals("<img alt='") && nextChar == '(') {
                    i++;
                    currentBlock = addImageTag(toImageTag(String.valueOf(currentChar) + nextChar), currentBlock);
                } else if (currentChar == ')' && !allTags.isEmpty() && allTags.peek().equals("' src='")) {
                    currentBlock = addImageTag(toImageTag(String.valueOf(currentChar)), currentBlock);
                } else if (!allTags.isEmpty() && (allTags.peek().equals(toImageTag("![")) || allTags.peek().equals(toImageTag("](")))) {
                    currentBlock.append(currentChar);
                } else if (isMarkdownTag(String.valueOf(currentChar))) {
                    String currentTag = String.valueOf(currentChar);
                    if (nextChar != 0 && currentChar == nextChar) {
                        currentTag += nextChar;
                        i++;
                    }
                    StringBuilder imageBlock = new StringBuilder();
                    while (!allTags.isEmpty() && isImageTag(allTags.peek())) {
                        StringBuilder previousBlock = blocks.pop();
                        if (previousBlock == null) {
                            previousBlock = new StringBuilder();
                        }
                        previousBlock.append(allTags.pop()).append(imageBlock);
                        imageBlock = previousBlock;
                    }
                    if (!allTags.isEmpty() && allTags.peek().equals(currentTag)) {
                        StringBuilder previousBlock = new StringBuilder();
                        if (!imageBlock.isEmpty()) {
                            previousBlock.append(toOpeningHtmlTag(currentTag)).
                                    append(imageBlock).
                                    append(currentBlock).
                                    append(toClosingHtmlTag(currentTag));
                            blocks.peek().append(previousBlock);
                            currentBlock = blocks.pop();
                        } else {
                            previousBlock = blocks.pop();
                            if (previousBlock == null) {
                                previousBlock = new StringBuilder();
                            }
                            previousBlock.append(toOpeningHtmlTag(currentTag)).
                                    append(currentBlock).
                                    append(toClosingHtmlTag(currentTag));
                            currentBlock = previousBlock;
                        }
                        allTags.pop();

                    } else {
                        allTags.add(currentTag);
                        blocks.add(imageBlock);
                        blocks.peek().append(currentBlock);
                        currentBlock = new StringBuilder();
                    }
                } else {
                    currentBlock.append(currentChar);
                }
            }
        }
        parseBlocks(currentBlock, lastLvl, isParagraph);
        writer.write(String.valueOf(html_text));
        writer.close();
        reader.close();
    }

}
