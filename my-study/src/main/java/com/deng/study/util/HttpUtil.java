package com.deng.study.util;

import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc:HttpUrl处理类
 * @Auther: dengyanliang
 * @Date: 2020/1/21 11:25
 */
public class HttpUtil {
    private static String CUT_OFF_STR = "${ll}$"; // 截断的替换字符

    private static final String TOP_LEVEL_DOMAIN = "design|museum|travel|aero|arpa|asia|coop|info|jobs|mobi|name|biz|cat|com|edu|gov|int|mil|net|org|pro|tel|xxx|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|png|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sk|sl|sm|sn|so|sr|ss|st|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|za|zm|zw|dp|product|app|dev";
    private static final String REG_URL_STR = "(?:(https|http|ftp|mtdaxiang):\\/\\/)?" +   //匹配协议，  $1
            "(?:(@)?" +   //作为向前查找的hack，   $2
            "(\\d{1,3}(?:\\.\\d{1,3}){3})|" +    //匹配ip，  $3
            "((?:[-a-z_A-Z_0-9]{1,256}\\.){1,256}(?:" + TOP_LEVEL_DOMAIN + "\\b))" + //匹配域，  $4
            ")" +
            "(:\\d{1,5})?" +     //匹配端口,  $5
            "(\\/[-a-z_A-Z_0-9@:%+.~&/=()!\',*$]*)?" +   //匹配路径 $6
            "(\\?[-a-z_A-Z_0-9@:%+.~&/=()!\',;{}*?$]*)?" +   //匹配查询参数   $7
            "(\\#[-a-z_A-Z_0-9@:%+.~&/=()!\';{}*?#^$]*)?";//匹配锚点，   $8

    private static final Pattern PATTERN = Pattern.compile(REG_URL_STR.trim());

    //匹配含[xxx|xxxxx]这种格式的 '|'后面内容随意
//    String REG_LINK_STR = "\\[\\s*" +
//            "([^\\[\\|]+)" + //匹配连接文字, $1
//            "\\s*\\|\\s*" +
//            "([^\\] \\n]+)" +   //匹配后面内容， $2
//            "\\s*\\]";
//
//    private static final String REG_ANCHOR_TAG = "\\/(<a.*?\\/a>)\\/gi";
//    RegExp REG_URL = new RegExp(REG_URL_STR, "gi") {
//        @Override
//        public RegExpMatcher match(String s) {
//            return null;
//        }
//    };
//    RegExp REG_LINK = new RegExp(REG_LINK_STR, "gmi") {
//        @Override
//        public RegExpMatcher match(String s) {
//            return null;
//        }
//    };
//    RegExp REG_LINK_EXT = new RegExp("^" + REG_URL_STR + "$", "i") {
//        @Override
//        public RegExpMatcher match(String s) {
//            return null;
//        }
//    };
//    String TEL_LINK = "\\/tel:(\\s+)?[\\d-]+(\\s+)?$\\/";

//    private static final String REG_EX_SCRIPT = "[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?";

//    private static final String HTTP =  "^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+";
//    private static final Pattern HTTP_PAT = Pattern.compile(HTTP.trim()); // 对比

    public static String dealWithContent(String content){
        Matcher mat = PATTERN.matcher(content.trim());
        List<String> urlList = Lists.newArrayList();

        // 1.找到匹配的字符串url
        // 2.判断url是否带http/https开头，如果带了，则在<a>标签中不再添加http/https；否则加上
        // 3.将url放入到集合中urlList
        // 4.将content中的第一个url替换为占位符；继续循环
        // 5.循环结束，遍历urlList，将占位符依次替换为urlList中的内容
        while (mat.find()){
            String url = mat.group(0);
            if(url.indexOf("https") == 0 || url.indexOf("http") == 0){
                urlList.add("<a href="+url+">"+url+"</a>");
            }else{
                urlList.add("<a href='http://"+url+"'>http://"+url+"</a>");
            }
            content = replaceFirstUrl(content,url);
        }
        content = recoverReplacedUrl(content,urlList);
        return content;
    }

    /**
     * 将content中的第一个url替换为占位符
     * @param content 原始字符串内容
     * @param url 识别出来的url
     * @return
     */
    private static String replaceFirstUrl(String content,String url){
        StringBuffer buffer = new StringBuffer(200);
        int firstIndex = content.indexOf(url);

        buffer.append(content, 0, firstIndex);
        buffer.append(CUT_OFF_STR);
        buffer.append(content.substring(firstIndex+url.length()));

        return buffer.toString();
    }

    /**
     * 将字符串中的占位符还原
     * @param content
     * @param urlList
     * @return
     */
    private static String recoverReplacedUrl(String content,List<String> urlList){
        if(content.contains(CUT_OFF_STR) == false){
            return content;
        }
        StringBuffer buffer = null;
        int num = 0;
        while(content.contains(CUT_OFF_STR)){
            buffer = new StringBuffer(200);
            int index = content.indexOf(CUT_OFF_STR);

            buffer.append(content, 0, index);
            buffer.append(urlList.get(num));
            buffer.append(content.substring(index + CUT_OFF_STR.length()));

            content = buffer.toString();
            num++;
        }
        return buffer.toString();
    }
}
