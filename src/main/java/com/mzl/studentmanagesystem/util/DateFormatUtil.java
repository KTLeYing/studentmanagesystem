package com.mzl.studentmanagesystem.util;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName :   DateFormatUtil
 * @Description: 日期转换工具
 * @Author: 21989
 * @CreateDate: 2020/7/31 21:15
 * @Version: 1.0
 */
public class DateFormatUtil {

    public static String getFormatDate(Date date, String format){//(new Date(), "yyyy-MM-dd")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
}
