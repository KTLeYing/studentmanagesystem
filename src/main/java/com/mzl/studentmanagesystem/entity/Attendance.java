package com.mzl.studentmanagesystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Attendance
 * @Description: 缺勤表
 * @Author: 21989
 * @CreateDate: 2020/7/31 17:05
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class Attendance {

    private Integer id;
    private Integer courseId;
    private Integer studentId;
    private String type;
    private String date;

}
