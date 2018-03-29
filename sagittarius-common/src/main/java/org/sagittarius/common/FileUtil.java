package org.sagittarius.common;

import java.io.File;

public class FileUtil {


    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        deleteFile(f);
                    }
                }
            }
            if (file.isFile()) {
                if (!file.delete()) {
                    throw new RuntimeException("Delete file fail!");
                }
            }
        }

    }
}
