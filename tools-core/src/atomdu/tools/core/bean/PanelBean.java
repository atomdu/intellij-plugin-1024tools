package atomdu.tools.core.bean;

import java.io.Serializable;
import java.util.List;

public class PanelBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;//面板名称
    private String clazz;//通过clazz构建
    private String jar;//通过clazz构建需要提供的jar包
    private String url;//通过url构建
    private List<PanelBean> panels;//如果有子面板对象，这个面板是一个JTabbedPane

    public PanelBean() {
        super();
    }

    /**
     * 通过反射获取Panel对象
     *
     * @param name
     * @param clazz panel对象的class
     * @param jar
     */
    public PanelBean(String name, String clazz, String jar) {
        super();
        this.name = name;
        this.clazz = clazz;
        this.jar = jar;
    }

    /**
     * 通过url获取panel对象
     * <p>
     * 如果通过此方法构建，会通过NavigationPanel构建一个面板
     *
     * @param name
     * @param url
     */
    public PanelBean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    /**
     * 通过子类构建，当前类为JTabbedPanel
     *
     * @param name
     * @param list
     */
    public PanelBean(String name, List<PanelBean> list) {
        this.name = name;
        this.panels = list;
    }

    public List<PanelBean> getPanels() {
        return panels;
    }

    public void setPanels(List<PanelBean> list) {
        this.panels = list;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getJar() {
        return jar;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    @Override
    public String toString() {
        return "PanelBean [name=" + name + ", clazz=" + clazz + ", jar=" + jar + "]";
    }

}
