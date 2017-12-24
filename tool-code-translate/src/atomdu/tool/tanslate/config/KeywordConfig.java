package atomdu.tool.tanslate.config;

/**
 * Created by Administrator on 2017/6/23.
 */
public class KeywordConfig {
    public static final String TRANS_KEYWORD = "翻译关键字";
    public static final String NO_TRANS_KEYWORD = "不翻译关键字";

    public static final String[] TRANS = new String[]{TRANS_KEYWORD, NO_TRANS_KEYWORD};
    public static final int[] TRANS_TYPES = new int[]{1, 2};

    public final static String[] JAVA = {
            //1.访问修饰符的关键字
            "public", "private", "protected",
            //2.定义类、接口、抽象类和实现接口、继承类的关键字、实例化对象（共6个）
            "class", "interface", "abstract", "implements", "extends", "new",
            //3.包的关键字（共2个）
            "package", "import",
            //4.数据类型的关键字（共12个）
            "byte", "char", "boolean", "short", "int", "float", "long", "double", "void", "null", "true", "false",
            //5.条件循环（流程控制）（共12个）
            "if", "else", "while", "for", "switch", "case", "default", "do", "break", "continue", "return", "instanceof",
            //6.修饰方法、类、属性和变量（共9个）
            "static", "final", "super", "this", "native", "strictfp", "synchronized", "transient", "volatile",
            //7).错误处理（共5个）
            "catch", "try", "finally", "throw", "throws"
    };

    public final static String[] PHP = {

    };
    public final static String[] CPP = {
            "auto",// 声明自动变量
            "short",// 声明短整型变量或函数
            "int",// 声明整型变量或函数
            "long",// 声明长整型变量或函数
            "float",// 声明浮点型变量或函数
            "double",// 声明双精度变量或函数
            "char",// 声明字符型变量或函数
            "struct",// 声明结构体变量或函数
            "union",// 声明共用数据类型
            "enum",// 声明枚举类型
            "typedef",// 用以给数据类型取别名
            "const",// 声明只读变量
            "unsigned",// 声明无符号类型变量或函数
            "signed",// 声明有符号类型变量或函数
            "extern",// 声明变量是在其他文件正声明
            "register",// 声明寄存器变量
            "static",// 声明静态变量
            "volatile",// 说明变量在程序执行中可被隐含地改变
            "void",// 声明函数无返回值或无参数，声明无类型指针
            "if",// 条件语句
            "else",// 条件语句否定分支（与 if 连用）
            "switch",// 用于开关语句
            "case",// 开关语句分支
            "for",// 一种循环语句
            "do",// 循环语句的循环体
            "while",// 循环语句的循环条件
            "goto",// 无条件跳转语句
            "continue",// 结束当前循环，开始下一轮循环
            "break",// 跳出当前循环
            "default",// 开关语句中的“其他”分支
            "sizeof",// 计算数据类型长度
            "return",// 子程序返回语句（可以带参数，也可不带参数）循环条件
    };
}
