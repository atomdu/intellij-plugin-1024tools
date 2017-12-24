package atomdu.tool.tanslate.processor;

import atomdu.tool.tanslate.bean.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atomdu on 2017/11/29.
 */
public class WordProcessor {
    //空格，小写字母，大写字母，特殊字符
    private static final int XIAO_XIE = 1, DA_XIE = 2, SHU_ZI = 3, QI_TA = 5, NULL = -1;


    public WordProcessor() {
    }

    public static int getCharType(char c) {
        if (c >= 'a' && c < 'z') {
            return XIAO_XIE;
        } else if (c >= 'A' && c <= 'Z') {
            return DA_XIE;
        } else if (c >= '0' && c <= '9') {
            return SHU_ZI;
        } else {
            return QI_TA;
        }
    }

    public List<Data> onProcessor(String str) {
        if (str == null)
            return null;
        List<Data> list = new ArrayList<>();

        Data temp = null;
        char[] chars = str.toCharArray();
        int beforeType = NULL;//前一个类型
        int currentType = chars.length == 0 ? NULL : getCharType(chars[0]);//当前类型
        for (int i = 0; i < chars.length; i++) {
            int afterType = i == chars.length - 1 ? NULL : getCharType(chars[i + 1]);//后一个类型
            char currentChar = chars[i];

            //第一个
            if (temp == null) {
                temp = new Data();
                temp.setTag((currentType == XIAO_XIE || currentType == DA_XIE) ? true : false);
                list.add(temp);
            }

            if (beforeType == NULL) {
                temp.addChar(currentChar);
            } else {
                switch (currentType) {
                    case XIAO_XIE:
                        if (beforeType == DA_XIE || beforeType == XIAO_XIE) {
                            temp.addChar(currentChar);
                        } else {
                            temp = new Data(currentChar, (currentType == XIAO_XIE || currentType == DA_XIE) ? true : false);
                            list.add(temp);
                        }
                        break;
                    case DA_XIE:
                        if (beforeType == XIAO_XIE || beforeType == SHU_ZI || beforeType == QI_TA || afterType == XIAO_XIE) {//前一个是小写、数字、其他，之后是小写
                            temp = new Data(currentChar, (currentType == XIAO_XIE || currentType == DA_XIE) ? true : false);
                            list.add(temp);
                        } else {
                            temp.addChar(currentChar);
                        }
                        break;
                    case SHU_ZI:
                        if (beforeType != SHU_ZI) {
                            temp = new Data(currentChar, (currentType == XIAO_XIE || currentType == DA_XIE) ? true : false);
                            list.add(temp);
                        } else {
                            temp.addChar(currentChar);
                        }
                        break;
                    case QI_TA:
                        if (beforeType != QI_TA) {
                            temp = new Data(currentChar, (currentType == XIAO_XIE || currentType == DA_XIE) ? true : false);
                            list.add(temp);
                        } else {
                            temp.addChar(currentChar);
                        }
                        break;
                }
            }

            beforeType = currentType;
            currentType = afterType;
        }
        return list;
    }

    public void translate(List<Data> li) {

    }
}