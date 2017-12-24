package atomdu.tools.core.bean;

import java.util.List;

/**
 * 导航列表
 * Created by atomdu on 2017/12/15.
 */
public class NavigationDiv {
    private String name;
    private int type;//div类型
    private int cols;//列数
    private List<NavigationItem> navigations;
    //private int switchTime;//切换时间

    public NavigationDiv() {
    }

    /**
     * @param name        div名字
     * @param type        div类型
     * @param cols        div内容每行的列数
     * @param navigations 内容
     */
    public NavigationDiv(String name, int type, int cols, List<NavigationItem> navigations) {
        this.name = name;
        this.type = type;
        this.cols = cols;
        this.navigations = navigations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public List<NavigationItem> getNavigations() {
        return navigations;
    }

    public void setNavigations(List<NavigationItem> navigations) {
        this.navigations = navigations;
    }

    @Override
    public String toString() {
        return "NavigationDiv{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", cols=" + cols +
                ", navigations=" + navigations +
                '}';
    }
}
