package atomdu.intellij.plugin.utils;

import atomdu.intellij.plugin.dispatch.Executor;
import atomdu.tools.core.config.ConfigManager;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

/**
 * 通知
 * Created by huazhou.whz on 2016/10/7.
 */
public class Notify implements atomdu.tools.core.notify.Notify {
    private static final NotificationGroup Builder = NotificationGroup.balloonGroup(ConfigManager.getAppName());

    @Override
    public void alert(String line) {
        Notification notification =
                Builder.createNotification(ConfigManager.getAppName(), line, NotificationType.INFORMATION, null);

        notify(notification);
    }

    @Override
    public void warn(String warning) {
        Notification notification =
                Builder.createNotification(ConfigManager.getAppName(), warning, NotificationType.WARNING, null);

        notify(notification);
    }

    @Override
    public void error(String error) {
        Notification notification =
                Builder.createNotification(ConfigManager.getAppName(), error, NotificationType.ERROR, null);

        notify(notification);
    }

    private static void notify(final Notification notification) {
        Executor.post(new Runnable() {
            @Override
            public void run() {
                Notifications.Bus.notify(notification);
            }
        });
    }
}
