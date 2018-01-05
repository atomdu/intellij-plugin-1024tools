package atomdu.tools.core.config;

import atomdu.tools.core.utils.SystemUtils;

/**
 * 配置管理器
 * Created by atomdu on 2017/12/13.
 */
public final class ConfigManager extends Config {
    /**
     * 获取主机
     *
     * @return
     */
    public static final String getHost() {
        if (Config.DEBUG) {
            HOST = HOST_DEBUG;
        } else {
            //根据语言环境返回服务器地址
            if (SystemUtils.isLocaleSystemLanguageZH()) {
                HOST = HOST_ZH;
            } else {
                HOST = HOST_EN;
            }
        }
        return HOST;
    }

    /**
     * 获取入口url
     *
     * @return
     */
    public static String getMainURL() {
        //根据语言环境返回服务器地址
        if (SystemUtils.isLocaleSystemLanguageZH()) {
            return getHost() + MAIN_ZH_PATH;
        } else {
            return getHost() + MAIN_EN_PATH;
        }
    }

    /**
     * 获取url
     *
     * @param path
     * @return
     */
    public static String getURL(String path) {
        String url = getHost() + path;
        return url;
    }

    /**
     * 获取项目名称
     *
     * @return
     */
    public static String getAppName() {
        if (SystemUtils.isLocaleSystemLanguageZH()) {
            return APP_NAME_ZH;
        } else {
            return APP_NAME_EN;
        }
    }

}
