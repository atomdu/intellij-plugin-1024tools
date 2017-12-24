package atomdu.tools.core;

/**
 * Created by atomdu on 2017/12/11.
 */
public interface View {
    void buildView();

    void updateView();

    void destroyView();

    default String getDisplayName() {
        return null;
    }
}
