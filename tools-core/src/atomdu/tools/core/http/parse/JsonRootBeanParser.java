package atomdu.tools.core.http.parse;

import atomdu.tools.core.bean.RootBean;
import atomdu.tools.core.http.ParseException;
import atomdu.tools.core.http.Parser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Json格式 RootBean解析
 * Created by atomdu on 2017/12/12.
 */
public class JsonRootBeanParser implements Parser<RootBean> {
    @Override
    public RootBean parse(String str) throws ParseException {
        return new Gson().fromJson(str, new TypeToken<RootBean>() {
        }.getType());
    }

    @Override
    public RootBean parse(byte[] bytes) throws ParseException {
        return null;
    }
}
