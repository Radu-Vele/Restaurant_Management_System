package dataaccess;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterCustom {
    String fileName;
    FileWriter fileWriter;
    public FileWriterCustom(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
    }
}
