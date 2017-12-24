package atomdu.tool.tanslate.bean;

import atomdu.tool.tanslate.dao.LocalDOM;

/**
 * Created by atomdu on 2017/11/29.
 */
public class Data {
    private String str = "";
    private boolean tag;

    public Data() {
    }

    public Data(String str, boolean tag) {
        this.str = str;
        this.tag = tag;
    }

    public Data(char c, boolean tag) {
        this.str = "" + c;
        this.tag = tag;
    }

    public String getTo() {
        if (str == null || str.length() == 0) {
            return "";
        } else {
            String s = LocalDOM.getInstance().getTo(str);
            if (s == null || s.length() == 0)
                return "";
            return s;
        }
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public void addChar(char c) {
        this.str += c;
    }

    @Override
    public String toString() {
        return "Data{" +
                "str='" + str + '\'' +
                ", to='" + getTo() + '\'' +
                ", tag=" + tag +
                '}';
    }
}
