package com.baojie.jeesite.util.util;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：
 */
public class UUID {

    public static String randomUUID10() {
        return RandomUtils.randomString(10);
    }

    public static String randomUUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String randomUUID(int length) {
        String uuId = randomUUID();
        if(length > uuId.length()) {
            length = uuId.length();
        }
        return uuId.substring(0, length);
    }

    public static String randomUserId() {
        return RandomUtils.randomNumberString(10);
    }

}
