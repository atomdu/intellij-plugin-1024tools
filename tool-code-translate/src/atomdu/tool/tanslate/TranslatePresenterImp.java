package atomdu.tool.tanslate;

import atomdu.tool.tanslate.api.ApiClient;
import atomdu.tool.tanslate.api.ApiClientFactory;

/**
 * Created by atomdu on 2017/12/5.
 */
public class TranslatePresenterImp implements TranslatePresenter {
    private TranslateView view;

    public TranslatePresenterImp(TranslateView view) {
        this.view = view;
    }

    @Override
    public void translate(String from) {
        String fromType = view.getFromType();
        String fileType = view.getFileType();
        String toStyle = view.getToStyle();
        String toType = view.getToType();
        translate(from, fromType, fileType, toStyle, toType);
    }

    @Override
    public void translate(String from, String fromType, String fileType, String toStyle, String toType) {
        String apiClientType = view.getApiClientType();
        ApiClient apiClient = ApiClientFactory.get(apiClientType);
        apiClient.doRequest(from, fromType, fileType, toStyle, toType, view.getOnStringCallback());
    }
}
