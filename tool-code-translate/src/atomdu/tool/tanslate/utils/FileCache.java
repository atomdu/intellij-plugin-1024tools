package atomdu.tool.tanslate.utils;

import org.apache.xmlgraphics.util.WriterOutputStream;

import java.io.*;

/**
 * Created by atomdu on 2018/2/6.
 */
public class FileCache {
    public static void main(String... strings) throws IOException {
        FileCache f = new FileCache();
        f.save("你好啊");
    }

    public void save(String str) {
        String fileName = "d:/test.txt";
        File file;
        FileOutputStream fos = null;

        try {
            file = new File(fileName);
            fos = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] cbs = str.getBytes();
            fos.write(cbs);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
