package atomdu.tools.core.swing;

import atomdu.tools.core.bean.NavigationItem;
import atomdu.tools.core.log.Log;
import atomdu.tools.core.utils.SystemUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 导航标签
 * Created by atomdu on 2017/12/10.
 */
public class NavigationLabel extends JLabel implements ActionListener, MouseListener {
    private NavigationItem indexBean;
    private Color defaultForeground;
    private Color foreground;

    public NavigationLabel(NavigationItem indexBean) {
        //设置文本内容并居中
        this(indexBean, SwingConstants.CENTER);
    }

    public NavigationLabel(NavigationItem indexBean, int i) {
        //设置文本内容并居中
        super(indexBean.getName(), i);
        this.indexBean = indexBean;
        //设置字体颜色
        if (indexBean.getFontColor() != null && indexBean.getFontColor().length() == 7) {
            try {
                foreground = Color.decode(indexBean.getFontColor());
                setForeground(foreground);
            } catch (NumberFormatException e) {
                Log.e(NavigationLabel.class, indexBean.getName() + ":颜色格式不正确：" + indexBean.getFontColor());
            }
        }

        addMouseListener(this);
        setMaximumSize(new Dimension(160, 30));
        setPreferredSize(new Dimension(-1, 30));
        setOpaque(true);
        defaultForeground = getForeground();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = indexBean.getUrl();
        SystemUtils.openDefaultBrowser(s);
    }

    @Override//鼠标点击
    public void mouseClicked(MouseEvent e) {

    }

    @Override//鼠标加压
    public void mousePressed(MouseEvent e) {

    }

    @Override//鼠标释放
    public void mouseReleased(MouseEvent e) {
        actionPerformed(null);
    }

    @Override//鼠标进入
    public void mouseEntered(MouseEvent e) {
        setForeground(Color.BLUE);
    }

    @Override//鼠标退出
    public void mouseExited(MouseEvent e) {
        mouseExitedForeground();
    }

    private void mouseExitedForeground() {
        if (foreground != null)
            setForeground(foreground);
        else
            setForeground(defaultForeground);
    }
}
