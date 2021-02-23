package com.deng.study.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc:处理内容工具：主要是为了去掉a链接保存
 * @Auther: dengyanliang
 * @Date: 2020/4/9 16:25
 */
public class DealwithContentUtil2 {

    private static String CUT_OFF_REGEX = "(<a \\s*[^>]*>(.*?)<\\/a>)|(<a \\s*[^>]*>(.*?)<\\/a>)";
    private static Pattern pattern = Pattern.compile(CUT_OFF_REGEX);
    private static String CUT_OFF_STR_LINK = "${ll}$";
    private static String CUT_OFF_STR_TEXT = "${oo}$";
    private static String EMPTY_CONTENT = "[]";

    public static String process(String content) {
        if(StringUtils.isBlank(content)){
            return EMPTY_CONTENT;
        }
        StringBuffer contentBuffer = new StringBuffer(200);
        List<String> textList = Lists.newArrayList();

        String text = "";

        int a_beginIndex = content.indexOf("<a");

        if(a_beginIndex < 0){
            content = setText(content);
        }else if(a_beginIndex == 0){
            content = handleContent(content,textList);
        }else if(a_beginIndex > 0){
            // 从0开始到a_beginIndex的内容为text
            text = content.substring(0, a_beginIndex);
            content = content.replace(text,CUT_OFF_STR_TEXT); // 将0-url位置之间的内容替换为内容标识符
            textList.add(text);

            int beginIndex = content.indexOf("</a>");
            if(beginIndex > 0) {
                content = handleContent(content,textList);
            }
        }

        contentBuffer.append(content);

        return contentBuffer.toString();
    }

    private static String handleContent(String content, List<String> textList ){
        List<String> urlList = Lists.newArrayList();
        String text = "";

        Matcher m = pattern.matcher(content);
        while (m.find()) {
            String url = m.group(0);
            urlList.add(url);

            content = replaceFirstUrl(content, url);

            int index = content.lastIndexOf(CUT_OFF_STR_LINK);

            int beginIndex = content.indexOf("<a");
            if(beginIndex > 0) {
                text = content.substring(index + 6,beginIndex);
            }else{
                text = content.substring(index + 6);
            }
            if(StringUtils.isNotBlank(text)){
                content = content.replace(text,CUT_OFF_STR_TEXT); // 将0-url位置之间的内容替换为内容标识符
                textList.add(text);
            }
        }

        content = recoverReplacedUrl(content, urlList,textList);

        return content;
    }


    /**
     * 将content中的第一个url替换为占位符
     *
     * @param content 原始字符串内容
     * @param url     识别出来的url
     * @return
     */
    private static String replaceFirstUrl(String content, String url) {
        StringBuffer buffer = new StringBuffer(200);

        int firstIndex = content.indexOf(url); // 再次定位url的位置，因为此时原串的内容已经变了，所以位置也会变

        buffer.append(content, 0, firstIndex); // 拼接0-url位置之间的内容
        buffer.append(CUT_OFF_STR_LINK); // 添加url占位符
        buffer.append(content.substring(firstIndex + url.length())); //拼接剩余的内容

        return buffer.toString();
    }

    /**
     * 将字符串中的占位符还原
     *
     * @param content
     * @param urlList
     * @return
     */
    private static String recoverReplacedUrl(String content, List<String> urlList,List<String> textList) {
        if (content.contains(CUT_OFF_STR_LINK) == false && content.contains(CUT_OFF_STR_TEXT) == false) {
            return content;
        }
        StringBuffer buffer = new StringBuffer(200);
        int num_link = 0,num_text = 0;
        while (content.contains(CUT_OFF_STR_LINK) || content.contains(CUT_OFF_STR_TEXT)) {

            int index_link = content.indexOf(CUT_OFF_STR_LINK);
            int index_text = content.indexOf(CUT_OFF_STR_TEXT);

            String url = "";
            String text = "";
            if(CollectionUtils.isNotEmpty(urlList) && num_link < urlList.size()){
                url = urlList.get(num_link);
            }
            if(CollectionUtils.isNotEmpty(textList) && num_text < textList.size()){
                text = textList.get(num_text);
            }

            if(index_link >=index_text){
                url = setLink(url);
                text = setText(text);

                if(StringUtils.isNotBlank(text)){
                    buffer.append(content, 0, index_text);
                    buffer.append(text);
                }
                buffer.append(url);
                content = content.substring(index_link + CUT_OFF_STR_LINK.length());
            }else{
                text = setText(text);
                url = setLink(url);

                if(StringUtils.isNotBlank(url)){
                    buffer.append(content, 0, index_link);
                    buffer.append(url);
                }
                buffer.append(text);
                content = content.substring(index_text + CUT_OFF_STR_TEXT.length());
            }

            num_link++;
            num_text++;
        }

        return buffer.toString();
    }

    private static String setLink(String url){
        if(StringUtils.isBlank(url)){
            return url;
        }

        int startIndex = url.indexOf(">");
        int endIndex = url.indexOf("</a>");
        url = url.substring(startIndex+1,endIndex);

        StringBuffer buffer = new StringBuffer(200);
        buffer.append(url);

        return buffer.toString();
    }

    private static String setText(String text){
        if(StringUtils.isBlank(text)){
            return text;
        }

        StringBuffer buffer = new StringBuffer(200);
        buffer.append(text);

        return buffer.toString();
    }
}
