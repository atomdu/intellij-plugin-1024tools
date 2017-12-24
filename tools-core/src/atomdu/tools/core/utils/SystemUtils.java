package atomdu.tools.core.utils;

import atomdu.tools.core.log.Log;
import atomdu.tools.core.notify.NotifyFactory;
import com.intellij.openapi.editor.EditorFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by atomdu on 2017/9/26.
 */
public class SystemUtils {

    /**
     * 获取当前开发工具名称
     *
     * @return intellij or eclipse
     */
    public static String getDevName() {
        try {
            EditorFactory factory = EditorFactory.getInstance();
            if (factory != null)
                return "intellij";
        } catch (Exception e) {
            return "eclipse";
        }
        return "";
    }

    /**
     * 获取本机系统语言
     *
     * @return intellij or eclipse
     */
    public static boolean isLocaleSystemLanguageZH() {
        //获得此Java虚拟机当前线程默认的语言环境值
        Locale defaultLocale = Locale.getDefault();
        String language = defaultLocale.getLanguage();//返回国家的语言
        if (language != null && language.equals("zh")) {
            return true;
        }
        return false;
    }

    /**
     * 获取本机系统语言
     *
     * @return intellij or eclipse
     */
    public static String getLocaleSystemLanguage() {
        //获得此Java虚拟机当前线程默认的语言环境值
        Locale defaultLocale = Locale.getDefault();
        //String country = defaultLocale.getCountry();//返回国家地区代码
        String language = defaultLocale.getLanguage();//返回国家的语言
        //String displayCountry = defaultLocale.getDisplayCountry();//返回适合向用户显示的国家信息
        //String displayLanaguage = defaultLocale.getDisplayLanguage();//返回适合向用户展示的语言信息
        //String displayName = defaultLocale.getDisplayName();//返回适合向用户展示的语言环境名
        //依次返回：CN、zh、中国、中文中文（中国）
        return language;
    }

    /**
     * 打开默认浏览器访问页面
     */
    public static void openDefaultBrowser(String url) {
        //启用系统默认浏览器来打开网址。
        if (url == null || url.length() == 0) {
            NotifyFactory.getInstance().getNotify().error("Url不能为空");
            return;
        }
        try {
            URI uri = new URI(url);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException e) {
            NotifyFactory.getInstance().getNotify().error("Url错误:" + url);
            e.printStackTrace();
        } catch (IOException e) {
            NotifyFactory.getInstance().getNotify().error("Url错误:" + url);
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
