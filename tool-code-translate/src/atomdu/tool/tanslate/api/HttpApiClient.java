package atomdu.tool.tanslate.api;

import atomdu.tool.tanslate.dao.LocalDOM;
import atomdu.tools.core.config.ConfigManager;
import atomdu.tools.core.http.AsyncHttpClient;
import atomdu.tools.core.http.OnFileCallback;
import atomdu.tools.core.http.OnStringCallback;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by atomdu on 2017/12/5.
 */
public class HttpApiClient implements ApiClient {
    public static final String EDITOR = ConfigManager.getURL("/translate-service-tp5/public/translate/code/api");
    public static final String WORDS = ConfigManager.getURL("/modules/translate/word.properties");

    /**
     * 获取单词数据
     * @param onStringCallback
     * @return
     */
    public AsyncHttpClient getLocalWord(OnStringCallback onStringCallback) {
        return new AsyncHttpClient.Builder().url(WORDS).get().onStringCallback(onStringCallback).build();
    }

    /**
     * 获取单词数据
     * @param onFileCallback
     * @return
     */
    public AsyncHttpClient getLocalWord(OnFileCallback onFileCallback) {
        return new AsyncHttpClient.Builder().url(WORDS).get().onFileCallback(onFileCallback).build();
    }

    /**
     * @param from       查询的内容
     * @param fromType   查询的内容的类型,现在是翻译不翻译关键字
     * @param fileType   文件类型
     * @param onCallback
     */
    @Override
    public void doRequest(String from, String fromType, String fileType, String toType, String isKeyword, OnStringCallback onCallback) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(EDITOR);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("from", from));
        params.add(new BasicNameValuePair("fromType", fromType));
        params.add(new BasicNameValuePair("fileType", fileType));
        params.add(new BasicNameValuePair("toType", toType));
        params.add(new BasicNameValuePair("isKeyword", isKeyword));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String body = EntityUtils.toString(entity);
                    System.out.println("Response content: " + body);
                    if (onCallback != null) {
                        onCallback.onSuccess(0, null, body);
                    }
                }
            } finally {
                response.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            if (onCallback != null) {
                onCallback.onFailure(0, null, "fail");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            if (onCallback != null) {
                onCallback.onFailure(0, null, "fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (onCallback != null) {
                onCallback.onFailure(0, null, "fail");
            }
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
