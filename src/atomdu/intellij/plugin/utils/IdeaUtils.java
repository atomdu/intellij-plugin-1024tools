package atomdu.intellij.plugin.utils;

import atomdu.intellij.plugin.TranslateEditorComponentImpl;
import atomdu.tools.core.notify.NotifyFactory;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.ide.DataManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ex.ClipboardUtil;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.impl.EditorComponentImpl;
import com.intellij.openapi.editor.impl.EditorFactoryImpl;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import javafx.application.Platform;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/24.
 */
public class IdeaUtils {
    public static void showErrorHint(Editor editor) {
        HintManager.getInstance().showErrorHint(editor, "showErrorHint");
    }

    /**
     * 读文件
     *
     * @param runnable
     */
    public static void runRead(Runnable runnable) {
        ApplicationManager.getApplication().runReadAction(runnable);
    }

    /**
     * 写文件
     *
     * @param runnable
     */
    public static void runWrite(Runnable runnable) {
        ApplicationManager.getApplication().runWriteAction(runnable);
    }

    /**
     * 创建Editor
     *
     * @param content  文本内容
     * @param fileType 文件类型
     * @return
     */
    public static Editor createEditor(String content, FileType fileType) {
        if (content == null || content.length() == 0) {
            NotifyFactory.getInstance().getNotify().error("内容不能为空");
            return null;
        }
        Document document = EditorFactory.getInstance().createDocument(content);
        Project project = getProject();
        Editor editor = createEditor(content, project, fileType, false);
        return editor;
    }

    /**
     * 创建Editor
     *
     * @param content  文本内容
     * @param project  项目
     * @param fileType 文件类型
     * @param v
     * @return
     */
    public static Editor createEditor(String content, Project project, FileType fileType, boolean v) {
        if (content == null || content.length() == 0) {
            NotifyFactory.getInstance().getNotify().error("内容不能为空");
            return null;
        }
        Document document = EditorFactory.getInstance().createDocument(content);
        EditorImpl editor = null;
        if (fileType != null) {
            editor = (EditorImpl) EditorFactory.getInstance().createEditor(document, project, fileType, v);
        } else {
            editor = (EditorImpl) EditorFactory.getInstance().createEditor(document);
        }
        return editor;
    }

    /**
     * 注入
     */
    public static void injectEditor(EditorImpl editor,String result){
        Class clazz = editor.getClass();
        try {
            Field field = clazz.getDeclaredField("myScrollPane");
            field.setAccessible(true);
            JScrollPane sp = (JScrollPane) field.get(editor);

            Field field2 = clazz.getDeclaredField("myEditorComponent");
            field2.setAccessible(true);
            EditorComponentImpl sp2 = (EditorComponentImpl) field2.get(editor);

            TranslateEditorComponentImpl editorComponent = new TranslateEditorComponentImpl(editor);
            editorComponent.setText(result);

            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            jPanel.add(sp2,BorderLayout.CENTER);
            jPanel.add(editorComponent,BorderLayout.EAST);
            sp.setViewportView(jPanel);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放Editor
     *
     * @param editor
     */
    public static void releaseEditor(Editor editor) {
        if (editor == null)
            return;
        EditorFactoryImpl.getInstance().releaseEditor(editor);
    }


    public static Editor[] getAllEditors() {
        return EditorFactory.getInstance().getAllEditors();
    }

    public static Editor[] getEditor(Document var1, Project var2) {
        if (var2 == null)
            return EditorFactory.getInstance().getEditors(var1);
        return EditorFactory.getInstance().getEditors(var1, var2);
    }

    private static Editor editor;

    /**
     * 获取当前打开的Editor的内容
     *
     * @return
     */
    public static Editor getCurrentEditor() {
        Editor[] editors = EditorFactoryImpl.getInstance().getAllEditors();
        Editor showEditor = null;
        for (Editor e : editors) {
            boolean s = e.getComponent().isShowing();//显示
            if (s) {
                try {
                    EditorEx ee = (EditorEx) e;
                    VirtualFile vf = ee.getVirtualFile();
                    String url = vf == null ? null : vf.getUrl();//文件路径
                    if (url != null && url.length() > 0) {
                        showEditor = e;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
//        showErrorHint(showEditor);
        return showEditor;
    }

    /**
     * 获取Editor
     *
     * @param anActionEvent
     * @return
     */
    public static Editor getEditor(AnActionEvent anActionEvent) {
        //获取编辑器全部内容
        Editor editor = anActionEvent == null ? null : anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        return editor;
    }

//    /**
//     * 获取当前打开的Editor的内容
//     *
//     * @return
//     */
//    public static Editor getCurrentEditor() {
//        //获取编辑器全部内容
//        DataContext context = DataManager.getInstance().getDataContext();
//        Editor editor = CommonDataKeys.EDITOR.getData(context);
//        Project project = ProjectManager.getInstance().getDefaultProject();
//        return editor;
//    }

    /**
     * 获取当前打开的Editor的内容
     *
     * @return
     */
    public static Document getDocument(Editor editor) {
        //获取编辑器全部内容
        Document document = editor == null ? null : editor.getDocument();
        return document;
    }

    /**
     * 获取当前打开的Editor的内容
     *
     * @return
     */
    public static String getDocumentText(Editor editor) {
        //获取编辑器全部内容
        Document document = editor == null ? null : editor.getDocument();
        String from = document == null ? "请打开一个文本编辑窗口在试" : document.getText();//（使用词库，不上传代码）
        return from;
    }

    /**
     * 获取当前打开的Editor的内容
     *
     * @param anActionEvent
     * @return
     */
    public static String getDocumentText(AnActionEvent anActionEvent) {
        //获取编辑器全部内容
        Editor editor = anActionEvent == null ? null : anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        Document document = editor == null ? null : editor.getDocument();
        String from = document == null ? null : document.getText();
        return from;
    }

    /**
     * 编辑器选中的文本
     *
     * @param anAction
     * @param anActionEvent
     * @return
     */
    public static String getClipboard(AnAction anAction, AnActionEvent anActionEvent) {
        String actionText = anAction.getTemplatePresentation().getText();
        String selectedText = null;
        if (actionText != null && actionText.equals("Copy")) {
            selectedText = ClipboardUtil.getTextInClipboard();
        }
        return selectedText;
    }

    /**
     * 获取当前打开的Editor的文件类型
     *
     * @return
     */
    public static FileType getFileType(Editor editor) {
        EditorEx editorEx = editor == null ? null : (EditorEx) editor;
        if (editorEx != null) {
            VirtualFile virtualFile = editorEx.getVirtualFile();
            FileType fileType = virtualFile == null ? null : virtualFile.getFileType();
            return fileType;
        }
        return null;
    }

    /**
     * 获取虚拟文件
     *
     * @param anActionEvent
     * @return
     */
    public static FileType getFileType(AnActionEvent anActionEvent) {
        //获取VirtualFile的方式
        //VirtualFile vf = AnActionEvent.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
        //VirtualFile vf = DataContext.getData(CommonDataKeys.VIRTUAL_FILE);
        //作用
        //System.out.println("getCharset:" + vf.getCharset());//编码
        //System.out.println("getDetectedLineSeparator:" + vf.getDetectedLineSeparator());
        //System.out.println("getExtension:" + vf.getExtension());//文件拓展名
        //System.out.println("getFileType:" + vf.getFileType().getName());//文件名
        //System.out.println(vf.getFileType().getDescription());//文件说明
        //System.out.println("getNameSequence:" + vf.getNameSequence());
        //System.out.println("getNameSequence:" + vf.getNameWithoutExtension());
        //System.out.println("getUrl:" + vf.getUrl());//文件路径
        //System.out.println("getPersentableName:" + vf.getPresentableName());
        VirtualFile vf = anActionEvent.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
        FileType fileType = vf == null ? null : vf.getFileType();
        return fileType;
    }


//    /**
//     * 获取当前打开的Editor的文件类型
//     *
//     * @return
//     */
//    public static FileType getFileType() {
//        DataContext context = DataManager.getInstance().getDataContext();
//        VirtualFile editor = CommonDataKeys.VIRTUAL_FILE.getData(context);
//        FileType fileType = editor.getFileType();
//        return fileType;
//    }

    /**
     * 获取虚拟文件
     *
     * @param anActionEvent
     * @return
     */
    public static VirtualFile getVirtualFile(AnActionEvent anActionEvent) {
        //获取VirtualFile的方式
        //VirtualFile vf = AnActionEvent.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
        //VirtualFile vf = DataContext.getData(CommonDataKeys.VIRTUAL_FILE);
        //作用
        //System.out.println("getCharset:" + vf.getCharset());//编码
        //System.out.println("getDetectedLineSeparator:" + vf.getDetectedLineSeparator());
        //System.out.println("getExtension:" + vf.getExtension());//文件拓展名
        //System.out.println("getFileType:" + vf.getFileType().getName());//文件名
        //System.out.println(vf.getFileType().getDescription());//文件说明
        //System.out.println("getNameSequence:" + vf.getNameSequence());
        //System.out.println("getNameSequence:" + vf.getNameWithoutExtension());
        //System.out.println("getUrl:" + vf.getUrl());//文件路径
        //System.out.println("getPersentableName:" + vf.getPresentableName());
        VirtualFile vf = anActionEvent.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
        return vf;
    }

    /**
     * 获取房钱打开的Editor的文件类型
     *
     * @return
     */
    public static boolean isImageFileType(VirtualFile vf) {
        FileType fileType = vf.getFileType();
        if (fileType.getName().equals("Images")) {
            return true;
        }
        return false;
    }

    /**
     * 获取房钱打开的Editor的文件类型
     *
     * @return
     */
    public static boolean isImageFileType(FileType fileType) {
        if (fileType.getName().equals("Images")) {
            return true;
        }
        return false;
    }

    public static Project getProject() {
        DataContext context = DataManager.getInstance().getDataContext();
        Project project = CommonDataKeys.PROJECT.getData(context);
        return project;
    }

    public static Project getProject(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getRequiredData(CommonDataKeys.PROJECT);
        return project;
    }

    /**
     * 获取当前打开的Editor的内容的文件类型
     *
     * @return
     */
    public static String getEditorDocumentType() {
        DataContext context = DataManager.getInstance().getDataContext();
        Editor editor = CommonDataKeys.EDITOR.getData(context);
        Document document = editor.getDocument();

        Map<String, String> m = new LinkedHashMap<>();

        //Project
        Project p = editor.getProject();
        VirtualFile bdvf = p.getBaseDir();
        VirtualFile pfvf = p.getProjectFile();
        VirtualFile wfvf = p.getWorkspaceFile();
//
//        String bp = p.getBasePath();
//        String lh = p.getLocationHash();
//        String name = p.getName();
//        String pu = p.getPresentableUrl();
//        String pfp = p.getProjectFilePath();
//
        //获取编辑器全部内容
        //return "java";

        VirtualFile vf = wfvf;
        m.put("dls", vf.getDetectedLineSeparator());
        m.put("cp", vf.getCanonicalPath());
        m.put("e", vf.getExtension());
        m.put("a", vf.getName());
        m.put("awe", vf.getNameWithoutExtension());
        m.put("p", vf.getPath());
        m.put("pn", vf.getPresentableName());
        m.put("pu", vf.getPresentableUrl());
        m.put("u", vf.getUrl());
        m.put("uds", vf.getUserDataString());

        StringBuffer sb = new StringBuffer();
        for (String k : m.keySet()) {
            String v = m.get(k);
            sb.append(k).append(":").append(v).append("\r\n");
        }

        return sb.toString();
    }

    /**
     * 数据持久化
     * 因为所有插件共享相同的命名空间，强烈推荐使用前缀（如你的插件ID）。
     */
    public static PropertiesComponent getProperties() {
        //保存应用级数据
        //PropertiesComponent.getInstance().setValue("key","value");
        //保持项目级数据
        //PropertiesComponent.getInstance(Project).setValue("key","value");
        return PropertiesComponent.getInstance();
    }

    /**
     * 获取图片
     *
     * @param path url、path
     * @return
     */
    public static Icon getIcon(String path) {
        Icon icon = null;
        try {
            URL u = new URL(path);
            icon = IconLoader.findIcon(u);
        } catch (MalformedURLException e) {
            //e.printStackTrace();
            icon = IconLoader.findIcon(path);
        }
        return icon;
    }

    /**
     * Swing控件更新UI线程
     *
     * @param runnable
     */
    public static void runSwingThread(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }

    /**
     * JFX控件更新UI线程
     *
     * @param runnable
     */
    public static void runJFXThread(Runnable runnable) {
        Platform.runLater(runnable);
    }

}
