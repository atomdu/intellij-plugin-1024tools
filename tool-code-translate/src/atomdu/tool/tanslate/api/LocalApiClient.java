package atomdu.tool.tanslate.api;

import atomdu.tool.tanslate.processor.ProcessorChain;
import atomdu.tools.core.http.OnStringCallback;

/**
 * Created by atomdu on 2017/12/5.
 */
public class LocalApiClient implements ApiClient {
    @Override
    public void doRequest(String from, String fromType, String fileType, String toStyle, String toType, OnStringCallback onCallback) {
        onCallback.onStart(null);
        ProcessorChain chain = new ProcessorChain();
        chain.setToStyle(toStyle);
        String result = chain.onProcessor(from);
        if (onCallback != null)
            onCallback.onSuccess(0, null, result);
    }


}
