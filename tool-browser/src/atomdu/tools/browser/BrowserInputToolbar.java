package atomdu.tools.browser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by atomdu on 2017/11/27.
 */
public class BrowserInputToolbar extends JPanel {
    private Checkbox autoInputCheckbox;//自动输入
    private JTextField inputTextField;//输入框
    private JButton inputButton;//输入确定按钮

    private Checkbox autoSearchCheckbox;//自动搜索
    private Map<String, String> map;

    private ToolbarListener listener;
    private String currentUrl;
    private String searchText;

    public BrowserInputToolbar() {
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        autoInputCheckbox = new Checkbox("自动输入", true);
        inputTextField = new JTextField();
        inputTextField.setEnabled(true);
        inputTextField.setPreferredSize(new Dimension(300, 26));
        inputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    listener.onSearch(currentUrl, getSearchText());
                }
            }
        });
        inputButton = new JButton("确定");
        inputButton.setMargin(new Insets(0, 0, 0, 0));
        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onSearch(currentUrl, getSearchText());
            }
        });

        inputPanel.add(autoInputCheckbox);
        inputPanel.add(inputTextField);
        inputPanel.add(inputButton);
        this.add(inputPanel, BorderLayout.NORTH);

        //
        JPanel hostPanel = new JPanel();
        hostPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        autoSearchCheckbox = new Checkbox("自动搜索", true);
        hostPanel.add(autoSearchCheckbox);
        map = new LinkedHashMap<>();
        map.put("百度", "https://www.baidu.com/s?ie=utf-8&wd=");
        map.put("Github", "https://github.com/search?utf8=✓&q=");
        map.put("StackOverFlow", "https://stackoverflow.com/search?q=");
        map.put("辞海翻译", "http://dict.cn/");
        map.put("Atomdu", "http://atomdu.com");

        for (String name : map.keySet()) {
            JLabel jLabel = new JLabel(name);
            jLabel.addMouseListener(new SearchAdapter(jLabel));
            hostPanel.add(jLabel);
            if (currentUrl == null) {
                currentUrl = map.get(name);
            }
        }
        this.add(hostPanel, BorderLayout.CENTER);
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
        inputTextField.setText(searchText);
    }

    public String getCurrentUrl() {
        if (currentUrl == null) {
            if (map != null && map.size() > 0) {
            }
        }
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public String getSearchText() {
        searchText = inputTextField.getText();
        return searchText;
    }

    public boolean isAutoInput() {
        return autoInputCheckbox.getState();
    }

    public boolean isAutoSearch() {
        return autoSearchCheckbox.getState();
    }

    public ToolbarListener getListener() {
        return listener;
    }

    public void setListener(ToolbarListener listener) {
        this.listener = listener;
    }


//    /**
//     * 浏览器配置
//     * Created by atomdu on 2017/11/5.
//     */
//    public class BrowserConfigAction extends DefaultAction {
//        public BrowserConfigAction() {
//            super("config", "", AllIcons.General.ConfigurableDefault);
//        }
//
//        @Override
//        public void actionPerformed(AnActionEvent anActionEvent) {
//        }
//    }
//
//    /**
//     * 打开外部浏览器
//     */
//    public static class OpenExternalBrowser extends DefaultAction {
//        public OpenExternalBrowser() {
//            super("open", "", AllIcons.Xml.Browsers.Firefox16);
//        }
//
//        @Override
//        public void actionPerformed(AnActionEvent anActionEvent) {
//            super.actionPerformed(anActionEvent);
////            openExternalBrowser();
//        }
//    }

    public class SearchAdapter extends MouseAdapter {
        JLabel jLabel;

        public SearchAdapter(JLabel p) {
            this.jLabel = p;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            String name = jLabel.getText();
            currentUrl = BrowserInputToolbar.this.map.get(name);
            if (currentUrl != null) {
                listener.onSearch(currentUrl, getSearchText());
            }
        }
    }

    public static interface ToolbarListener {
        void onSearch(String url, String query);
    }
}