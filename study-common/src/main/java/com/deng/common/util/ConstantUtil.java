package com.deng.common.util;

/**
 * @Desc:常量工具类
 * @Auther: dengyanliang
 * @Date: 2020/3/1 19:48
 */
public class ConstantUtil {

    public static final int ADD_ONE = 1;
    public static final int DECREASE_ONE = -1;

    public static final String EMPTY_CONTENT = "[]";

    public static final String SYS = "sys";

    public static final String MQ = "MQ";

    public static final String TYPE = "type";

    public static final String CONTENT = "content";
    public static final String PROPS = "props";

    public static final String REQUEST_CONTENT = "requestContent";
    public static final String NOTIFY_CONTENT = "notifyContent";
    public static final String EMP_ID_LIST = "empIdList";
    public final static String UNDERLINE ="_";


    /**
     * 获取feeds流列表的key前缀
     */
    public final static String REDIS_FEEDS_TOKEN_PREFIX="feeds_";

    /**
     * 获取聚合页列表的key前缀
     */
    public final static String REDIS_MOMENTS_TAG_PREFIX="tagmoments_";

    /**
     * moment信息的key前缀
     */
    public final static String REDIS_MOMENTS_PREFIX="moments_";

    /**
     * momentImage信息的key前缀
     */
    public final static String REDIS_MOMENT_IMAGES_PREFIX="moment_images_";

    /**
     * momentTag信息的key前缀
     */
    public final static String REDIS_MOMENT_TAGS_PREFIX="moment_tags_";

    /**
     * 获取个人动态列表的key前缀
     */
    public final static String REDIS_MOMENTS_USER_PREFIX="usermoments_";

    /**
     * 获取个人动态列表的key前缀
     */
    public final static String REDIS_USER_INFO_PREFIX="user_info_";

    /**
     * 获取分享列表的key前缀
     */
    public final static String REDIS_SHARE_TOKEN_PREFIX="shares_";

    /**
     * 获取用户关注主题标识
     */
    public final static String REDIS_USER_FOLLOW_PREFIX ="user_follow_";

    /**
     * 截断链接的正则
     */
    public static String CUT_OFF_REGEX = "(<a \\s*[^>]*>(.*?)<\\/a>)|(<a \\s*[^>]*>(.*?)<\\/a>)";

    /**
     * 截断的替换字符
     */
    public static String CUT_OFF_STR = "${ll}$";

    /**
     * 截断保留的字数
     */
    public static int CUT_OFF_NUM = 200;

}
