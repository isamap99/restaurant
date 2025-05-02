package Common;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static void createFileIfNotExists(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();  // گرفتن دایرکتوری والد فایل

        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) { // اگر پوشه‌ها وجود ندارند، آنها را بساز
                System.out.println("Parent directories created: " + parentDir.getAbsolutePath());
            } else {
                System.out.println("Failed to create parent directories.");
            }
        }

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getAbsolutePath());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file.");
                e.printStackTrace();
            }
        }
    }
}
