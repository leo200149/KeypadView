package tw.com.hycleo.keypad.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhanyu on 15/9/20.
 */
public class FileUtil {

    public static final String ENCODE = "UTF8";

    public static List<String> readFile(File file) {
        List<String> content = new ArrayList<>();
        if (file.isFile()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = null;
                while((line = br.readLine()) != null) {
                    content.add(line);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    public static List<String> readFile(InputStream is) {
        List<String> content = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while((line = br.readLine()) != null) {
                content.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
