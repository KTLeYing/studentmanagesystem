package com.mzl.studentmanagesystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @ClassName :   Leave
 * @Description: 请假
 * @Author: 21989
 * @CreateDate: 2020/7/31 23:00
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class Leave {

    //请假转态
    public static int LEAVE_STATUS_WAIT = 0;   //等待审核
    public static int LEAVE_STATUS_AGREE = 1;  //同意
    public static int LEAVE_STATUS_DISAGREE = -1;  //不同意

    //属性
    private int id;
    private int studentId;
    private String info;  //请假理由
    private int status = LEAVE_STATUS_WAIT;  //请假条转态
    private String remark;   //批复内容


}
