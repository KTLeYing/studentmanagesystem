package com.mzl.studentmanagesystem.service;

import com.mzl.studentmanagesystem.entity.Leave;
import com.mzl.studentmanagesystem.util.PageBean;

import java.util.Map;

/**
 * @InterfaceName :   LeaveService
 * @Description: 请假业务逻辑
 * @Author: 21989
 * @CreateDate: 2020/7/31 21:40
 * @Version: 1.0
 */
public interface LeaveService {

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    PageBean<Leave> queryPage(Map<String, Object> paramMap);

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
