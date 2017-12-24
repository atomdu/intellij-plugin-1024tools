package atomdu.tools.core.http;

/**
 * 解析数据异常
 * Created by atomdu on 2017/12/12.
 */
public class ParseException extends RuntimeException {
    public ParseException() {
        super("解析数据异常");
    }

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }
}
