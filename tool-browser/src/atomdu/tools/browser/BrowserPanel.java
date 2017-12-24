package atomdu.tools.browser;

import java.awt.*;

/**
 * Created by atomdu on 2017/12/4.
 */
public class BrowserPanel extends BrowserBodyPanel implements BrowserInputToolbar.ToolbarListener {
    private BrowserInputToolbar toolbar;

    public BrowserPanel() {
        toolbar = new BrowserInputToolbar();
        toolbar.setListener(this);
        add(toolbar, BorderLayout.NORTH);
    }

    @Override
    public void onSearch(String url, String search) {
        load(url + search);
    }

    public BrowserInputToolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(BrowserInputToolbar toolbar) {
        this.toolbar = toolbar;
    }
}
