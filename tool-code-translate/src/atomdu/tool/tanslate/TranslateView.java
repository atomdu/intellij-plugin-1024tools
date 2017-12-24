package atomdu.tool.tanslate;

import atomdu.tools.core.http.OnStringCallback;

/**
 * Created by atomdu on 2017/12/5.
 */
public interface TranslateView {
    String getApiClientType();//ApiClientFactory.API_TYPE_LOCAL;

    String getFrom();

    String getFromType();

    String getFileType();

    String getToStyle();

    String getToType();

    OnStringCallback getOnStringCallback();
}
