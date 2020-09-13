package com.mzl.studentmanagesystem.service;

import com.mzl.studentmanagesystem.entity.Teacher;
import com.mzl.studentmanagesystem.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   TeacherService
 * @Description: 教师业务逻辑
 * @Author: 21989
 * @CreateDate: 2020/7/30 8:51
 * @Version: 1.0
 */
public interface TeacherService {

    /**
     * 获取图片地址（教师和学生均可用）
     * @param tid
     * @return
     */
    Teacher findById(Integer tid);

    /**
     * 老师登录，使用用户名和密码
     * @param teacher
     * @return
     */
    Teacher findByTeacher(Teacher teacher);

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    PageBean<Teacher> queryPage(Map<String, Object> paramMap);

    /**
     * 修改教师
     * @param teacher
     * @return
     */
    int editTeacher(Teacher teacher);

    /**
     * 修改老师密码
     * @param teacher
     * @return
     */
    int editPswByTeacher(Teacher teacher);

    /**
     * 添加老师
     * @param teacher
     * @return
     */
    int addTeacher(Teacher teacher);

    /**
     * 删除教师
     * @param ids
     * @return
     */
    int deleteTeacher(List<Integer> ids);
}
