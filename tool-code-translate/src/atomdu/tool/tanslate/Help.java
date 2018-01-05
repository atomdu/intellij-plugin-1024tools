package atomdu.tool.tanslate;

import atomdu.tools.core.utils.SystemUtils;

/**
 * Created by atomdu on 2018/1/4.
 */
public class Help {
    public static final String HELP_NAME_ZH = "使用帮助与付费声明";
    public static final String HELP_NAME_EN = "Help and Pay";
    public static final String HELP_URL_ZH = "http://www.atomdu.com/website/zh/code_translate.html";
    public static final String HELP_URL_EN = "http://www.atomdu.com/website/en/code_translate.html";

    /**
     * 获取帮助名字
     *
     * @return
     */
    public static String getHelpName() {
        if (SystemUtils.isLocaleSystemLanguageZH())
            return HELP_NAME_ZH;
        else
            return HELP_NAME_EN;
    }

    /**
     * 获取帮助页面
     *
     * @return
     */
    public static String getHelpUrl() {
        if (SystemUtils.isLocaleSystemLanguageZH())
            return HELP_URL_ZH;
        else
            return HELP_URL_EN;
    }


}
