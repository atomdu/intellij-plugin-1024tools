package atomdu.tools.core.notify;

import atomdu.tools.core.utils.SystemUtils;

/**
 * 通知工厂
 * Created by atomdu on 2017/12/10.
 */
public class NotifyFactory {
    private static NotifyFactory instance;

    private NotifyFactory() {
    }

    public static NotifyFactory getInstance() {
        if (instance == null) {
            instance = new NotifyFactory();
        }
        return instance;
    }

    public Notify getNotify() {
        Notify notify = null;
        if (SystemUtils.getDevName().equals("intellij")) {
            try {
                Class clazz = getClass().getClassLoader().loadClass("atomdu.intellij.plugin.utils.Notify");
                notify = (Notify) clazz.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            //TODO Eclipse 的没有实现
        }

        return notify;
    }
}
