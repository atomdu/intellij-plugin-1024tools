package atomdu.tool.tanslate.processor;

import atomdu.tool.tanslate.bean.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符处理
 * Created by atomdu on 2017/11/29.
 */
public class SymbolProcessor {
    //符号，非符号，空
    static final int SYMBOL = 1, NO_SYMBOL = 2, NULL = -1;

    //字符
    static final char[] SYMBOLS = {' ', '.', '<', ',', '>', '/', '?', ';', ':', '\'', '"', '[', ']', '{', '}', '\\', '|'
            , '`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+','\r','\n'};

    public SymbolProcessor() {
    }

    public static int getCharType(char c) {
        for (int i = 0; i < SYMBOLS.length; i++) {
            if (SYMBOLS[i] == c) {
                return SYMBOL;
            }
        }
        return NO_SYMBOL;
    }

    public List<Data> onProcessor(String str) {
        if (str == null || str.length() == 0)
            return null;
        List<Data> list = new ArrayList<>();

        Data temp = null;
        int beforeType = NULL;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char currentChar = chars[i];
            int currentType = getCharType(currentChar);

            //第一个
            if (temp == null) {
                temp = new Data();
                temp.setTag(currentType == SYMBOL ? true : false);
                list.add(temp);
            }

            if (beforeType == NULL) {//beforeType==null是起始
                temp.addChar(currentChar);
            } else if (currentType == beforeType) {//现在类型与前一个类型相等
                temp.addChar(currentChar);
            } else {//现在类型与前一个类型不相等
                temp = new Data(currentChar, currentType == SYMBOL ? true : false);
                list.add(temp);
            }
            beforeType = currentType;
        }

        return list;
    }
}
