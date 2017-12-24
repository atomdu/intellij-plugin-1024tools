package atomdu.tools.core.swing;

import atomdu.tools.core.View;
import atomdu.tools.core.bean.Navigation;
import atomdu.tools.core.bean.NavigationDiv;
import atomdu.tools.core.bean.NavigationItem;
import atomdu.tools.core.config.Config;
import atomdu.tools.core.http.*;
import atomdu.tools.core.log.Log;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;


/**
 * 导航页面
 * Created by atomdu on 2017/12/12.
 */
public class NavigationPanel extends JScrollPane implements View {
    //    protected Map<String, List<NavigationItem>> map;
    protected Navigation navigation;
    protected NavigationOnCallback onCallback = new NavigationOnCallback();
    protected JPanel contentPanel = new JPanel();
    protected String url;
    protected AsyncHttpClient httpClient;
    protected String displayName;

    public NavigationPanel(String url) {
        //this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        this.contentPanel.setLayout(new VFlowLayout());
        setViewportView(contentPanel);
        this.url = url;
        this.httpClient = ApiHelper.getNavigation(url, onCallback);
        buildView();
    }

    /**
     * 内容样式1
     *
     * @param navigation
     */
    public void setContentView(Navigation navigation) {
        if (navigation == null) {
            return;
        } else {
            this.navigation = navigation;
            contentPanel.removeAll();
            //导航数据
            List<NavigationDiv> divs = navigation.getNavigationDivList();

            if (divs != null && divs.size() > 0) {
                for (NavigationDiv div : divs) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(0, div.getCols()));
                    panel.setBorder(new TitledBorder(new LineBorder(Color.lightGray), div.getName()));
                    contentPanel.add(panel);

                    List<NavigationItem> list = div.getNavigations();
                    for (NavigationItem b : list) {
                        panel.add(new NavigationLabel(b, SwingConstants.CENTER));
                    }
                }
            }
        }
    }

    public NavigationOnCallback getOnCallback() {
        return onCallback;
    }

    public void setOnCallback(NavigationOnCallback onCallback) {
        this.onCallback = onCallback;
    }

    @Override
    public void show(boolean b) {
        super.show(b);
        Log.d(getDisplayName(), "show:" + b);
        if (b) {
            updateView();
        } else {
            destroyView();
        }
    }

    @Override
    public void buildView() {

    }

    @Override
    public void updateView() {
        if (Config.DEBUG) {
            httpClient.execute();
        } else {
            if (navigation == null) {
                httpClient.execute();
            }
        }
    }

    @Override
    public void destroyView() {

    }

    public class NavigationOnCallback extends OnObjectCallback<Navigation> {
        @Override
        public void onStart(HttpURLConnection httpURLConnection) {
            super.onStart(httpURLConnection);

        }

        @Override
        public void onSuccess(int statusCode, Map<String, List<String>> headers, Navigation content) {
            setContentView(content);
        }

        @Override
        public void onFailure(int statusCode, Map<String, List<String>> headers, String content) {

        }

        @Override
        public void onFailure(Throwable throwable) {

        }

    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
