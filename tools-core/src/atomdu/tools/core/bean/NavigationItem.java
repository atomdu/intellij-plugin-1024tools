package atomdu.tools.core.bean;

/**
 * 导航条目
 * Created by atomdu on 2017/12/9.
 */
public class NavigationItem {
    private String name;
    private String url;
    private String fontColor;//字体颜色

    public NavigationItem() {
    }

    public NavigationItem(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public NavigationItem(String name, String url, String fontColor) {
        this.name = name;
        this.url = url;
        this.fontColor = fontColor;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NavigationItem{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", fontColor='" + fontColor + '\'' +
                '}';
    }
}
