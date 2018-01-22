package atomdu.tool.tanslate.processor;

import atomdu.tool.tanslate.bean.Data;
import atomdu.tool.tanslate.config.KeywordConfig;

/**
 * 关键字处理器
 * Created by atomdu on 2017/11/30.
 */
public class KeywordProcessor {
    String type;
    public KeywordProcessor(String type){
        this.type = type;
    }
    public Data onProcessor(Data data) {
        String[] keywords = {};
        if (type==null||type.equals("")){
            keywords= new String[]{};
        }else if ( type.equals("java")||type.equals("class") ){
            keywords=KeywordConfig.JAVA;
        }else if ( type.equals("php")){
            keywords=KeywordConfig.PHP;
        }else if ( type.equals("python")){
            keywords=KeywordConfig.PYTHON;
        }else if (type.equals("sql")){
            keywords=KeywordConfig.SQL;
        }else if(type.equals("c")){
            keywords=KeywordConfig.C;
        }else if (type.equals("html")){
            keywords=KeywordConfig.HTML;
        }

        for (String s : keywords) {
            if (s.equals(data.getStr())) {
                data.setTag(true);
            }
        }
        return data;
    }
}
