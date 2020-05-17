package com.dmf.bootstarter.common.util;


import org.apache.commons.lang.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间转化工具类 (jdk1.8 LocalDateTime 等相关转化)
 * --- (其他关于时间的操作建议用 LocalDateTime等jdk原生方法)
 *
 * @author dmf
 */
public class LocalDateUtils {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final ZoneId ZONE_ID = ZoneId.systemDefault();
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);

    /**
     * LocalDateTime >>> Long
     *
     * @param localDateTime the local date-time, not null
     * @return Long
     */
    public static Long getTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZONE_ID).toInstant().toEpochMilli();
    }

    /**
     * LocalDate >>> Long
     *
     * @param localDate the local date, not null
     * @return Long
     */
    public static Long getTime(LocalDate localDate) {
        return localDate.atStartOfDay(ZONE_ID).toInstant().toEpochMilli();
    }

    /**
     * LocalDate >>> Date
     *
     * @param localDate the local date, not null
     * @return Date
     */
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZONE_ID).toInstant());
    }

    /**
     * LocalDateTime >>> Date
     *
     * @param localDateTime the local date-time, not null
     * @return Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZONE_ID).toInstant());
    }

    /**
     * date >>> LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalDateTime();
    }

    /**
     * date >>> LocalDate
     *
     * @param date Date
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalDate();
    }

    /**
     * Long >>> LocalDateTime
     *
     * @param millis 毫秒值
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZONE_ID);
    }

    /**
     * Long >>> LocalDate
     *
     * @param millis 毫秒值
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Long millis) {
        return toLocalDateTime(millis).toLocalDate();
    }

    /**
     * format
     * {@link LocalDateUtils#DATE_TIME_FORMATTER yyyy-MM-dd hh:mm:ss}
     *
     * @return LocalDate such as '2017-11-11 11:11:11'
     */
    public static String format(Date date) {
        return format(date, null);
    }

    public static String format(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_FORMAT;
        }
        return toLocalDate(date).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * format
     * {@link LocalDateUtils#DATE_TIME_FORMATTER yyyy-MM-dd hh:mm:ss}
     *
     * @param localDateTime the local date-time, not null
     * @return LocalDate such as '2017-11-11 11:11:11'
     */
    public static String format(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * parse
     * {@link LocalDateUtils#DATE_TIME_FORMATTER yyyy-MM-dd hh:mm:ss}
     *
     * @param text such as '2017-11-11 11:11:11'
     * @return LocalDate
     */
    public static LocalDateTime parse(CharSequence text) {
        return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
    }

}