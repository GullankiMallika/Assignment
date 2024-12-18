package Utils;

import java.io.FileWriter;
import java.io.IOException;

public class TestLogger {
    public static void logTestResult(String testName, boolean result) {
        try (FileWriter logWriter = new FileWriter("outputs/test_results.log", true)) {
            logWriter.write(testName + ": " + (result ? "PASS" : "FAIL") + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}