package atomdu.tools.core.bean;

import java.util.List;

/**
 * 导航
 * Created by atomdu on 2017/12/15.
 */
public class Navigation {
    private String name;
    private List<NavigationDiv> navigationDivList;

    public Navigation() {
    }

    public Navigation(String name, List<NavigationDiv> navigationDivList) {
        this.name = name;
        this.navigationDivList = navigationDivList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NavigationDiv> getNavigationDivList() {
        return navigationDivList;
    }

    public void setNavigationDivList(List<NavigationDiv> navigationDivList) {
        this.navigationDivList = navigationDivList;
    }

    @Override
    public String toString() {
        return "Navigation{" +
                "name='" + name + '\'' +
                ", navigationDivList=" + navigationDivList +
                '}';
    }
}
