package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static int x;
    private static Calendar localTime = Calendar.getInstance();
    public static String transTimeStampToDate(long timeStamp) {
        Date date = new Date(timeStamp);
        return sdf.format(date);
    }

    public static long transDateToTimeStamp(String dateStr) {
        try {
            Date date = sdf.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("日期格式不符");
            return 0L;
        }
    }


    public static void main(String[] args) {
        String dateStr = transTimeStampToDate(1537166299000L);
        transDateToTimeStamp(dateStr);
    }

    public static int compareDate(String date1, String date2) {
        return compareDate(date1, date2, "yyyy-MM-dd");
    }

    public static int compareDate(String date1, String date2, String patten) {
        DateFormat df = new SimpleDateFormat(patten);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            return Long.compare(dt1.getTime(), dt2.getTime());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static Date parseDate(String date) {
        return parseDate(date, "yyyy-MM-dd");
    }

    public static Date parseDate(String date, String patten) {
        DateFormat df = new SimpleDateFormat(patten);
        try {
            return df.parse(date);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String parseString(Date date, String patten) {
        DateFormat df = new SimpleDateFormat(patten);
        try {
            return df.format(date);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }

    public static String parseString(Date date) {
        return parseString(date, "yyyy-M-d");
    }

    public static String changeToYYYY_MM_DD(String dateStr) {
        String temp = StringUtil.getNotNullStr(dateStr);
        String[] dateArray = temp.split("-");
        //正确日期格式为年月日
        if (dateArray.length == 3) {
            dateArray[1] = dateArray[1].length() == 1 ? "0" + dateArray[1] : dateArray[1];
            dateArray[2] = dateArray[2].length() == 1 ? "0" + dateArray[2] : dateArray[2];
            return dateArray[0] + "-" + dateArray[1] + "-" + dateArray[2];
        }
        return null;
    }

    public static Date dateTimeStringToYYYY_MM_DD(String dateTimeString) {
        //2008.01.01 00:00:00 转成 date类型
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dateTimeString = dateTimeString.split(" ")[0].replace(".", "-");
        try {
            return df.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Date> getLastWeek() {
        Map<String, Date> map = new HashMap<String, Date>();
        Calendar cal = Calendar.getInstance();
        int n = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (n == 0) {
            n = 7;
        }
        cal.add(Calendar.DATE, -(7 + (n - 1)));// 上周一的日期
        Date monday = cal.getTime();
        map.put("monday", monday);

        cal.add(Calendar.DATE, 1);
        Date tuesday = cal.getTime();
        map.put("tuesday", tuesday);

        cal.add(Calendar.DATE, 1);
        Date wednesday = cal.getTime();
        map.put("wednesday", wednesday);

        cal.add(Calendar.DATE, 1);
        Date thursday = cal.getTime();
        map.put("thursday", thursday);

        cal.add(Calendar.DATE, 1);
        Date friday = cal.getTime();
        map.put("friday", friday);

        cal.add(Calendar.DATE, 1);
        Date saturday = cal.getTime();
        map.put("saturday", saturday);

        cal.add(Calendar.DATE, 1);
        Date sunday = cal.getTime();
        map.put("sunday", sunday);
        return map;
    }

    // 获取上月第一天
    public static String getLastMonthDayOne(Date date) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar1.getTime());
    }


    // 获取上月最后一天
    public static String getLastMonthLastDay(Date date) {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar2.getTime());
    }

    public static String getLastYear() {
        x = localTime.get(Calendar.YEAR) - 1;
        return x + "-01" + "-01";
    }

    public static String getLastYearEnd() {
        x = localTime.get(Calendar.YEAR) - 1;
        return x + "-12" + "-31";
    }

}
