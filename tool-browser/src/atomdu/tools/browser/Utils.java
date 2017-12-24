package atomdu.tools.browser;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Created by atomdu on 2017/9/26.
 */
public class Utils {
    public static String COLOR = "http://localhost/atomdu.github.io/extra/image.html";

    /**
     * 打开默认浏览器访问页面
     */
    public static void openDefaultBrowser(String url) {
        //启用系统默认浏览器来打开网址。
        try {
            URI uri = new URI(url);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前操作系统名称
     */
    public static String getOSName() {
        Properties props = System.getProperties(); //获得系统属性集
        String osName = props.getProperty("os.name"); //操作系统名称
        System.err.println(osName);
        return osName;
    }

    /**
     * 获取当前操作系统构架
     */
    public static String getOSArch() {
        Properties props = System.getProperties(); //获得系统属性集
        String osArch = props.getProperty("os.arch"); //操作系统构架
        System.err.println(osArch);
        return osArch;
    }


    /**
     * 获取当前操作系统版本
     */
    public static String getOSVersion() {
        Properties props = System.getProperties(); //获得系统属性集
        String osVersion = props.getProperty("os.version"); //操作系统版本
        System.err.println(osVersion);
        return osVersion;
    }
}
