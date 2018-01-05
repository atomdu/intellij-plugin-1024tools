package atomdu.intellij.plugin.panel;

import atomdu.intellij.plugin.utils.IdeaUtils;
import atomdu.tool.tanslate.TranslatePanel;
import atomdu.tools.core.http.OnStringCallback;
import atomdu.tools.core.notify.NotifyFactory;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by atomdu on 2017/12/4.
 */
public class IntellijTranslatePanel extends TranslatePanel {
    private Editor transEditor;
    private Document document;
    private JComponent transPanel;

    private AnActionEvent currentEvent;

    private String documentText;
    private FileType fileType;
    private Project project;

    public IntellijTranslatePanel() {
//        造成PhpStorm的编辑窗口无法输入数据
//        ActionManager.getInstance().addAnActionListener(new AnActionListener() {
//            @Override
//            public void beforeActionPerformed(AnAction anAction, DataContext dataContext, AnActionEvent anActionEvent) {
//                System.out.println(anAction.getClass().getName());
//                if (!isShowing() && false) {
//                    return;
//                }
//                if (anAction instanceof EditorAction) {
//                    doTranslate(anActionEvent);
//                }
//            }
//        });

    }

    @Override
    protected void initToolbarClickUI(JPanel toolbar) {
        JButton button = new JButton("翻译");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                translate();
            }
        });
        toolbar.add(button);
    }

    @Override
    public String getFrom() {
        return documentText;
    }

    @Override
    public String getFileType() {
        return "";
    }

    private void translate() {
        Editor editor = IdeaUtils.getCurrentEditor();
        translate(editor);
    }

    private void translate(AnActionEvent anActionEvent) {
        Editor editor = IdeaUtils.getEditor(anActionEvent);
        translate(editor);
    }

    private void translate(Editor editor) {
        documentText = IdeaUtils.getDocumentText(editor);
        fileType = IdeaUtils.getFileType(editor);
        project = IdeaUtils.getProject();
        translate(documentText);
    }

    @Override
    public void onStart(OnStringCallback onCallback) {

    }

    @Override
    public void onProgress(OnStringCallback onCallback, int all, int progress) {

    }

    @Override
    public void onSuccess(OnStringCallback onCallback, byte[] bytes, String result, String s) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                transEditor = IdeaUtils.createEditor(result, fileType);
                transPanel = transEditor.getComponent();
                setContentPanel(transPanel);
            }
        });
    }

    @Override
    public void onFail(OnStringCallback onCallback, Exception e, int code, String msg) {
        NotifyFactory.getInstance().getNotify().error("请求失败，请稍后重试");
    }
}
