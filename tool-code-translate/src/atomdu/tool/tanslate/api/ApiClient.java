package atomdu.tool.tanslate.api;

import atomdu.tools.core.http.OnStringCallback;

/**
 * Created by atomdu on 2017/12/5.
 */
public interface ApiClient {
    /**
     * @param from       请求翻译的数据
     * @param fromType   数据的类型
     * @param fileType   文件类型
     * @param toStyle    翻译成的样式：追加，逐加，同行末尾，上一行，下一行
     * @param toType     翻译成的类形：翻译关键字，不翻译关键字
     * @param onCallback
     */
    void doRequest(String from, String fromType, String fileType, String toStyle, String toType, OnStringCallback onCallback);
}
