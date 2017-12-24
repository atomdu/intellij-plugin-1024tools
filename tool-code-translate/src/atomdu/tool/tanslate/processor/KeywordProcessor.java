package atomdu.tool.tanslate.processor;

import atomdu.tool.tanslate.bean.Data;
import atomdu.tool.tanslate.config.KeywordConfig;

/**
 * 关键字处理器
 * Created by atomdu on 2017/11/30.
 */
public class KeywordProcessor {

    public Data onProcessor(Data data) {
        for (String s : KeywordConfig.JAVA) {
            if (s.equals(data.getStr())) {
                data.setTag(true);
            }
        }
        return data;
    }
}
