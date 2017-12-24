package atomdu.intellij.plugin;

import atomdu.tools.core.config.Config;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * 需要再xml中注册
 * Created by dzw on 17-6-8.
 */
public class Application implements ApplicationComponent {//, AnActionListener

    @Override
    public void initComponent() {
        //PhpStorm会报错误，在Editor中无法打字
        //DefaultAction action = new DefaultAction("tools");
        //ActionManager.getInstance().addAnActionListener(this);
        //ActionManager.getInstance().registerAction("tools", action);
    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return Config.APP_NAME_EN;
    }

//    /**
//     * 系统AnAction监听
//     *
//     * @param anAction
//     * @param dataContext
//     * @param anActionEvent
//     */
//    @Override
//    public void beforeActionPerformed(AnAction anAction, DataContext dataContext, AnActionEvent anActionEvent) {
////        if (!vf.getFileType().getName().equals("Images")) {
////            String actionText = anAction.getTemplatePresentation().getText();
////            if (actionText != null && actionText.equals("Copy")) {
////                Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);//anActionEvent.getData(CommonDataKeys.EDITOR);
////
////                SelectionModel selectionModel = editor.getSelectionModel();
////                String selectedText = (selectionModel.getSelectedText() == null) ? null : selectionModel.getSelectedText().trim();
////                if (selectedText!=null&&selectedText.length()>0){
////                    Event event = new TextEvent(selectedText);
////                    event.setModelType(Event.MODE_LISTENER);
////                    event.execute();
////                }
////            }
////
////            try {
////                byte[] bytes = vf.contentsToByteArray();
////                String content = new String(bytes,"utf-8");
////                TextEvent event = new TextEvent();
////                event.setServiceTag("Translate");
////                event.putExtra("file_type",vf.getFileType());
////                event.setModelType(Event.MODE_LISTENER);
////                event.setText(content);
////                event.execute();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
//    }
}
