package atomdu.tool.tanslate.processor;

import atomdu.tool.tanslate.bean.Data;
import atomdu.tool.tanslate.config.ToStyle;

import java.util.List;

/**
 * Created by atomdu on 2017/11/30.
 */
public class ProcessorChain {
    SymbolProcessor symbolProcessor;//符号处理
    KeywordProcessor keywordProcessor;//关键字处理
    WordProcessor wordProcessor;//词法处理

    boolean isOutKeyword = true;//是否输出关键字
    boolean isOutTranslateKeyword = false;//是否输出翻译后的关键字
    boolean isOutResource = true;//是否输出原文

    private String toStyle = ToStyle.LINE;

    public ProcessorChain() {
        symbolProcessor = new SymbolProcessor();
        wordProcessor = new WordProcessor();
        keywordProcessor = new KeywordProcessor();
    }

    public synchronized String onProcessor(String str) {//TODO 当前格式写死了，需要写活，明天数据下载到本地，测试速度
        StringBuffer sb = new StringBuffer();
        List<Data> list = symbolProcessor.onProcessor(str);
        for (Data d1 : list) {
            if (d1.isTag()) { // 是符号
                String s = d1.getStr();
                sb.append(s);
            } else {//不是符号
                keywordProcessor.onProcessor(d1);//关键字判断,内部处理了tag标记
                if (d1.isTag()) {//是关键字
                    if (isOutKeyword) {
                        sb.append(d1.getStr());
                    }
                    if (isOutTranslateKeyword) {
                        sb.append(d1.getTo());
                    }
                } else {
                    //不是关键字,分词
                    List<Data> li = wordProcessor.onProcessor(d1.getStr());
                    switch (toStyle) {
                        case ToStyle.ADD_1:
                            for (Data d2 : li) {
                                if (isOutResource) {
                                    sb.append(d2.getStr());
                                }
                            }
                            for (Data d2 : li) {
                                sb.append(d2.getTo());
                            }
                            break;
                        case ToStyle.ADD_2:
                            for (Data d2 : li) {
                                if (isOutResource) {
                                    sb.append(d2.getStr());
                                }
                                sb.append(d2.getTo());
                            }
                            break;
                        case ToStyle.LINE:
                            for (Data d2 : li) {
                                if (d2.getTo() != null && d2.getTo().length() > 0)
                                    sb.append(d2.getTo());
                                else
                                    sb.append(d2.getStr());
                            }
                            break;
                    }
                }
            }
        }

        return sb.toString();
    }

    public void setToStyle(String toStyle) {
        this.toStyle = toStyle;
    }
}
