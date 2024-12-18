package Utils;

import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    private FileWriter csvWriter;

    public CsvWriter(String filePath) throws IOException {
        csvWriter = new FileWriter(filePath);
        csvWriter.append("Name,Price,Rating,URL\n");
    }

    public void writeProduct(String name, String price, String rating, String url) throws IOException {
        csvWriter.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\"\n", name, price, rating, url));
    }

    public void close() throws IOException {
        csvWriter.flush();
        csvWriter.close();
    }
}