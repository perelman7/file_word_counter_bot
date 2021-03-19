package org.pdfwordcounter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfo implements Serializable {

    private String filename;
    private int wordCount;
    private int charCount;
}
