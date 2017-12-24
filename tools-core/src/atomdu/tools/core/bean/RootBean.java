package atomdu.tools.core.bean;

import java.io.Serializable;
import java.util.List;

public class RootBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String version;
    private List<JarBean> jars;
    private List<PanelBean> panels;

    public RootBean() {
        super();
    }

    public RootBean(String version, List<JarBean> jars, List<PanelBean> panels) {
        super();
        this.version = version;
        this.jars = jars;
        this.panels = panels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<JarBean> getJars() {
        return jars;
    }

    public void setJars(List<JarBean> jars) {
        this.jars = jars;
    }

    public List<PanelBean> getPanels() {
        return panels;
    }

    public void setPanels(List<PanelBean> panels) {
        this.panels = panels;
    }

    @Override
    public String toString() {
        return "RootBean [version=" + version + ", jars=" + jars + ", panels=" + panels + "]";
    }

}
