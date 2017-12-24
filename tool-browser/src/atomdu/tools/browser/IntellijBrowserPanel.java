//package atomdu.intellij.plugin.panel;
//
//import atomdu.intellij.plugin.utils.IdeaUtils;
//import BrowserPanel;
//import com.intellij.openapi.actionSystem.ActionManager;
//import com.intellij.openapi.actionSystem.AnAction;
//import com.intellij.openapi.actionSystem.AnActionEvent;
//import com.intellij.openapi.actionSystem.DataContext;
//import com.intellij.openapi.actionSystem.ex.AnActionListener;
//
///**
// * Created by atomdu on 2017/12/4.
// */
//public class Browser extends BrowserPanel implements AnActionListener {
//    public Browser() {
//        super();
//        ActionManager.getInstance().addAnActionListener(this);
//    }
//
//    @Override
//    public void beforeActionPerformed(AnAction anAction, DataContext dataContext, AnActionEvent anActionEvent) {
//        //当前显示
//        if (!isShowing())
//            return;
//        //当前不是图片文件
//        if (!IdeaUtils.isImageFileType(IdeaUtils.getVirtualFile(anActionEvent))) {
//            String str = IdeaUtils.getClipboard(anAction, anActionEvent);
//            if (str != null && str.length() > 0) {
//                if (getToolbar().isAutoInput()) {
//                    getToolbar().setSearchText(str);
//                }
//                if (getToolbar().isAutoSearch()) {
//                    onSearch(getToolbar().getCurrentUrl(), str);
//                }
//            }
//        }
//    }
//}
