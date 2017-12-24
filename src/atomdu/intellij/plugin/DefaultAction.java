package atomdu.intellij.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by atomdu on 2017/9/26.
 */
public class DefaultAction extends AnAction {
    public DefaultAction() {
        super();
    }

    public DefaultAction(Icon icon) {
        super(icon);
    }

    public DefaultAction(@Nullable String text) {
        super(text);
    }

    public DefaultAction(@Nullable String text, @Nullable String description, @Nullable Icon icon) {
        super(text, description, icon);
        System.out.println(getClass().getSimpleName() + ": constructor");
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        System.out.println(getClass().getSimpleName() + ": actionPerformed");
    }
}
