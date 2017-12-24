package atomdu.tools.core.notify;

/**
 * 通知
 * Created by atomdu on 2017/12/10.
 */
public interface Notify {
    void alert(String str);

    void warn(String str);

    void error(String str);
}
