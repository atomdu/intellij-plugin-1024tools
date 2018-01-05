package atomdu.tools.core.http;

import atomdu.tools.core.bean.RootBean;
import atomdu.tools.core.config.ConfigManager;
import atomdu.tools.core.http.parse.JsonRootBeanParser;
import atomdu.tools.core.bean.Navigation;
import atomdu.tools.core.http.parse.JsonNavigationParser;

/**
 * Created by atomdu on 2017/12/13.
 */
public class ApiHelper {
    /**
     * 获取入口文件
     *
     * @param onCallback
     * @return
     */
    public static AsyncHttpClient getCoreApi(OnObjectCallback<RootBean> onCallback) {
        return new AsyncHttpClient.Builder().url(ConfigManager.getMainURL()).get().parser(new JsonRootBeanParser()).onObjectCallback(onCallback).build();
    }

    /**
     * 获取导航数据
     *
     * @param url
     * @param onCallback
     * @return
     */
    public static AsyncHttpClient getNavigation(String url, OnObjectCallback<Navigation> onCallback) {
        return new AsyncHttpClient.Builder().url(url).parser(new JsonNavigationParser()).onObjectCallback(onCallback).build();
    }
}
