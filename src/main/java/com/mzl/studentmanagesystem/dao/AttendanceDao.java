package com.mzl.studentmanagesystem.dao;

import com.mzl.studentmanagesystem.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   AttendanceDao
 * @Description: 缺勤dao层
 * @Author: 21989
 * @CreateDate: 2020/7/31 10:13
 * @Version: 1.0
 */
@Mapper
public interface AttendanceDao {

    /**
     * 分页查询列表
     * @param paramMap
     * @return
     */
    List<Attendance> queryList(Map<String, Object> paramMap);

    /**
     * 查询总记录数
     * @param paramMap
     * @return
     */
    Integer queryCount(Map<String, Object> paramMap);

    /**
     * 判断是否签到
     * @param attendance
     * @return
     */
    Attendance isAttendance(Attendance attendance);

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
