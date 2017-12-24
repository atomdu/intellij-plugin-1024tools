package atomdu.tools.core.http.parse;

import atomdu.tools.core.bean.Navigation;
import atomdu.tools.core.http.ParseException;
import atomdu.tools.core.http.Parser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Json格式 NavigationItem(导航业务bean)解析
 * Created by atomdu on 2017/12/12.
 */
public class JsonNavigationParser implements Parser<Navigation> {
    @Override
    public Navigation parse(String str) throws ParseException {
        return new Gson().fromJson(str, new TypeToken<Navigation>() {
        }.getType());
    }

    @Override
    public Navigation parse(byte[] bytes) throws ParseException {
        return null;
    }
}
