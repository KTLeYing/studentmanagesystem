package com.mzl.studentmanagesystem.dao;

import com.mzl.studentmanagesystem.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   StudentDao
 * @Description: 学生dao层
 * @Author: 21989
 * @CreateDate: 2020/7/29 11:08
 * @Version: 1.0
 */
@Mapper
public interface StudentDao {

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
    List<Student> queryList(Map<String, Object> paramMap);

    /**
     * 查询总记录数
     * @param paramMap
     * @return
     */
    Integer queryCount(Map<String, Object> paramMap);

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
     * 通过同学名字找到id
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
     * 删除学生（包含批量删除）
     * @param ids
     * @return
     */
    int deleteStudent(List<Integer> ids);

    /**
     * 判断课程是否有关联的学生
     * @param id
     * @return
     */
    List<Student> findByClazzId(Integer id);
}
