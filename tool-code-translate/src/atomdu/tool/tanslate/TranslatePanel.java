package atomdu.tool.tanslate;


import atomdu.tool.tanslate.api.HttpApiClient;
import atomdu.tool.tanslate.config.KeywordConfig;
import atomdu.tool.tanslate.api.ApiClientFactory;
import atomdu.tool.tanslate.config.ToStyle;
import atomdu.tool.tanslate.dao.LocalDOM;
import atomdu.tools.core.http.OnStringCallback;
import atomdu.tools.core.utils.SystemUtils;
import com.siyeh.ig.ui.*;
import com.siyeh.ig.ui.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.List;

/**
 * Created by atomdu on 2017/12/2.
 */
public abstract class TranslatePanel extends JPanel implements TranslateView, TranslatePresenter {
    private TranslatePresenter translatePresenter;

    private JComboBox apiClientTypeCB;
    private JComboBox toTypeCB;
    private JComboBox keywordCB;

    private JPanel contentRootPanel;

    private boolean isInitWords;

    public TranslatePanel() {
        setLayout(new BorderLayout());
        //UI
        initToolbar();
        //主体根
        initContentRootPanel();
        //控制器
        translatePresenter = new TranslatePresenterImp(this);
    }

    private void initContentRootPanel() {
        contentRootPanel = new JPanel();
        contentRootPanel.setLayout(new BorderLayout());
        add(contentRootPanel, BorderLayout.CENTER);
    }

    public JPanel getContentRootPanel() {
        return contentRootPanel;
    }

    public void setContentPanel(JComponent contentPanel) {
        this.contentRootPanel.removeAll();
        this.contentRootPanel.add(contentPanel, BorderLayout.CENTER);
    }

    protected void initToolbar() {
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(toolbar, BorderLayout.NORTH);
        initPayUI(toolbar);
        initToolbarApiClientTypeUI(toolbar);
        initTollbarToStyleUI(toolbar);
        initTollbarKeywordUI(toolbar);
        initToolbarClickUI(toolbar);
        initStatisticsUI(toolbar);

    }

    /**
     * 帮助页面
     * @param toolbar
     */
    protected void initPayUI(JPanel toolbar) {
        JLabel h = new JLabel(Help.getHelpName());
        h.setForeground(Color.decode("#dea87a"));
        h.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO 帮助页面
                SystemUtils.openDefaultBrowser(Help.getHelpUrl());
            }
        });
        toolbar.add(h);
    }

    /**
     * api服务类型，网络还是本地
     *
     * @param jPanel
     */
    protected void initToolbarApiClientTypeUI(JPanel jPanel) {
        apiClientTypeCB = new JComboBox(ApiClientFactory.API_CLIENT_TYPES);
        apiClientTypeCB.setSelectedIndex(0);
        apiClientTypeCB.setVisible(false);
        jPanel.add(apiClientTypeCB);
    }

    /**
     * 翻译成什么格式
     *
     * @param jPanel
     */
    protected void initTollbarToStyleUI(JPanel jPanel) {
        toTypeCB = new JComboBox(ToStyle.TO_TYPES);
        toTypeCB.setSelectedIndex(0);
        jPanel.add(toTypeCB);
    }

    /**
     * 是否翻译关键字
     *
     * @param jPanel
     */
    protected void initTollbarKeywordUI(JPanel jPanel) {
        keywordCB = new JComboBox(KeywordConfig.TRANS);
        keywordCB.setSelectedIndex(0);
        keywordCB.setVisible(false);
        jPanel.add(keywordCB);
    }

    /**
     * 确定按钮
     *
     * @param toolbar
     */
    protected void initToolbarClickUI(JPanel toolbar) {


    }
    /**
     * 统计
     *
     * @param jPanel
     */
    protected void initStatisticsUI(JPanel jPanel) {

    }

    @Override
    public String getApiClientType() {
        String apiClientType = (String) apiClientTypeCB.getItemAt(apiClientTypeCB.getSelectedIndex());
        return apiClientType;
    }

    @Override
    public String getFromType() {
        return KeywordConfig.TRANS_TYPES[keywordCB.getSelectedIndex()] + "";
    }

    @Override
    public abstract String getFrom();

    @Override
    public abstract String getFileType();

    @Override
    public String getToType() {
        return "";
    }

    @Override
    public String getToStyle() {
        String toType = ToStyle.TO_TYPES[toTypeCB.getSelectedIndex()];
        return toType;
    }

    @Override
    public void translate(String from) {
        if (!isInitWords) {
            HttpApiClient wordApiClient = new HttpApiClient();
            wordApiClient.getLocalWord(new OnStringCallback() {
                @Override
                public void onStart(HttpURLConnection httpURLConnection) {
                    super.onStart(httpURLConnection);
                    JLabel textField = new JLabel("初始化词库中，请稍后", SwingConstants.CENTER);
                    setContentPanel(textField);
                }

                @Override
                public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                    isInitWords = true;
                    LocalDOM.getInstance().init(content);
                    translatePresenter.translate(from);
                }

                @Override
                public void onFailure(int statusCode, Map<String, List<String>> headers, String content) {
                    isInitWords = false;
                    JLabel textField = new JLabel("初始化词库失败，请重试", SwingConstants.CENTER);
                    setContentPanel(textField);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    isInitWords = false;
                    JLabel textField = new JLabel("初始化词库失败，请检查网络链接", SwingConstants.CENTER);
                    setContentPanel(textField);
                }
            }).execute();
        } else {
            translatePresenter.translate(from);
        }
    }

    @Override
    public void translate(String from, String fromType, String fileType, String toStyle, String toType) {
        translatePresenter.translate(from, fromType, fileType, null, toType);
    }


    @Override
    public OnStringCallback getOnStringCallback() {
        return new OnStringCallback() {
            @Override
            public void onStart(HttpURLConnection httpURLConnection) {
                super.onStart(httpURLConnection);
                TranslatePanel.this.onStart(this);
            }

            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                TranslatePanel.this.onSuccess(this, null, content, content);
            }

            @Override
            public void onFailure(int statusCode, Map<String, List<String>> headers, String content) {
                TranslatePanel.this.onFail(this, null, -1, content);
            }

            @Override
            public void onFailure(Throwable throwable) {
                TranslatePanel.this.onFail(this, null, -1, null);
            }
        };
    }

    public abstract void onStart(OnStringCallback onCallback);

    public abstract void onProgress(OnStringCallback onCallback, int all, int progress);

    public abstract void onSuccess(OnStringCallback onCallback, byte[] bytes, String result, String o);

    public abstract void onFail(OnStringCallback onCallback, Exception e, int code, String msg);

}
