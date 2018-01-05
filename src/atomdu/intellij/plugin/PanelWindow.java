package atomdu.intellij.plugin;

import atomdu.tools.core.swing.RootPanel;
import atomdu.tools.core.bean.RootBean;
import atomdu.tools.core.config.ConfigManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Created by atomdu on 2017/12/4.
 */
public class PanelWindow implements ToolWindowFactory, RootPanel.RootPanelListener {
    private ToolWindowImpl toolWindow;
    private Project project;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.project = project;
        this.toolWindow = (ToolWindowImpl) toolWindow;

        RootPanel window = new RootPanel();
        window.setListener(this);

        // 将显示面板添加到显示区 2017/3/18 19:57
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(window, "", true);
        toolWindow.getContentManager().addContent(content);
    }

    @Override
    public void onChanged(RootBean rootBean) {
    }

    @Override
    public boolean isDisposed() {
        return toolWindow.isDisposed();
    }
}
