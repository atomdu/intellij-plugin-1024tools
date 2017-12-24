package atomdu.tools.core.swing;

import atomdu.tools.core.PanelManager;
import atomdu.tools.core.View;
import atomdu.tools.core.bean.RootBean;
import atomdu.tools.core.http.ApiHelper;
import atomdu.tools.core.http.AsyncHttpClient;
import atomdu.tools.core.http.OnObjectCallback;

import javax.swing.*;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * 根面板
 * Created by atomdu on 2017/12/4.
 */
public class RootPanel extends JPanel implements View {
    private OnRootBeanCallback onRootBeanCallback = new OnRootBeanCallback();
    private AsyncHttpClient httpClient;
    private RootBean rootBean;
    private boolean isRequest;
    private RootPanelListener listener;

    public RootPanel() {
        httpClient = ApiHelper.getCoreApi(onRootBeanCallback);
        buildView();
    }

    @Override
    public void show(boolean b) {
        super.show(b);
        System.out.println("show:" + b);
    }

    @Override
    public void buildView() {
        httpClient.execute();
    }

    @Override
    public void updateView() {
        httpClient.execute();
    }

    @Override
    public void destroyView() {

    }

    @Override
    public String getDisplayName() {
        if (rootBean != null)
            return rootBean.getName();
        else
            return null;
    }


    public class OnRootBeanCallback extends OnObjectCallback<RootBean> {
        @Override
        public void onStart(HttpURLConnection httpURLConnection) {
            super.onStart(httpURLConnection);
            isRequest = true;
        }

        @Override
        public void onSuccess(int statusCode, Map<String, List<String>> headers, RootBean rootBean) {
            RootPanel.this.rootBean = rootBean;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    PanelManager.getInstance().createRootComponent(RootPanel.this, rootBean);
                }
            });
            //设置监听改变
            if (rootBean != null && listener != null) {
                listener.onChanged(rootBean);
            }
            isRequest = false;
        }

        @Override
        public void onFailure(int statusCode, Map<String, List<String>> headers, String content) {
            isRequest = false;
        }

        @Override
        public void onFailure(Throwable throwable) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    PanelManager.getInstance().createErrorComponent(RootPanel.this);
                }
            });
            isRequest = false;
        }
    }

    public RootPanelListener getListener() {
        return listener;
    }

    public void setListener(RootPanelListener listener) {
        this.listener = listener;
    }

    public static interface RootPanelListener {
        void onChanged(RootBean rootBean);

        boolean isDisposed();
    }
}
