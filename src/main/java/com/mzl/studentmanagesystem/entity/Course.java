package com.mzl.studentmanagesystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Course
 * @Description: 课程
 * @Author: 21989
 * @CreateDate: 2020/7/30 13:59
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class Course {

    private int id;
    private String name;
    private int teacherId;
    private String courseDate;
    private int selectedNum = 0;   //已选人数，默认初始化为0
    private int maxNum = 50;  //课程总人数，默认为50
    private String info;

}
