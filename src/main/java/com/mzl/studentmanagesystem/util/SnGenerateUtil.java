package com.mzl.studentmanagesystem.util;

/**
 * @ClassName :   SnGenerateUtil
 * @Description: 随机生成一个学号
 * @Author: mzl
 * @CreateDate: 2020/8/5 19:35
 * @Version: 1.0
 */
public class SnGenerateUtil {

    //生成学生学号
    public static String generateSn(int clazzId){
        String sn = "";
        sn = "S" + clazzId + System.currentTimeMillis();
        return sn;
    }

    //生成教师学号
    public static String generateTeacherSn(int clazzId){
        String sn = "";
        sn = "T" + clazzId + System.currentTimeMillis();
        return sn;
    }
}
