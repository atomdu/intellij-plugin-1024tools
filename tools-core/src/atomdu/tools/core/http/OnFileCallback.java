package atomdu.tools.core.http;

import com.mb3364.http.FileHttpResponseHandler;

import java.io.File;

/**
 * Created by atomdu on 2017/12/15.
 */
public abstract class OnFileCallback extends FileHttpResponseHandler {
    public OnFileCallback(File file) {
        super(file);
    }
}
