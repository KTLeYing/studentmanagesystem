package com.mzl.studentmanagesystem.dao;

import com.mzl.studentmanagesystem.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   TeacherDao
 * @Description: TODO
 * @Author: 21989
 * @CreateDate: 2020/7/30 8:56
 * @Version: 1.0
 */
@Mapper
public interface TeacherDao {

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
     * 分页查询教师每页内容
     * @param paramMap
     * @return
     */
    List<Teacher> queryList(Map<String, Object> paramMap);

    /**
     * 查询总记录数
     * @param paramMap
     * @return
     */
    Integer queryCount(Map<String, Object> paramMap);

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
