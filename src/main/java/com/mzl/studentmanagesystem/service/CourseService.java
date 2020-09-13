package com.mzl.studentmanagesystem.service;

import com.mzl.studentmanagesystem.entity.Course;
import com.mzl.studentmanagesystem.entity.Teacher;
import com.mzl.studentmanagesystem.util.PageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   CourseService
 * @Description: 课程业务逻辑层
 * @Author: 21989
 * @CreateDate: 2020/7/30 14:43
 * @Version: 1.0
 */
public interface CourseService {

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    PageBean<Course> qureyPage(Map<String, Object> paramMap);

    /**
     * 查询所有的课程
     * @param ids
     * @return
     */
    List<Course> getCourById(List<Integer> ids);

    /**
     * 通过课程名查询课程id
     * @param name
     * @return
     */
    int findByName(String name);

    /**
     * 用课程名和时间查询课程
     * @param course
     * @return
     */
    Course queryCourseByNameAndDate(Course course);

    /**
     * 添加课程
     * @param course
     * @return
     */
    int addCourse(Course course);

    /**
     * 修改课程
     * @param course
     * @return
     */
    int editCourse(Course course);

    /**
     * 删除课程
     * @param ids
     * @return
     */
    int deleteCourse(List<Integer> ids);

    /**
     * 查询选课表是否关联到删除的课程
     * @param ids
     * @return
     */
    int isCourseInSelectedCourse(List<Integer> ids);

    /**
     * 用课程id查找教师id
     * @param id
     * @return
     */
    Integer findById(Integer id);

    /**
     * 判课程表是否存在关联删除的老师
     * @param ids
     * @return
     */
    int isTeacherInCourse(List<Integer> ids);

}
