package com.csu.common.utils;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化日期
     */
    public static String formatDate(Date date) {
        return DateUtil.format(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 格式化时间
     */
    public static String formatTime(Date date) {
        return DateUtil.format(date, DEFAULT_TIME_FORMAT);
    }

    /**
     * 格式化日期时间
     */
    public static String formatDateTime(Date date) {
        return DateUtil.format(date, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 获取当前日期字符串
     */
    public static String getCurrentDate() {
        return DateUtil.today();
    }

    /**
     * 获取当前日期时间字符串
     */
    public static String getCurrentDateTime() {
        return DateUtil.now();
    }
}
