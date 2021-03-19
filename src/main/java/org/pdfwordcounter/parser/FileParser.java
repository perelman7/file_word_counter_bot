package org.pdfwordcounter.parser;

import org.pdfwordcounter.model.FileInfo;

import java.net.URL;

public interface FileParser {

    FileInfo parse(URL url, String filename);
}
