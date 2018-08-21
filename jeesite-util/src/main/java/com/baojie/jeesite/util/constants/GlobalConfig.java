package com.baojie.jeesite.util.constants;

import lombok.Data;

/**
 * @author ：冀保杰
 * @date：2018-08-15
 * @desc：系统全局变量设置
 */
public class GlobalConfig {

    /**
     * 验证码长度
     */
    public static final int VALICATE_CODE_LENGTH = 4;

    public static final String VALICATE_CODE_PRE = "login:validCode:";
    /**
     * 保存再redis中的验证码
     */
    public static final String LOGIN_VALID_CODE = "LOGIN_VALID_CODE";

    /**
     * 显示验证码的登录失败次数
     */
    public static final  String LOGIN_FAIL_COUNT = "LOGIN_FAIL_COUNT";

    /**
     * 失败次数重置时间 单位：s
     */
    public static final Long LOGIN_FAIL_COUNT_LIMIT_TIME = 600L;

    /**
     * session 验证失效时间（默认为15分钟 单位：m秒）
     */
    public static final Integer SESSION_VALIDATION_INTERVAL = 15 * 60 * 1000;

    /**
     * session失效时间（默认为12h 单位：m秒）
     */
    public static final Integer SESSION_INVALI_DATETIME = 10 * 1000;

    /**
     * Cookie失效时间
     */
    public static final Long COOKIE_EXPIRETIME = 60 * 30L;

    /**
     * 系统cookie名称
     */
    public static final String COOKIE_NAME = "shiroCookieId";

    /**
     * 注册验证码失效时间 单位：s
     */
    public static final Long REGISTRY_CODE_EXPIRETIME = 60L;

    /**
     * 登录失败次数限制
     */
    public static final Integer MAX_LOGIN_ERROE_COUNT = 3;

    public static final String DEFAULT_CHART_SET = "UTF-8";

    /**
     * 加盐参数
     */
    public final static String HASH_ALGORITHM_NAME = "MD5";

    /**
     * 循环次数
     */
    public final static int HASH_ITERATIONS = 1024;

}
