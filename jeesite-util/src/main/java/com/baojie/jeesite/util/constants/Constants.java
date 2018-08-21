package com.baojie.jeesite.util.constants;

//import com.baojie.jeesite.util.cache.CacheKey;

import java.util.Map;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：
 */
public interface Constants {

    String EXCEPTION_HEAD = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :";
/**   Map<Class<?>, CacheKey> cacheKeyMap = InstanceUtil.newHashMap();*/
    String OPERATION_NAME = "OPERATION_NAME";
    String USERLANGUAGE = "userLanguage";
    String WEBTHEME = "webTheme";
    String CURRENT_USER = "CURRENT_USER";
    String USER_AGENT = "USER-AGENT";
    String USER_IP = "USER_IP";
    String LOGIN_URL = "/login.html";
    String CACHE_NAMESPACE = "iBase4J:";
    String SYSTEM_CACHE_NAMESPACE = "S:iBase4J:";
    String CACHE_NAMESPACE_LOCK = "L:iBase4J:";
    String PREREQUEST = "iBase4J:PREREQUEST";
    String PREREQUEST_TIME = "iBase4J:PREREQUEST_TIME";
    String MALICIOUS_REQUEST_TIMES = "iBase4J:MALICIOUS_REQUEST_TIMES";
    String ALLUSER_NUMBER = "S:iBase4J:ALLUSER_NUMBER";
    String TOKEN_KEY = "S:iBase4J:TOKEN_KEY";
    String REDIS_SHIRO_CACHE = "S:iBase4J:SHIRO-CACHE:";
    String REDIS_SHIRO_SESSION = "S:iBase4J:SHIRO-SESSION:";
    String MYBATIS_CACHE = "D:iBase4J:MYBATIS:";
    String DB_KEY = "90139119";
    String TEMP_DIR = "/temp/";
    String REQUEST_BODY = "iBase4J.requestBody";

    public interface MSGCHKTYPE {
        String REGISTER = "iBase4J:REGISTER:";
        String LOGIN = "iBase4J:LOGIN:";
        String CHGPWD = "iBase4J:CHGPWD:";
        String VLDID = "iBase4J:VLDID:";
        String CHGINFO = "iBase4J:CHGINFO:";
        String AVTCMF = "iBase4J:AVTCMF:";
    }

    public interface JOBSTATE {
        String INIT_STATS = "I";
        String SUCCESS_STATS = "S";
        String ERROR_STATS = "E";
        String UN_STATS = "N";
    }

}
