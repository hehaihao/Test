package com.xm6leefun.common.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sfSlashYMD = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat sfSlashMD = new SimpleDateFormat("MM/dd");

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTime() {
        return sf.format(new Date());
    }

    /**
     * 获取当前时间的时间戳
     *
     * @return
     */
    public static long getCurrentTimeStamps() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间的时间戳（ms）
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurrentTime() {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    /**
     * 获取当前日期（不包含时间）
     * yyyy/MM/dd
     *
     * @return
     */
    public static String getCurrentDateYMDStr() {
        Date today = new Date(System.currentTimeMillis());
        return sfSlashYMD.format(today);
    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s, String format) {
        if (StrUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return ts;
    }

    /**
     * 时间戳转时间（不包含时间）
     * yyyy/MM/dd
     *
     * @param milSecond
     * @return
     */
    public static String stamps2DateStr(long milSecond) {
        Date date = new Date(milSecond * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 时间戳转时间
     *
     * @param milSecond
     * @return
     */
    public static String stamps2TimeStr(long milSecond) {
        Date date = new Date(milSecond * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 24小时制转化成12小时制
     *
     * @param strDay
     */
    public static String timeFormat24To12Str(Calendar calendar, String strDay) {
        String tempStr;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 11) {
            tempStr = "下午" + " " + strDay;
        } else {
            tempStr = "上午" + " " + strDay;
        }
        return tempStr;
    }

    /**
     * 计算两个时间的间隔
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int stringDaysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 60);
//        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取指定日期是星期几
     *
     * @param dateTime
     * @return
     */
    public static int getDayofWeek(String dateTime) {
        Calendar cal = Calendar.getInstance();
        if (dateTime.equals("")) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            Date date;
            try {
                date = sdf.parse(dateTime);
            } catch (ParseException e) {
                date = null;
                e.printStackTrace();
            }
            if (date != null) {
                cal.setTime(new Date(date.getTime()));
            }
        }
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 时间转化为星期
     *
     * @param indexOfWeek 星期的第几天
     */
    public static String getWeekDayStr(int indexOfWeek) {
        switch (indexOfWeek) {
            case 1:
                return "星期日";
//                return "周日";
            case 2:
                return "星期一";
//            return "周一";
            case 3:
                return "星期二";
//                return "周二";
            case 4:
                return "星期三";
//                return "周三";
            case 5:
                return "星期四";
//                return "周四";
            case 6:
                return "星期五";
//                return "周五";
            case 7:
                return "星期六";
//                return "周六";
            default:
                return "***";
//                    return "**";
        }
    }

    /**
     * 将时间戳格式化，13位的转为10位
     *
     * @param timestamp
     * @return
     */
    public static long timestampFormate(long timestamp) {
        String timestampStr = timestamp + "";
        if (timestampStr.length() == 13) {
            timestamp = timestamp / 1000;
        }
        return timestamp;
    }

    /**
     * 获取日起时间秒差
     *
     * @param time    需要格式化的时间 如"2014-07-14 19:01:45"
     * @param pattern 输入参数time的时间格式 如:"yyyy-MM-dd HH:mm:ss"
     *                <p/>
     *                如果为空则默认使用"yyyy-MM-dd HH:mm:ss"格式
     * @return time为null，或者时间格式不匹配，输出空字符""
     * @throws ParseException
     */
    public static long formatLongTime(String time, String pattern) {
        long dTime = 0;
        if (time != null) {
            if (pattern == null)
                pattern = "yyyy-MM-dd HH:mm:ss";
            Date tDate;
            try {
                tDate = new SimpleDateFormat(pattern).parse(time);
                Date today = new Date();
                if (tDate != null) {
                    dTime = (today.getTime() - tDate.getTime()) / 1000;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dTime;
    }

    /**
     * 获取日期时间戳
     *
     * @param time    需要格式化的时间 如"2014-07-14 19:01:45"
     * @param pattern 输入参数time的时间格式 如:"yyyy-MM-dd HH:mm:ss"
     *                <p/>
     *                如果为空则默认使用"yyyy-MM-dd HH:mm:ss"格式
     * @return time为null，或者时间格式不匹配，输出空字符""
     * @throws ParseException
     */
    public static long timeStamp(String time, String pattern) {
        long dTime = 0;
        if (time != null) {
            if (pattern == null)
                pattern = "yyyy-MM-dd HH:mm:ss";
            Date tDate;
            try {
                tDate = new SimpleDateFormat(pattern).parse(time);
                if (tDate != null) {
                    dTime = tDate.getTime() / 1000;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dTime;
    }

    /**
     * 时间转化为显示字符串
     *
     * @param timeStamp 单位为秒
     */
    public static String getTimeStr(long timeStamp) {
        if (timeStamp == 0)
            return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp * 1000);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            return "昨天";
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, -5);
            if (calendar.before(inputTime)) {
                return getWeekDayStr(inputTime.get(Calendar.DAY_OF_WEEK));
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
                int year = inputTime.get(Calendar.YEAR);
                int month = inputTime.get(Calendar.MONTH) + 1;
                int day = inputTime.get(Calendar.DAY_OF_MONTH);
                return year + "-" + month + "-" + day;
            }

        }

    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 时间转化为聊天界面显示字符串
     *
     * @param timeStamp 单位为秒
     */
    public static String getChatTimeStr(long timeStamp) {
        if (timeStamp == 0)
            return "";
        Calendar inputTime = Calendar.getInstance();
        String timeStr = timeStamp + "";
        if (timeStr.length() == 10) {
            timeStamp = timeStamp * 1000;
        }
        inputTime.setTimeInMillis(timeStamp);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        // if (calendar.before(inputTime)){
        // //当前时间在输入时间之前
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy" +
        // "年"+"MM"+"月"+"dd"+"日");
        // return sdf.format(currenTimeZone);
        // }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
            return timeFormat24To12Str(inputTime, sdf.format(currenTimeZone));
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
            return "昨天" + " " + timeFormat24To12Str(inputTime, sdf.format(currenTimeZone));
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("M" + "月" + "d" + "日");
                String temp1 = sdf.format(currenTimeZone);
                SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm");
                String temp2 = timeFormat24To12Str(inputTime, sdf1.format(currenTimeZone));
                return temp1 + temp2;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "/" + "M" + "/" + "d" + " ");
                String temp1 = sdf.format(currenTimeZone);
                SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm");
                String temp2 = timeFormat24To12Str(inputTime, sdf1.format(currenTimeZone));
                return temp1 + temp2;
            }

        }

    }

    /**
     * 群发使用的时间转换
     */
    public static String multiSendTimeToStr(long timeStamp) {

        if (timeStamp == 0)
            return "";
        Calendar inputTime = Calendar.getInstance();
        String timeStr = timeStamp + "";
        if (timeStr.length() == 10) {
            timeStamp = timeStamp * 1000;
        }
        inputTime.setTimeInMillis(timeStamp);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            @SuppressWarnings("unused")
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String format = sdf.format(currenTimeZone);
            return "昨天" + " " + format;
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, -5);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                String temp2 = sdf1.format(currenTimeZone);
                return getWeekDayStr(inputTime.get(Calendar.DAY_OF_WEEK)) + " " + temp2;
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
                if (calendar.before(inputTime)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("M" + "/" + "d" + " ");
                    String temp1 = sdf.format(currenTimeZone);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                    String temp2 = sdf1.format(currenTimeZone);
                    return temp1 + temp2;
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "/" + "M" + "/" + "d" + " ");
                    String temp1 = sdf.format(currenTimeZone);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                    String temp2 = sdf1.format(currenTimeZone);
                    return temp1 + temp2;
                }
            }
        }
    }

    /**
     * 格式化时间（输出类似于 刚刚, 4分钟前, 一小时前, 昨天这样的时间）
     *
     * @param time    需要格式化的时间 如"2014-07-14 19:01:45"
     * @param pattern 输入参数time的时间格式 如:"yyyy-MM-dd HH:mm:ss"
     *                <p/>
     *                如果为空则默认使用"yyyy-MM-dd HH:mm:ss"格式
     * @return time为null，或者时间格式不匹配，输出空字符""
     */
    public static String formatDisplayTime(String time, String pattern) {
        String display = "";
        int tMin = 60 * 1000;
        int tHour = 60 * tMin;
        int tDay = 24 * tHour;

        if (time != null) {
            if (pattern == null)
                pattern = "yyyy-MM-dd HH:mm:ss";
            try {
                Date tDate = new SimpleDateFormat(pattern).parse(time);
                Date today = new Date();
                SimpleDateFormat thisYearDf = new SimpleDateFormat("yyyy");
                SimpleDateFormat todayDf = new SimpleDateFormat("yyyy-MM-dd");
                Date thisYear = new Date(thisYearDf.parse(thisYearDf.format(today)).getTime());
                Date yesterday = new Date(todayDf.parse(todayDf.format(today)).getTime());
                Date beforeYes = new Date(yesterday.getTime() - tDay);
                if (tDate != null) {
                    @SuppressWarnings("unused")
                    SimpleDateFormat halfDf = new SimpleDateFormat("MM月dd日");
                    long dTime = today.getTime() - tDate.getTime();
                    if (tDate.before(thisYear)) {
                        display = new SimpleDateFormat("yyyy年MM月dd日").format(tDate);
                    } else {

                        if (dTime < tMin) {
                            display = "刚刚";
                        } else if (dTime < tHour) {
                            display = (int) Math.ceil(dTime / tMin) + "分钟前";
                        } else if (dTime < tDay && tDate.after(yesterday)) {
                            display = (int) Math.ceil(dTime / tHour) + "小时前";
                        } else if (tDate.after(beforeYes) && tDate.before(yesterday)) {
                            display = "昨天  " + new SimpleDateFormat("HH:mm").format(tDate);
                        } else {
                            display = multiSendTimeToStr(tDate.getTime() / 1000);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return display;
    }


    /**
     * 格式化时间（输出类似于 刚刚, 4分钟前, 一小时前, 昨天这样的时间）
     *
     * @param time 需要格式化的时间 如"2014-07-14 19:01:45"
     * @return time为null，或者时间格式不匹配，输出空字符""
     */

    public static String formatDisplayTime(String time) {
        String display = "";

        long tMin = 60 * 1000;
        long tHour = 60 * tMin;
        long tDay = 24 * tHour;
        long tMonth = 30 * tDay;
        long tYear = 365 * tMonth;

        if (time != null) {
            String pattern = "yyyy-MM-dd HH:mm:ss";
            try {
                Date tDate = new SimpleDateFormat(pattern).parse(time);
                if (tDate != null) {
                    long dTime = System.currentTimeMillis() - tDate.getTime() ;
                    if (dTime < tMin) {
                        display = "刚刚";
                    } else if (dTime > tMin && dTime <= tHour) {
                        display = (int) Math.ceil(dTime / tMin) + "分钟前";
                    } else if (dTime > tHour && dTime <= tDay) {
                        display = (int) Math.ceil(dTime / tHour) + "小时前";
                    } else if (dTime > tDay && dTime <= tMonth) {
                        display = (int) Math.ceil(dTime / tDay) + "天前";
                    } else if (dTime > tMonth && dTime <= tYear) {
                        display = (int) Math.ceil(dTime / tMonth) + "月前";
                    } else {
                        display = (int) Math.ceil(dTime / tYear) + "年前";
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return display;
    }

    public static void main(String[] args) {
        //TODO 当前时间
        System.out.println("当前时间戳=" + System.currentTimeMillis());
        System.out.println("当前时间=" + getTime());
        // TODO 字传入时间日期字符串转换群发使用时间输出
        System.out.println(formatDisplayTime("2017-06-30 10:34:00", null));
        // TODO 先 转换成时间戳 转换成对应的时间输出格式
        long timeStamp = timeStamp("2020-01-04 10:34:00", null);
        System.out.println(multiSendTimeToStr(timeStamp));//群发使用时间
        System.out.println(getChatTimeStr(timeStamp));//时间转化为聊天界面显示字符串
        System.out.println(getTimeStr(timeStamp));//时间转化为显示字符串

    }

    private static Calendar cd = Calendar.getInstance();

    /**
     * 获取年
     *
     * @return
     */
    public static int getYear() {
//        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return
     */
    public static int getMonth() {
//        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @return
     */
    public static int getDay() {
//        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.DATE);
    }

    /**
     * 获取时
     *
     * @return
     */
    public static int getHour() {
//        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.HOUR);
    }

    /**
     * 获取分
     *
     * @return
     */
    public static int getMinute() {
//        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MINUTE);
    }

    /**
     * 获取秒
     *
     * @return
     */
    public static int getSecond() {
//        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.SECOND);
    }

}
