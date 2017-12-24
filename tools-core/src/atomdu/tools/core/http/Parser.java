package atomdu.tools.core.http;

import com.google.gson.JsonSyntaxException;

/**
 * 解析器
 * Created by atomdu on 2017/12/12.
 */
public interface Parser<T> {
    T parse(String str) throws ParseException;

    T parse(byte[] bytes) throws ParseException;
}
