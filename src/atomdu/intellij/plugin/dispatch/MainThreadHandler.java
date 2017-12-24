package atomdu.intellij.plugin.dispatch;

import org.jetbrains.annotations.NotNull;

/**
 * 线程处理器
 * Created by huazhou.whz on 2016/10/8.
 */
public abstract class MainThreadHandler {
    //具体的处理消息写在这里
    protected abstract void handleMessage(@NotNull Message msg);

    //发送空消息
    public void sendEmptyMessage(int what){
        final Message msg = new Message(what);
        sendMessage(msg);
    }

    public void sendMessage(int what,Object obj){
        sendMessage(new Message(what,obj));
    }

    public void sendMessage(@NotNull final Message msg){
        post(new Runnable() {
            @Override
            public void run() {
                handleMessage(msg);
            }
        });
    }


    /**
     * 在Runable中调用sendMessage()方法
     * @param runnable
     */
    public void post(Runnable runnable){
        Executor.post(runnable);
    }

    /**
     * execute in pooled thread
     * 合并线程执行
     */
    public static void execute(@NotNull final Runnable runnable){
        Executor.execute(runnable);
    }
}
