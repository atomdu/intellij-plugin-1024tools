package atomdu.tools.core;

import atomdu.tools.core.bean.PanelBean;
import atomdu.tools.core.bean.RootBean;
import atomdu.tools.core.config.ConfigManager;
import atomdu.tools.core.swing.NavigationPanel;
import atomdu.tools.core.swing.RootPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by atomdu on 2017/12/7.
 */
public class PanelManager {
    private static PanelManager instance;

    private PanelManager() {
    }

    public static PanelManager getInstance() {
        if (instance == null)
            instance = new PanelManager();
        return instance;
    }

    /**
     * 创建错误提示界面
     *
     * @param rootPanel
     */
    public void createErrorComponent(final RootPanel rootPanel) {
        rootPanel.removeAll();
        rootPanel.setLayout(new BorderLayout());
        JButton jLabel = new JButton("创建界面失败,请检查网络链接,然后在点击刷新");
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        rootPanel.updateView();
                    }
                });
            }
        });
        rootPanel.add(jLabel, BorderLayout.CENTER);
        rootPanel.updateUI();
    }

    /**
     * 创建根界面
     *
     * @param rootPanel
     * @param rootBean
     * @return
     */
    public void createRootComponent(RootPanel rootPanel, RootBean rootBean) {
        if (rootPanel == null)
            return;
        if (rootBean == null || rootBean.getPanels() == null || rootBean.getPanels().size() == 0) {
            createErrorComponent(rootPanel);
        } else {
            rootPanel.removeAll();
            rootPanel.setLayout(new BorderLayout());

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
            for (PanelBean p : rootBean.getPanels()) {
                createComponent(p, tabbedPane);//递归创建界面
            }
            rootPanel.add(tabbedPane, BorderLayout.CENTER);
            rootPanel.updateUI();
        }
    }

    /**
     * 递归创建界面
     *
     * @param panelBean
     * @param jTabbedPane
     * @return
     */
    public void createComponent(PanelBean panelBean, JTabbedPane jTabbedPane) {
        if (panelBean.getPanels() != null && panelBean.getPanels().size() > 0) {
            JTabbedPane childTabbedPane = new JTabbedPane();
            for (PanelBean p : panelBean.getPanels()) {
                createComponent(p, childTabbedPane);//递归调用
            }
            if (childTabbedPane.getComponentCount() > 0) {
                jTabbedPane.add(panelBean.getName(), childTabbedPane);
            }
        } else if (panelBean.getUrl() != null && panelBean.getUrl().length() > 0) {
            NavigationPanel urlComponent = createUrlComponent(panelBean);
            if (urlComponent != null) {
                jTabbedPane.add(panelBean.getName(), urlComponent);
            }
        } else if (panelBean.getClazz() != null && panelBean.getClazz().length() > 0) {
            JComponent jarComponent = createJarComponent(panelBean);
            if (jarComponent != null) {
                jTabbedPane.add(panelBean.getName(), jarComponent);
            }
        }
    }

    /**
     * 获取通过url生成的面板
     *
     * @param panelBean
     * @return
     */
    public NavigationPanel createUrlComponent(PanelBean panelBean) {
        if (panelBean == null)
            return null;
        try {
            String urlstr = ConfigManager.getURL(panelBean.getUrl());
            URL url = new URL(urlstr);
            NavigationPanel navigationPanel = new NavigationPanel(urlstr);
            navigationPanel.setDisplayName(panelBean.getName());
            return navigationPanel;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取通过Jar生成的面板
     *
     * @param panelBean
     * @return
     */
    public JComponent createJarComponent(PanelBean panelBean) {
        if (panelBean == null)
            return null;
        String name = panelBean.getName();
        String clazzName = panelBean.getClazz();
        String jar = panelBean.getJar();
        JComponent jComponent = null;
        ClassLoader classLoader = getClass().getClassLoader();
        if (jar != null && jar.length() > 0) {
            String[] jars = jar.split(";");
            URL[] urls = new URL[jars.length];
            for (int i = 0; i < jars.length; i++) {
                try {
                    urls[i] = new URL(jars[i]);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            classLoader = new URLClassLoader(urls, classLoader);
            try {
                Class clazz = classLoader.loadClass(clazzName);
                jComponent = (JComponent) clazz.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class clazz = classLoader.loadClass(clazzName);
                jComponent = (JComponent) clazz.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jComponent;
    }

//    /**
//     * 获取跟面板
//     *
//     * @param toolsBean
//     * @return
//     */
//    @Deprecated
//    public JTabbedPane getJTabbedPane(RootBean toolsBean) {
//        List<PanelBean> panels = toolsBean.getPanels();
//        //List<JComponent> jComponents = new ArrayList<>();
//        JTabbedPane jTabbedPane = new JTabbedPane();
//        jTabbedPane.setTabPlacement(JTabbedPane.RIGHT);
//
//        for (PanelBean p : panels) {
//            String name = p.getName();
//            String clazzName = p.getClazz();
//            String jar = p.getJar();
//
//            ClassLoader classLoader = getClass().getClassLoader();
//            if (jar != null && jar.length() > 0) {
//                String[] jars = jar.split(";");
//                URL[] urls = new URL[jars.length];
//                for (int i = 0; i < jars.length; i++) {
//                    try {
//                        urls[i] = new URL(jars[i]);
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//                }
//                classLoader = new URLClassLoader(urls, classLoader);
//                try {
//                    Class clazz = classLoader.loadClass(clazzName);
//                    JComponent jComponent = (JComponent) clazz.newInstance();
//                    jTabbedPane.add(name, jComponent);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                try {
//                    Class clazz = classLoader.loadClass(clazzName);
//                    JComponent jComponent = (JComponent) clazz.newInstance();
//                    jTabbedPane.add(name, jComponent);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return jTabbedPane;
//    }
}
