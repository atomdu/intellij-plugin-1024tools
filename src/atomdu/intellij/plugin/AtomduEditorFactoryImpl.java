package atomdu.intellij.plugin;

import atomdu.intellij.plugin.utils.IdeaUtils;
import com.intellij.injected.editor.DocumentWindow;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory;
import com.intellij.openapi.editor.impl.EditorFactoryImpl;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by atomdu on 2018/1/11.
 */
public class AtomduEditorFactoryImpl extends EditorFactoryImpl {
    public AtomduEditorFactoryImpl(EditorActionManager editorActionManager) {
        super(editorActionManager);
    }

    @Override
    public Editor createEditor(@NotNull Document document) {
        return this.createEditorEx(document, false, (Project) null);
    }
    @Override
    public Editor createViewer(@NotNull Document document) {
        return this.createEditorEx(document, true, (Project) null);
    }
    @Override
    public Editor createEditor(@NotNull Document document, Project project) {
        return this.createEditorEx(document, false, project);
    }
    @Override
    public Editor createViewer(@NotNull Document document, Project project) {
        return this.createEditorEx(document, true, project);
    }
    @Override
    public Editor createEditor(@NotNull Document document, Project project, @NotNull FileType fileType, boolean isViewer) {
        Editor editor = this.createEditorEx(document, isViewer, project);
        ((EditorEx) editor).setHighlighter(EditorHighlighterFactory.getInstance().createEditorHighlighter(project, fileType));
        return editor;
    }
    @Override
    public Editor createEditor(@NotNull Document document, Project project, @NotNull VirtualFile file, boolean isViewer) {
        Editor editor = this.createEditorEx(document, isViewer, project);
        ((EditorEx) editor).setHighlighter(EditorHighlighterFactory.getInstance().createEditorHighlighter(project, file));
        return editor;
    }

    private Editor createEditorEx(@NotNull Document document, boolean isViewer, Project project) {
        Document hostDocument = document instanceof DocumentWindow ? ((DocumentWindow) document).getDelegate() : document;
        EditorImpl editor = null;
        Class clazz = getClass();
        try {
            Method method =  clazz.getDeclaredMethod("createEditor",Document.class,boolean.class,Project.class);
            method.setAccessible(true);
            editor = (EditorImpl) method.invoke(this,hostDocument,isViewer,project);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return editor;
    }
}
