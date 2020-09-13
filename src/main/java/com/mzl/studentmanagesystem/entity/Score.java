package com.mzl.studentmanagesystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Score
 * @Description: 成绩
 * @Author: 21989
 * @CreateDate: 2020/7/30 14:06
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class Score {

    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private double score;
    private String remark;

    //关联表
    private String courseName;
    private String studentName;
}
