package atomdu.tools.browser;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.web.*;
import javafx.util.Callback;

import javax.swing.*;
import java.awt.*;

/**
 * 浏览器面板
 * TODO 用try...catch 包裹住浏览器异常退出的部分试一试还会不会退出
 * Created by atomdu on 2017/11/23.
 */
public class BrowserBodyPanel extends JPanel implements ChangeListener<Worker.State>, ListChangeListener<WebHistory.Entry> {

    protected JFXPanel webFXPanel;
    protected WebView webView;
    protected WebEngine webEngine;
    protected WebHistory webHistory;

    protected String currentShowPageUrl;//当前显示的页面url

    public BrowserBodyPanel() {
        setBrowserPanel();
    }

    protected void setBrowserPanel() {
        setLayout(new BorderLayout());

        //webView
        webFXPanel = new JFXPanel();
        webFXPanel.setLayout(new BorderLayout());
        add(webFXPanel, BorderLayout.CENTER);
        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initWebWiew(webFXPanel);
            }
        });
    }

    protected void initWebWiew(JFXPanel webFXPanel) {
        webFXPanel.removeAll();
        Group root = new Group();
        root.setAutoSizeChildren(true);
        Scene scene = new Scene(root);
        webFXPanel.setScene(scene);

        webView = new WebView();
        webView.prefWidthProperty().bind(root.getScene().widthProperty());
        webView.prefHeightProperty().bind(root.getScene().heightProperty());
        webView.setZoom(0.9);
        webEngine = webView.getEngine();
        //webEngine.load();
        webEngine.getLoadWorker().stateProperty().addListener(this);//ChangeListener
        webEngine.getHistory().getEntries().addListener(this);//ListChangeListener
        webEngine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                Desktop d = Desktop.getDesktop();
//                URI address;
//                try {
//                    if (!observable.getValue().contentEquals(defaultHostUrl)) {
//                        address = new URI(observable.getValue());
//                        d.browse(address);
//                    }
//                } catch (URISyntaxException | IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
        webHistory = webEngine.getHistory();

        root.getChildren().add(webView);

        webEngine.setConfirmHandler(new Callback<String, Boolean>() {
            @Override
            public Boolean call(String param) {
                //Log.d(this, "setConfirmHandler:" + param);
                return null;
            }
        });
        webEngine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
            @Override
            public WebEngine call(PopupFeatures param) {
                //Log.d(this, "setCreatePopupHandler:" + param.toString());

                return webEngine;
            }
        });
        webEngine.setPromptHandler(new Callback<PromptData, String>() {
            @Override
            public String call(PromptData param) {
                //Log.d(this, "setPromptHandler:" + param.toString());
                return null;
            }
        });
        webView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                //Log.d(this, "setOnContextMenuRequested:" + event.toString());
            }
        });

        ObjectProperty<EventHandler<? super ContextMenuEvent>> property = webView.onContextMenuRequestedProperty();

    }

    public void load(final String url) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webEngine.load(url);
            }
        });
    }

    /**
     * 当前显示的页面的URL
     *
     * @return
     */
    public String getCurrentShowPageUrl() {
        return currentShowPageUrl;
    }

    /**
     * 打开外部浏览器
     */
    public void openExternalBrowser() {
        Utils.openDefaultBrowser(currentShowPageUrl);
    }

    //ChangeListener接口的方法
    @Override
    public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
        switch (newValue) {
            /**
             * Indicates that this Worker has been cancelled via the {@link #cancel()}
             * method.
             */
            case CANCELLED:
                System.out.println("cancelled");
                break;
            /**
             * Indicates that this Worker has completed successfully, and that there
             * is a valid result ready to be read from the <code>value</code> property.
             */
            case SUCCEEDED:
                System.out.println("succeeded");
                //currentShowPageUrl = webHistory.getEntries().get(webHistory.getCurrentIndex()).getUrl();
                break;
            /**
             * Indicates that this Worker has failed, usually due to some unexpected
             * condition having occurred. The exception can be retrieved from the
             * <code>exception</code> property.
             */
            case FAILED:
                System.out.println("failed");
                webEngine.loadContent("请求数据失败，请重试");
                //currentShowPageUrl = webHistory.getEntries().get(webHistory.getCurrentIndex()).getUrl();
                break;
        }
    }

    //ListChangeListener接口的方法
    @Override
    public void onChanged(Change<? extends WebHistory.Entry> c) {
        //currentOpenedUrl = webHistory.getEntries().get(webHistory.getCurrentIndex()).getUrl();
    }

    public void init2(JFXPanel fxWebBrowser) {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        final TabPane tp = new TabPane();
        WebView webView = new WebView();
        webView.prefWidthProperty().bind(root.getScene().widthProperty());
        webView.prefHeightProperty().bind(root.getScene().heightProperty());
        final WebEngine webEngine = webView.getEngine();
        webEngine.load("https://m.baidu.com/s?ie=utf-8&word=");
        webEngine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {

            @Override
            public WebEngine call(PopupFeatures arg0) {
                WebView newWebView = new WebView();
                WebEngine newWebEngine = newWebView.getEngine();
                Tab tab = new Tab("new Window");
                tab.textProperty().bind(newWebEngine.titleProperty());
                tab.setContent(newWebView);
                tp.getTabs().add(tab);
                return newWebEngine;
            }
        });
        final Tab tab = new Tab();
        tab.textProperty().bind(webEngine.titleProperty());
        tab.setContent(webView);
        // process page loading
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> ov,
                                        Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            tp.getTabs().add(tab);
                        }
                    }
                }
        );
        root.getChildren().add(tp);
        fxWebBrowser.setScene(scene);
    }
}
