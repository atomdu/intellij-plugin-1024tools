package atomdu.intellij.plugin;

import atomdu.tools.core.config.Config;
import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * 需要再xml中注册
 * Created by dzw on 17-6-8.
 */
public class Application implements ApplicationComponent {//, AnActionListener

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return Config.APP_NAME_EN;
    }
}
