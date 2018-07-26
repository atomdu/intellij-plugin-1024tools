package atomdu.intellij.plugin.panel;

import atomdu.intellij.plugin.AtomduApplication;
import atomdu.intellij.plugin.utils.IdeaUtils;
import atomdu.tool.tanslate.TranslatePanel;
import atomdu.tool.tanslate.dao.LocalDOM;
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
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by atomdu on 2017/12/4.
 */
public class IntellijTranslatePanel extends TranslatePanel implements FileEditorManagerListener {
    private Editor transEditor;
    private Document document;
    private JComponent transPanel;

    private AnActionEvent currentEvent;

    private String documentText;
    private FileType fileType;
    private Project project;

    public IntellijTranslatePanel() {
        AtomduApplication.getInstance().addFileEditorManagerListenerList(this);
        translate();
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
    protected void initStatisticsUI(JPanel jPanel) {
        JButton all = new JButton("全部统计");
        all.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String str = LocalDOM.getInstance().getCurrentStatistics();
                transEditor = IdeaUtils.createEditor(str, null);
                transPanel = transEditor.getComponent();
                setContentPanel(transPanel);
            }
        });
        jPanel.add(all);
    }

    @Override
    public String getFrom() {
        return documentText;
    }

    @Override
    public String getFileType() {
        String str = (fileType == null) ? "" : fileType.getDefaultExtension();
        return str;
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

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {

    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {

    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        if (!isShowing() || !autoTranslateCB.isSelected()) return;

        EditorImpl editor = (EditorImpl) event.getManager().getSelectedTextEditor();
        translate(editor);
    }
}
