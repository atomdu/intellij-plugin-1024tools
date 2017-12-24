package atomdu.tool.tanslate;

/**
 * 翻译控制器
 * Created by atomdu on 2017/12/5.
 */
public interface TranslatePresenter {
    void translate(String from);

    void translate(String from, String fromType, String fileType, String toStyle, String toType);
}
