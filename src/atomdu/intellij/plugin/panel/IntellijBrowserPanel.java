package atomdu.intellij.plugin.panel;

import atomdu.intellij.plugin.AtomduApplication;
import atomdu.intellij.plugin.utils.IdeaUtils;
import atomdu.tools.browser.BrowserPanel;
import atomdu.tools.core.log.Log;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.actionSystem.impl.ActionManagerImpl;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;

/**
 * Created by atomdu on 2017/12/4.
 */
public class IntellijBrowserPanel extends BrowserPanel implements CaretListener {
    public IntellijBrowserPanel() {
        super();
        AtomduApplication.getInstance().addCaretListenerList(this);
    }

    @Override
    public void caretPositionChanged(CaretEvent caretEvent) {
        //当前显示
        if (isShowing()) {
            Caret caret = caretEvent.getCaret();
            if (caret != null && caret.hasSelection()) {//选中模式
                String str = caret.getSelectedText().trim();
                //获取选中内容
                if (getToolbar().isAutoInput()) {
                    getToolbar().setSearchText(str);
                }
                if (getToolbar().isAutoSearch()) {
                    onSearch(getToolbar().getCurrentUrl(), str);
                }
                Log.d("CaretListener ", "caretPositionChanged:" + caret.getSelectedText());
            } else { //非选中模式
                //什么都不做
                //Log.d("CaretListener ", "caretEvent.getCaret() is null:");
            }
        }
    }

    @Override
    public void caretAdded(CaretEvent caretEvent) {

    }

    @Override
    public void caretRemoved(CaretEvent caretEvent) {

    }
}
