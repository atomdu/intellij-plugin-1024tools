package atomdu.tools.browser;

import atomdu.tools.core.http.AsyncHttpClient;
import atomdu.tools.core.http.OnStringCallback;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * 浏览器主体抽象面板
 * Created by atomdu on 2018/2/4.
 */
public class BrowserBodyTextPanel extends JPanel {
    protected String currentShowPageUrl;//当前显示的页面url

    JTextArea cLable;

    public BrowserBodyTextPanel() {
        setLayout(new BorderLayout());
        cLable = new JTextArea();
        add(cLable, BorderLayout.CENTER);
    }


    public void load(final String url) {
        if (url.equals(currentShowPageUrl))
            return;

        currentShowPageUrl = url;
        new AsyncHttpClient.Builder().url(url).get().onStringCallback(new OnStringCallback() {
            String u = url;

            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                JsonObject jsonObject = new JsonObject();
                cLable.setText(content);
            }

            @Override
            public void onFailure(int statusCode, Map<String, List<String>> headers, String content) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        }).build().execute();
    }


    /**
     * 当前显示的页面的URL
     *
     * @return
     */
    public String getCurrentShowPageUrl() {
        return currentShowPageUrl;
    }

    /**
     * 打开外部浏览器
     */
    public void openExternalBrowser() {
        openExternalBrowser(currentShowPageUrl);
    }

    /**
     * 打开外部浏览器
     */
    public void openExternalBrowser(String url) {
        Utils.openDefaultBrowser(currentShowPageUrl);
    }

}
