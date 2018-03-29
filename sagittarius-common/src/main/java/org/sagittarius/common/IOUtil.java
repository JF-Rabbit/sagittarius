package org.sagittarius.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtil {

    private static Logger logger = LoggerFactory.getLogger(IOUtil.class);

    private static final String READ_FILE_ERROR = "READ_FILE_ERROR";
    private static final String CLOSE_BUFFEREDREADER_ERROR = "CLOSE_BUFFEREDREADER_ERROR";

    public static String readFirstLine(String filePath) {
        BufferedReader br = null;
        String readLine = "";
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));

            if ((readLine = br.readLine()) != null) {
                return readLine;
            }
        } catch (IOException e) {
            logger.error(READ_FILE_ERROR, e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                logger.error(CLOSE_BUFFEREDREADER_ERROR, e);
            }
        }
        return readLine;
    }

    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.isFile();
    }

    public static String getFilePathFromClassLoader(String fileName) throws FileNotFoundException {
        URL url = ClassLoader.getSystemClassLoader().getResource(fileName);
        if (url == null) {
            throw new FileNotFoundException(fileName);
        }
        return url.getPath();
    }

    public static String getClassLoaderResource(String path) {
        URL url = ClassLoader.getSystemClassLoader().getResource(path);
        Optional<URL> optional = Optional.ofNullable(url);

        Supplier<RuntimeException> supplier = () -> new RuntimeException("Can't get Resource by: " + path);
        return optional.map(URL::getPath).orElseThrow(supplier);
    }

}
