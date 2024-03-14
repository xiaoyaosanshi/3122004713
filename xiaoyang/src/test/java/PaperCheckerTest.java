import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class PaperCheckerTest {

  private static final String BASE_PATH = "C:\\Users\\x\\Desktop\\_3122004713\\text\\";
  private static final String ORIGINAL_FILE = BASE_PATH + "orig.txt";
  private static final String[] PLAGIARIZED_FILES = {
    BASE_PATH + "orig_0.8_add.txt",
    BASE_PATH + "orig_0.8_del.txt",
    BASE_PATH + "orig_0.8_dis_1.txt",
    BASE_PATH + "orig_0.8_dis_10.txt",
    BASE_PATH + "orig_0.8_dis_15.txt"
  };
  private static final String OUTPUT_FILE = "output.txt";

  @Test
  public void continuousTest10TimesEach() throws IOException {
    String originalText = Files.readString(Path.of(ORIGINAL_FILE));

    for (String plagiarizedFilePath : PLAGIARIZED_FILES) {
      for (int i = 0; i < 10; i++) {
        String plagiarizedText = Files.readString(Path.of(plagiarizedFilePath));
        double similarity = PaperChecker.calculateSimilarity(originalText, plagiarizedText, 5);
        Assert.assertTrue("Expected similarity to be a percentage between 0 and 1", 0 <= similarity && similarity <= 1);

        Files.writeString(Path.of(OUTPUT_FILE), String.format("Test #%d, File: %s, Similarity: %.2f\n", i + 1, plagiarizedFilePath, similarity), StandardOpenOption.APPEND);
      }
    }
  }

  // ... (Include other methods that are part of the test class)
}
