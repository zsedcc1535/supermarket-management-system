package com.Ambition.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取格式化好的当前时间
 */
public class AppDateUtils {

    public static String getFormatTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String string = format.format(new Date());
        return string;
    }

    public static String getFormatTime(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String string = format.format(new Date());
        return string;
    }

    public static String getFormatTime(String pattern, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String string = format.format(date);
        return string;
    }
    
	public static String getFormatTime(Date date){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String string = sdf.format(date);
		return string;
	}

	public static Date toDate(String time) throws Exception {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(time);
    }
    public static String MyDate(String time) throws Exception {
        time = time.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
        String result = null;
        try {
            Date time1 = format.parse(time);
            result = defaultFormat.format(time1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
