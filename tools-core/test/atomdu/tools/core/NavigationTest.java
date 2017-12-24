package atomdu.tools.core;

import atomdu.tools.core.bean.Navigation;
import atomdu.tools.core.bean.NavigationDiv;
import atomdu.tools.core.bean.NavigationItem;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atomdu on 2017/12/15.
 */
public class NavigationTest {

    @Test
    public void toJson() {
        List<NavigationItem> list = new ArrayList<>();
        list.add(new NavigationItem("aaaa", "bbbb"));
        list.add(new NavigationItem("cccc", "dddd"));

        List<NavigationDiv> divs = new ArrayList<>();
        divs.add(new NavigationDiv("div1", 0, 3, list));
        divs.add(new NavigationDiv("div2", 0, 4, list));

        Navigation navigation = new Navigation();
        navigation.setName("测试");
        navigation.setNavigationDivList(divs);

        System.out.print(new Gson().toJson(navigation));
    }
}
