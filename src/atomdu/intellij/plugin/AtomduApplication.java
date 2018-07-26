package atomdu.intellij.plugin;

import atomdu.intellij.plugin.utils.IdeaUtils;
import atomdu.tools.core.config.Config;
import atomdu.tools.core.log.Log;
import com.intellij.ide.ui.laf.IdeaLaf;
import com.intellij.idea.IdeaLogger;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.IdeaLoggingEvent;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.reference.SoftReference;
import com.intellij.rt.ant.execution.IdeaAntLogger2;
import com.intellij.terminal.AppendableTerminalDataStream;
import com.jediterm.terminal.Terminal;
import com.jediterm.terminal.TerminalDataStream;
import com.jediterm.terminal.ui.TerminalAction;
import com.jediterm.terminal.ui.TerminalActionProvider;
import com.jediterm.terminal.ui.TerminalActionProviderBase;
import org.jdesktop.swingx.action.ActionManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.builders.logging.BuildLoggingManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 需要再xml中注册
 * Created by dzw on 17-6-8.
 */
public class AtomduApplication implements ApplicationComponent, FileEditorManagerListener, CaretListener {
    //插入字符监听
    private ArrayList<SoftReference<CaretListener>> caretListenerList = new ArrayList<SoftReference<CaretListener>>();
    //Editor打开、切换监听
    private ArrayList<SoftReference<FileEditorManagerListener>> fileEditorManagerListenerList = new ArrayList<>();

    private static AtomduApplication instance;

    public AtomduApplication() {
        instance = this;
    }

    public static AtomduApplication getInstance() {
        return instance;
    }

    @Override
    public void initComponent() {
        IdeaUtils.addFileEditorManagerListener(this);
    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return Config.APP_NAME_EN;
    }

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        Log.d("Editor open listener", "fileOpened");
        //source.addTopComponent(source.getSelectedEditor(file), new JLabel("Test"));
        //EditorImpl editor = (EditorImpl) source.getSelectedTextEditor();
//        IntellijBrowserPanel browser = new IntellijBrowserPanel();
//        browser.setPreferredSize(new Dimension(600,0));
//        editor.getComponent().add(browser, BorderLayout.EAST);
        //editor.addEditorMouseListener(this);

//        if (source instanceof FileEditorManagerImpl){
//            FileEditorManagerImpl m = (FileEditorManagerImpl) source;
//            IntellijBrowserPanel browser = new IntellijBrowserPanel();
//            browser.setPreferredSize(new Dimension(600,0));
//            m.getComponent().add(browser, BorderLayout.EAST);
//        }

        for (int i = fileEditorManagerListenerList.size() - 1; i >= 0; i--) {
            SoftReference<FileEditorManagerListener> softReference = fileEditorManagerListenerList.get(i);
            if (softReference == null) {
                fileEditorManagerListenerList.remove(i);
            } else {
                FileEditorManagerListener listener = softReference.get();
                if (listener == null) {
                    fileEditorManagerListenerList.remove(i);
                } else {
                    listener.fileOpened(source,file);
                }
            }
        }
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        Log.d("Editor open listener", "fileClosed");
        for (int i = fileEditorManagerListenerList.size() - 1; i >= 0; i--) {
            SoftReference<FileEditorManagerListener> softReference = fileEditorManagerListenerList.get(i);
            if (softReference == null) {
                fileEditorManagerListenerList.remove(i);
            } else {
                FileEditorManagerListener listener = softReference.get();
                if (listener == null) {
                    fileEditorManagerListenerList.remove(i);
                } else {
                    listener.fileClosed(source,file);
                }
            }
        }
    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        Log.d("Editor open listener", "selectionChanged");
        //切换Editor改变插入字符监听
        Editor editor = event.getManager().getSelectedTextEditor();
        CaretModel caretModel = editor == null ? null : editor.getCaretModel();
        if (caretModel != null) {
            //设置插入字符监听
            caretModel.addCaretListener(this);
        }
        //监听设置
        for (int i = fileEditorManagerListenerList.size() - 1; i >= 0; i--) {
            SoftReference<FileEditorManagerListener> softReference = fileEditorManagerListenerList.get(i);
            if (softReference == null) {
                fileEditorManagerListenerList.remove(i);
            } else {
                FileEditorManagerListener listener = softReference.get();
                if (listener == null) {
                    fileEditorManagerListenerList.remove(i);
                } else {
                    listener.selectionChanged(event);
                }
            }
        }
    }

    @Override
    public void caretPositionChanged(CaretEvent caretEvent) {
        for (int i = caretListenerList.size() - 1; i >= 0; i--) {
            SoftReference<CaretListener> softReference = caretListenerList.get(i);
            if (softReference == null) {
                caretListenerList.remove(i);
            } else {
                CaretListener caretListener = softReference.get();
                if (caretListener == null) {
                    caretListenerList.remove(i);
                } else {
                    caretListener.caretPositionChanged(caretEvent);
                }
            }
        }
    }

    @Override
    public void caretAdded(CaretEvent caretEvent) {
        for (int i = caretListenerList.size() - 1; i >= 0; i--) {
            SoftReference<CaretListener> softReference = caretListenerList.get(i);
            if (softReference == null) {
                caretListenerList.remove(i);
            } else {
                CaretListener caretListener = softReference.get();
                if (caretListener == null) {
                    caretListenerList.remove(i);
                } else {
                    caretListener.caretAdded(caretEvent);
                }
            }
        }
    }

    @Override
    public void caretRemoved(CaretEvent caretEvent) {
        for (int i = caretListenerList.size() - 1; i >= 0; i--) {
            SoftReference<CaretListener> softReference = caretListenerList.get(i);
            if (softReference == null) {
                caretListenerList.remove(i);
            } else {
                CaretListener caretListener = softReference.get();
                if (caretListener == null) {
                    caretListenerList.remove(i);
                } else {
                    caretListener.caretRemoved(caretEvent);
                }
            }
        }
    }

    public ArrayList<SoftReference<CaretListener>> getCaretListenerList() {
        return caretListenerList;
    }

    public void addCaretListenerList(CaretListener caretListener) {
        this.caretListenerList.add(new SoftReference<CaretListener>(caretListener));
    }

    public ArrayList<SoftReference<FileEditorManagerListener>> getFileEditorManagerListenerList() {
        return fileEditorManagerListenerList;
    }

    public void addFileEditorManagerListenerList(FileEditorManagerListener fileEditorManagerListener) {
        this.fileEditorManagerListenerList.add(new SoftReference<FileEditorManagerListener>(fileEditorManagerListener));
    }
}
