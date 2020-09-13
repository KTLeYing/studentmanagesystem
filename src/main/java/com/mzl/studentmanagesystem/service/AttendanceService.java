package com.mzl.studentmanagesystem.service;

import com.mzl.studentmanagesystem.entity.Attendance;
import com.mzl.studentmanagesystem.util.PageBean;

import java.util.Map;

/**
 * @InterfaceName :   AttendanceService
 * @Description: 缺勤业务逻辑
 * @Author: 21989
 * @CreateDate: 2020/7/31 10:10
 * @Version: 1.0
 */
public interface AttendanceService {

    /**
     * 分页查询缺勤表
     * @param paramMap
     * @return
     */
    PageBean<Attendance> queryPage(Map<String, Object> paramMap);

    /**
     * 判断是否签到
     * @param attendance
     * @return
     */
    boolean isAttendance(Attendance attendance);

    /**
     * 添加出勤签到
     * @param attendance
     * @return
     */
    int addAttendance(Attendance attendance);

    /**
     * 删除出勤
     * @param id
     * @return
     */
    int deleteAttendance(Integer id);
}
