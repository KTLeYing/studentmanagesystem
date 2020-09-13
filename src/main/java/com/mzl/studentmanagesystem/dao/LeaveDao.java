package com.mzl.studentmanagesystem.dao;

import com.mzl.studentmanagesystem.entity.Leave;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   LeaveDao
 * @Description: 请假dao层
 * @Author: 21989
 * @CreateDate: 2020/7/31 21:42
 * @Version: 1.0
 */
@Mapper
public interface LeaveDao {

    /**
     * 分页查询列表
     * @param paramMap
     * @return
     */
    List<Leave> queryList(Map<String, Object> paramMap);

    /**
     * 查询总记录数
     * @param paramMap
     * @return
     */
    Integer queryCount(Map<String, Object> paramMap);

    /**
     * 添加请假
     * @param leave
     * @return
     */
    int addLeave(Leave leave);

    /**
     * 修改请假
     * @param leave
     * @return
     */
    int editLeave(Leave leave);

    /**
     * 删除请假
     * @param id
     * @return
     */
    int deleteLeave(Integer id);

    /**
     * 检查审核学生请假
     * @param leave
     * @return
     */
    int checkLeave(Leave leave);
}
