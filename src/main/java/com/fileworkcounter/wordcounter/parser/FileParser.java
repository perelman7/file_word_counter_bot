package com.fileworkcounter.wordcounter.parser;

import com.fileworkcounter.wordcounter.model.FileInfo;
import java.net.URL;

public interface FileParser {

    FileInfo parse(URL url, String filename);
}
