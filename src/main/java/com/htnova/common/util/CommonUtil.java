package com.htnova.common.util;

import org.apache.commons.lang3.RandomStringUtils;

public class CommonUtil {

    private CommonUtil() {}

    /** 获取随机字符串 */
    public static String getRandomString(int length) {
        return RandomStringUtils.randomGraph(length);
    }

    /** 获取随机数字 */
    public static String getRandomNum(int length) {
        return RandomStringUtils.random(length, "0123456789");
    }
}
