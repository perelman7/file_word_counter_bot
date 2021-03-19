package org.pdfwordcounter.parser;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.pdfwordcounter.model.FileInfo;

import java.net.URL;

public class PdfFileParser implements FileParser {

    public FileInfo parse(URL url, String filename) {
        FileInfo result = null;
        PdfReader reader = null;
        try {
            reader = new PdfReader(url);
            int n = reader.getNumberOfPages();
            int wordCount = 0;
            int charCount = 0;

            for (int i = 1; i <= n; i++) {

                String contentPage = PdfTextExtractor.getTextFromPage(reader, i);

                if (contentPage != null && !contentPage.isEmpty()) {
                    charCount += contentPage.length();
                    String[] splitWords = contentPage.split(" ");
                    wordCount += splitWords.length;
                }
            }
            result = FileInfo.builder()
                    .charCount(charCount)
                    .wordCount(wordCount)
                    .filename(filename)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }
}
