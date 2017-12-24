package atomdu.intellij.plugin.dispatch;

import com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.annotations.NotNull;

/**
 * 线程执行处理器
 * Created by huazhou.whz on 2016/10/7.
 */
public class Executor {
    /**
     * execute in AWT event dispatching thread
     * 在事件派发线程执行
     * 将控制权从后台线程移交到事件处理线程
     */
    public static void post(@NotNull final Runnable runnable){
        ApplicationManager.getApplication().invokeLater(runnable);
    }

    /**
     * execute in pooled thread
     * 合并线程执行
     */
    public static void execute(@NotNull final Runnable runnable){
        ApplicationManager.getApplication().executeOnPooledThread(runnable);
    }
}
