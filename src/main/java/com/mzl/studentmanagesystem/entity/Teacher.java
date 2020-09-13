package com.mzl.studentmanagesystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @ClassName :   Teacher
 * @Description: 教师
 * @Author: 21989
 * @CreateDate: 2020/7/29 14:57
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class Teacher {

    private int id;
    private String sn;
    private String username;
    private String password;
    private int clazzId;
    private String sex;
    private String mobile;
    private String qq;
    private String photo;

}
