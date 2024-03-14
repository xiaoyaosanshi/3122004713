import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

class PaperChecker {

  // 主方法，程序入口
  public static void main(String[] args) {
    if (args.length != 3) {
      // 如果用户输入的参数个数不为3，则给出提示
      System.out.println("Please enter in the following format：java -jar main.jar [originalFilePath] [checkFilePath] [outputFilePath]");
      return;
    }

    // 获取用户输入的文件路径
    String originalFilePath = args[0];  // 原文文件路径
    String checkFilePath = args[1];  // 抄袭版论文文件路径
    String outputFilePath = args[2];  // 输出文件路径

    // 打印路径信息
    System.out.println("OriginalFile:  " + originalFilePath);
    System.out.println("PlagiarizedFile:  " + checkFilePath);
    System.out.println("AnswerFile:  " + outputFilePath);

    try {
      // 读入原文和抄袭版论文文件
      String originalText = readFile(originalFilePath);
      String checkText = readFile(checkFilePath);

      int n = 5;  // 设定切词长度
      // 计算重复率
      double similarity = calculateSimilarity(originalText, checkText, n);
      System.out.println("similarity:  " + similarity);
      // 将重复率写入输出文件
      writeResult(outputFilePath, similarity);
    } catch (IOException e) {
      System.out.println("Error reading or writing file:  " + e.getMessage());
    }
  }

  // 读取文件的方法
  private static String readFile(String filePath) throws IOException {
    return new String(Files.readAllBytes(Paths.get(filePath)));
  }

  // 将重复率写入输出文件的方法
  private static void writeResult(String filePath, double similarity) throws IOException {
    // 将结果格式化成百分比字符串
    String result = String.format("Similarity: %.2f%%", similarity * 100);
    // 写入文件
    Files.write(Paths.get(filePath), result.getBytes());
  }

  // 计算重复率的方法

  public static double calculateSimilarity(String origin, String check, int cutLength) {
    Set<String> originSet = calculateHashes(origin, cutLength);
    Set<String> checkSet = calculateHashes(check, cutLength);

    Set<String> intersection = new HashSet<>(originSet);
    intersection.retainAll(checkSet);

    int unionSize = originSet.size() + checkSet.size() - intersection.size();
    double similarity = unionSize == 0 ? 0 : (double) intersection.size() / unionSize;

    // 输出百分比形式的查重率
    System.out.printf("Similarity Percentage: %.2f%%\n", similarity * 100);

    return similarity;
  }

  // 切词并计算切词hash值的方法
//  public static Set<String> calculateHashes(String text, int cutLength) {
//    Set<String> hashes = new HashSet<>();
//    for (int i = 0; i <= text.length() - cutLength; i++) {
//      if (i == 0 || text.charAt(i - 1) == ' ' || i + cutLength == text.length() || text.charAt(i + cutLength) == ' ') {
//        hashes.add(text.substring(i, i + cutLength));
//      }
//    }
//    return hashes;
//  }`
  public static Set<String> calculateHashes(String text, int cutLength) {
    Set<String> hashes = new HashSet<>();
    for (int i = 0; i <= text.length() - cutLength; i++) {
      // 直接生成并添加长度为cutLength的子字符串到集合中
      hashes.add(text.substring(i, i + cutLength));
    }
    return hashes;
  }
}
