package com.dmf.boot.learn.test;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author dmf
 * @date 2018/8/13
 */
public class DateTest {

    public static int calculateMonthIn(Date end, Date begin) throws Exception {
        Assert.assertNotNull("结束时间不可以为空。", end);
        Assert.assertNotNull("开始时间不可以为空。", begin);
        if (begin.after(end)) {
            throw new Exception("开始时间大于结束时间。");
        } else {
            Calendar cal1 = new GregorianCalendar();
            cal1.setTime(end);
            Calendar cal2 = new GregorianCalendar();
            cal2.setTime(begin);
            int c = (cal1.get(1) - cal2.get(1)) * 12 + cal1.get(2) - cal2.get(2);
            return c;
        }
    }

    public static String conver(Date date) throws Exception {
        if (date == null) {
            return null;
        }
        Date now = new Date();
        //发布时间小于当前时间
        if (date.after(now)) {
            throw new Exception("发布或者刷新时间不能大于当前时间。");
        } else {
            Calendar cal1 = new GregorianCalendar();
            cal1.setTime(now);
            Calendar cal2 = new GregorianCalendar();
            cal2.setTime(date);
            StringBuilder sb = new StringBuilder();

            int year = cal1.get(GregorianCalendar.YEAR) - cal2.get(GregorianCalendar.YEAR);
            if (year > 0) {
                return sb.append(year).append("年前").toString();
            }

            int month = cal1.get(GregorianCalendar.MONTH) - cal2.get(GregorianCalendar.MONTH);
            if (month > 0) {
                return sb.append(month).append("个月前").toString();
            }

            int day = cal1.get(GregorianCalendar.DAY_OF_MONTH) - cal2.get(GregorianCalendar.DAY_OF_MONTH);
            if (day > 0) {
                return sb.append(day).append("天前").toString();
            }

            int hour = cal1.get(GregorianCalendar.HOUR_OF_DAY) - cal2.get(GregorianCalendar.HOUR_OF_DAY);
            if (hour > 0) {
                return sb.append(hour).append("小时前").toString();
            }

            int minute = cal1.get(GregorianCalendar.MINUTE) - cal2.get(GregorianCalendar.MINUTE);
            if (minute >= 0) {
                return sb.append(minute).append("分钟前").toString();
            }
            return "";
        }
    }

    public static void main(String[] args) throws Exception {
        Calendar calendar = Calendar.getInstance();
        //使用date 要注意year和month的计数区别
        //calendar.set(2018, 7, 18, 14, 30);
        calendar.setTime(new Date());
        /*Date date = calendar.getTime();
        String s = conver(date);
        System.out.println(s);*/
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH)+1);
    }
}
