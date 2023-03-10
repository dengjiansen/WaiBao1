package com.waibaoservice.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author DJS
 * Date create 1:44 2023/3/10
 * Modified By DJS
 **/

public class DateUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Calendar cal = Calendar.getInstance();
    private DateUtils() {}

    // 获取当前时间经过time之后的时间
    // time格式 小时:分钟:秒
    public static String getEndTimeStr(Date currentDate, String time, String sep) {
        cal.setTime(currentDate);
        String[] times = time.split(sep);
        cal.add(Calendar.HOUR, Integer.parseInt(times[0]));
        cal.add(Calendar.MINUTE, Integer.parseInt(times[1]));
        cal.add(Calendar.SECOND, Integer.parseInt(times[2]));
        Date endDate = cal.getTime();
        return sdf.format(endDate);
    }

    public static String getCurrentTimeStr(Date d) {
        return sdf.format(d);
    }

    public static Date parseDateStr(String dateStr) {
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
