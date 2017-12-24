package atomdu.tools.core.http;

import com.mb3364.http.StringHttpResponseHandler;

import java.util.List;
import java.util.Map;

/**
 * Created by atomdu on 2017/12/15.
 */
public abstract class OnObjectCallback<T> extends StringHttpResponseHandler {
    private Parser<T> parser;

    public Parser<T> getParser() {
        return parser;
    }

    public void setParser(Parser<T> parser) {
        this.parser = parser;
    }

    public T parse(String str) throws ParseException {
        if (parser != null)
            return parser.parse(str);
        else
            throw new ParseException("没有定义解析器");
    }

    @Override
    public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
        try{
            T t = parse(content);
            onSuccess(statusCode, headers, t);
        }catch (ParseException e){
            onFailure(-1,headers,content);
            onFailure(e);
        }
    }

    @Override
    public abstract void onFailure(int statusCode, Map<String, List<String>> headers, String content);

    @Override
    public abstract void onFailure(Throwable throwable);

    public abstract void onSuccess(int statusCode, Map<String, List<String>> headers, T content);

}
