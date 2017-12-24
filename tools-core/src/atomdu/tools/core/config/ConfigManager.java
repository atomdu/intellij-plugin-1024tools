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
            return HOST = HOST_DEBUG;
        } else {
            //根据语言环境返回服务器地址
            if (SystemUtils.isLocaleSystemLanguageZH()) {
                return HOST_ZH;
            } else {
                return HOST_EN;
            }
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
