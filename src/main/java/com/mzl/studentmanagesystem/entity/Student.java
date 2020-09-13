package com.mzl.studentmanagesystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Student
 * @Description: 学生实体类
 * @Author: 21989
 * @CreateDate: 2020/7/29 10:53
 * @Version: 1.0
 */
@Data   //getter、getter、toString方法自动生成
@NoArgsConstructor
public class Student {

    private int id;
    private String sn;
    private String username;
    private String password;
    private int clazzId;
    private String sex = "男";
    private String mobile;
    private String qq;
    private String photo;   //头像

}
