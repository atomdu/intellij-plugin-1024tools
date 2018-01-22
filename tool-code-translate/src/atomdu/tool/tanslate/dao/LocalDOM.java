package atomdu.tool.tanslate.dao;

import atomdu.tools.core.config.Config;
import atomdu.tools.core.utils.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 本地数据
 * Created by atomdu on 2017/12/1.
 */
public class LocalDOM {
    public static Map<String, String> map = new HashMap<>();
    public static Map<String, Integer> currentCounts = new HashMap<>();//记录

    static LocalDOM localDOM;

    public LocalDOM() {
    }

    public static LocalDOM getInstance() {
        if (localDOM == null)
            localDOM = new LocalDOM();
        return localDOM;
    }

    public boolean isFrom(String from) {
        return map.containsKey(from);
    }

    public String getTo(String key) {
        if (map.containsKey(key.toLowerCase())) {
            if (currentCounts.containsKey(key.toLowerCase())) {
                currentCounts.put(key.toLowerCase(), currentCounts.get(key.toLowerCase()) + 1);
            } else {
                currentCounts.put(key, 1);
            }
            return map.get(key.toLowerCase());
        }
        return null;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        LocalDOM.map = map;
    }

    public void init(String content) {
        //分行
        String[] lines = null;
        if (Config.DEBUG)
            lines = content.split("\r\n");//在window“\r\n”是换行符//TODO 换行符系统适配
        else
            lines = content.split("\n");//再linux“\n”是换行符//TODO 换行符系统适配

        //分词
        for (String line : lines) {
            String[] kv = line.split("=");
            if (kv.length == 2)
                map.put(kv[0].toLowerCase(), kv[1]);
        }
    }

    /**
     * 统计
     */
    public String getCurrentStatistics() {
        StringBuilder sb = new StringBuilder();
        for (String key : currentCounts.keySet()) {
            int value = currentCounts.get(key);
            sb.append(key + "=" + value+"\n");//TODO 换行符系统适配
        }
        return sb.toString();
    }
}
