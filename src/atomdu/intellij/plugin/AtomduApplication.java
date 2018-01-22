package atomdu.intellij.plugin;

import atomdu.tools.core.config.Config;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ex.ApplicationManagerEx;
import com.intellij.openapi.application.impl.ApplicationImpl;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.components.impl.ComponentManagerImpl;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.impl.EditorFactoryImpl;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileEditor.impl.EditorHistoryManager;
import com.intellij.psi.stubs.StubIndex;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 需要再xml中注册
 * Created by dzw on 17-6-8.
 */
public class AtomduApplication implements ApplicationComponent {//, AnActionListener

    @Override
    public void initComponent() {
        EditorImpl editor = null;
        StubIndex stubIndex = null;
        ApplicationManager applicationManager = null;
        ApplicationManagerEx applicationManagerEx = null;
        EditorFactoryImpl editorFactory = null;
        ComponentManagerImpl c = null;

    }

    @Override
    public void disposeComponent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ApplicationImpl applicationImpl = (ApplicationImpl) ApplicationManager.getApplication();
                Class clazz = applicationImpl.getClass();
                try {
                    Field[] fields = clazz.getFields();
                    Field[] DeclaredFields = clazz.getDeclaredFields();
                    Field field = clazz.getDeclaredField("myNameToComponent");
                    field.setAccessible(true);
                    Map<String, BaseComponent> myNameToComponent = (Map<String, BaseComponent>) field.get(applicationImpl);
                    myNameToComponent.put("EditorFactoryImpl",new AtomduEditorFactoryImpl(EditorActionManager.getInstance()));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @NotNull
    @Override
    public String getComponentName() {
        return Config.APP_NAME_EN;
    }
}
