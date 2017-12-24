package atomdu.tools.core;

import atomdu.tools.core.bean.JarBean;
import atomdu.tools.core.bean.PanelBean;
import atomdu.tools.core.bean.RootBean;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atomdu on 2017/12/15.
 */
public class RootBeanTest {
    @Test
    public void toJson() {
        List<JarBean> jars = new ArrayList<>();
        jars.add(new JarBean("hhh", "aaa"));


        List<PanelBean> listBeanPanels = new ArrayList<>();
        listBeanPanels.add(new PanelBean("jarPanel", "clazz", "url:jar"));
        listBeanPanels.add(new PanelBean("urlPanel", "url"));

        List<PanelBean> panels = new ArrayList<>();
        PanelBean jarBean = new PanelBean("jarPanel", "clazz", "url:jar");
        PanelBean urlBean = new PanelBean("urlPanel", "url");
        PanelBean listBean = new PanelBean("listPanel", listBeanPanels);

        panels.add(jarBean);
        panels.add(urlBean);
        panels.add(listBean);

        RootBean rootBean = new RootBean();
        rootBean.setVersion("1");
        rootBean.setJars(jars);
        rootBean.setPanels(panels);

        System.out.println(new Gson().toJson(rootBean));

    }
}
