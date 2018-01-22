package atomdu.tool.tanslate.config;

import java.io.*;

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
            "__halt_compiler", "abstract", "and", "array", "as", "break", "callable", "case", "catch", "class", "clone", "const", "continue", "declare", "default", "die", "do", "echo", "else", "elseif", "empty", "enddeclare", "endfor", "endforeach", "endif", "endswitch", "endwhile", "eval", "exit", "extends", "final", "for", "foreach", "function", "global", "goto", "if", "implements", "include", "include_once", "instanceof", "insteadof", "interface", "isset", "list", "namespace", "new", "or", "print", "private", "protected", "public", "require", "require_once", "return", "static", "switch", "throw", "trait", "try", "unset", "use", "var", "while", "xor"
    };
    public static final String[] C = {};
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
    public static final String[] PYTHON = {"False", "class", "finally", "is", "return", "None", "continue", "for", "lambda", "try", "True", "def", "from", "nonlocal", "while", "and", "del", "global", "not", "with", "as", "elif", "if", "or", "yield", "assert", "else", "import", "pass", "break", "except", "in", "raise"};
    public static final String[] SQL = {"﻿abort","abs","absolute","access","action","ada","add","admin","after","aggregate","alias","all","allocate","alter","analyse","analyze","and","any","are","array","as","asc","asensitive","assertion","assignment","asymmetric","at","atomic","authorization","avg","backward","before","begin","between","binary","bit","bitvar","bit_length","blob","boolean","both","breadth","by","c","cache","call","called","cardinality","cascade","cascaded","case","cast","catalog","catalog_name","chain","char","character","characteristics","character_length","character_set_catalog","character_set_name","character_set_schema","char_length","check","checked","checkpoint","class","class_origin","clob","close","cluster","coalesce","cobol","collate","collation","collation_catalog","collation_name","collation_schema","column","column_name","command_function","command_function_code","comment","commit","committed","completion","condition_number","connect","connection","connection_name","constraint","constraints","constraint_catalog","constraint_name","constraint_schema","constructor","contains","continue","convert","copy","corresponding","count","create","createdb","createuser","cross","cube","current","current_date","current_path","current_role","current_time","current_timestamp","current_user","cursor","cursor_name","cycle","data","database","date","datetime_interval_code","datetime_interval_precision","day","deallocate","dec","decimal","declare","default","deferrable","deferred","defined","definer","delete","delimiters","depth","deref","desc","describe","descriptor","destroy","destructor","deterministic","diagnostics","dictionary","disconnect","dispatch","distinct","do","domain","double","drop","dynamic","dynamic_function","dynamic_function_code","each","else","encoding","encrypted","end","end-exec","equals","escape","every","except","exception","exclusive","exec","execute","existing","exists","explain","external","extract","false","fetch","final","first","float","for","force","foreign","fortran","forward","found","free","freeze","from","full","function","g","general","generated","get","global","go","goto","grant","granted","group","grouping","handler","having","hierarchy","hold","host","hour","identity","ignore","ilike","immediate","implementation","in","increment","index","indicator","infix","inherits","initialize","initially","inner","inout","input","insensitive","insert","instance","instantiable","instead","int","integer","intersect","interval","into","invoker","is","isnull","isolation","iterate","join","k","key","key_member","key_type","lancompiler","language","large","last","lateral","leading","left","length","less","level","like","limit","listen","load","local","localtime","localtimestamp","location","locator","lock","lower","m","map","match","max","maxvalue","message_length","message_octet_length","message_text","method","min","minute","minvalue","mod","mode","modifies","modify","module","month","more","move","mumps","name","names","national","natural","nchar","nclob","new","next","no","nocreatedb","nocreateuser","none","not","nothing","notify","notnull","null","nullable","nullif","number","numeric","object","octet_length","of","off","offset","oids","old","on","only","open","operation","operator","option","options","or","order","ordinality","out","outer","output","overlaps","overlay","overriding","owner","pad","parameter","parameters","parameter_mode","parameter_name","parameter_ordinal_position","parameter_specific_catalog","parameter_specific_name","parameter_specific_schema","partial","pascal","password","path","pendant","pli","position","postfix","precision","prefix","preorder","prepare","preserve","primary","prior","privileges","procedural","procedure","public","read","reads","real","recursive","ref","references","referencing","reindex","relative","rename","repeatable","replace","reset","restrict","result","return","returned_length","returned_octet_length","returned_sqlstate","returns","revoke","right","role","rollback","rollup","routine","routine_catalog","routine_name","routine_schema","row","rows","row_count","rule","savepoint","scale","schema","schema_name","scope","scroll","search","second","section","security","select","self","sensitive","sequence","serializable","server_name","session","session_user","set","setof","sets","share","show","similar","simple","size","smallint","some","source","space","specific","specifictype","specific_name","sql","sqlcode","sqlerror","sqlexception","sqlstate","sqlwarning","start","state","statement","static","statistics","stdin","stdout","structure","style","subclass_origin","sublist","substring","sum","symmetric","sysid","system","system_user","table","table_name","temp","template","temporary","terminate","than","then","time","timestamp","timezone_hour","timezone_minute","to","toast","trailing","transaction","transactions_committed","transactions_rolled_back","transaction_active","transform","transforms","translate","translation","treat","trigger","trigger_catalog","trigger_name","trigger_schema","trim","true","truncate","trusted","type","uncommitted","under","unencrypted","union","unique","unknown","unlisten","unnamed","unnest","until","update","upper","usage","user","user_defined_type_catalog","user_defined_type_name","user_defined_type_schema","using","vacuum","valid","value","values","varchar","variable","varying","verbose","version","view","when","whenever","where","with","without","work","write","year","zone"};

    public static final String[] HTML = {};
}
