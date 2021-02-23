package com.deng.study.util;



import org.apache.commons.text.translate.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc:Html处理类
 * @Auther: dengyanliang
 * @Date: 2020/1/21 11:14
 */
public class HtmlUtil {

    private static final String REG_EX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String REG_EX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String REG_EX_HTML = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String REG_EX_SPACE = "\\s*|\t|\r|\n";//定义空格回车换行符

    /**
     * 基础的html转义器，只转义 \ & < >
     */
    public static final CharSequenceTranslator ESCAPE_HTML =
            new AggregateTranslator(
                    new LookupTranslator(EntityArrays.BASIC_ESCAPE)
            );
    /**
     * 基础的html反转义器，只反转义 \ & < >
     */
    public static final CharSequenceTranslator UNESCAPE_HTML =
            new AggregateTranslator(
                    new LookupTranslator(EntityArrays.BASIC_UNESCAPE),
                    new NumericEntityUnescaper()
            );
    public static String dealWithContent(String content){
        content = delHTMLTag(content);
        return content;
    }
    public static String delHTMLTag(String htmlStr) {
        String unescapeHtml = UNESCAPE_HTML.translate(htmlStr);
        return  ESCAPE_HTML.translate(unescapeHtml);

    }
    /**
     * @param htmlStr
     * @return
     *  删除Html标签
     */
    public static String delHTMLTag2(String htmlStr) {

        htmlStr = dealWithRegExScript(htmlStr);
        htmlStr = dealWithRegExStyle(htmlStr);
        htmlStr = dealWithRegExHtml(htmlStr);
        htmlStr = dealWithRegExpace(htmlStr);

        return htmlStr; // 返回文本字符串
    }

    private static String dealWithRegExScript(String htmlStr){
        Pattern pScript = Pattern.compile(REG_EX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher mScript = pScript.matcher(htmlStr);
        if(mScript.find()){
            htmlStr = mScript.replaceAll(""); // 过滤script标签
        }
        return htmlStr;
    }
    private static String dealWithRegExStyle(String htmlStr){
        Pattern pStyle = Pattern.compile(REG_EX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher mStyle = pStyle.matcher(htmlStr);
        if(mStyle.find()){
            htmlStr = mStyle.replaceAll(""); // 过滤style标签
        }
        return htmlStr;
    }
    private static String dealWithRegExHtml(String htmlStr){
        Pattern pHtml = Pattern.compile(REG_EX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher mHtml = pHtml.matcher(htmlStr);
        if(mHtml.find()){
            htmlStr = mHtml.replaceAll(""); // 过滤html标签
        }
        return htmlStr;
    }
    private static String dealWithRegExpace(String htmlStr){
        Pattern pSpace = Pattern.compile(REG_EX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher mSpace = pSpace.matcher(htmlStr);
        if(mSpace.find()){
            htmlStr = mSpace.replaceAll(""); // 过滤空格回车标签
        }

        htmlStr.trim().replaceAll("&nbsp;", "");
        return htmlStr;
    }
}
