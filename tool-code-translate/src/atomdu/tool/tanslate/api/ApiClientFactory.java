package atomdu.tool.tanslate.api;

/**
 * Created by atomdu on 2017/12/5.
 */
public class ApiClientFactory {
    public static final String API_TYPE_LOCAL = "本地翻译";
    public static final String API_TYPE_HTTP = "网络翻译";

    //public static final String[] API_CLIENT_TYPES = {API_TYPE_LOCAL, API_TYPE_HTTP};
    public static final String[] API_CLIENT_TYPES = {API_TYPE_LOCAL, API_TYPE_HTTP};

    public static ApiClient get(String apiType) {
        if (apiType == API_TYPE_LOCAL)
            return new LocalApiClient();
        else if (apiType == API_TYPE_HTTP)
            return new HttpApiClient();
        else
            return null;
    }
}
