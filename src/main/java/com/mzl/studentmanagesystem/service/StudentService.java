package com.mzl.studentmanagesystem.service;

import com.mzl.studentmanagesystem.entity.Student;
import com.mzl.studentmanagesystem.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   StudentService
 * @Description: 学生业务逻辑接口
 * @Author: 21989
 * @CreateDate: 2020/7/29 11:03
 * @Version: 1.0
 */
public interface StudentService {

    /**
     * 查询学生（登录）
     * @param student
     * @return
     */
    Student findByStudent(Student student);

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    PageBean<Student> queryPage(Map<String, Object> paramMap);

    /**
     * 获取图片地址（教师和学生均可用）
     * @param sid
     * @return
     */
    Student findById(Integer sid);

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    int editStudent(Student student);

    /**
     * 通过
     * @param name
     * @return
     */
    int findByName(String name);

    /**
     * 修改密码
     * @param student
     * @return
     */
    int editPswByStudent(Student student);

    /**
     * 保存学生信息到数据库
     * @param student
     * @return
     */
    int addStudent(Student student);

    /**
     * 删除学生
     * @param ids
     * @return
     */
    int deleteStudent(List<Integer> ids);

    /**
     * 判断课程是否有关联的学生
     * @param id
     * @return
     */
    boolean isStudent(Integer id);
}
