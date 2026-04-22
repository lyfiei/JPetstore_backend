package com.csu.common.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 字符串工具类
 */
public class StringUtils extends StrUtil {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(CharSequence str) {
        return StrUtil.isEmpty(str);
    }

    /**
     * 判断字符串是否不为空
     */
    public static boolean isNotEmpty(CharSequence str) {
        return StrUtil.isNotEmpty(str);
    }

    /**
     * 判断字符串是否为空白
     */
    public static boolean isBlank(CharSequence str) {
        return StrUtil.isBlank(str);
    }

    /**
     * 判断字符串是否不为空白
     */
    public static boolean isNotBlank(CharSequence str) {
        return StrUtil.isNotBlank(str);
    }
}
