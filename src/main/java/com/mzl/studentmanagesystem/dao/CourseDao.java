package com.mzl.studentmanagesystem.dao;

import com.mzl.studentmanagesystem.entity.Course;
import com.mzl.studentmanagesystem.entity.SelectedCourse;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   CourseDao
 * @Description: 课程dao
 * @Author: 21989
 * @CreateDate: 2020/7/30 14:41
 * @Version: 1.0
 */
@Mapper
public interface CourseDao {

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    List<Course> queryList(Map<String, Object> paramMap);

    /**
     * 总记录数
     * @param paramMap
     * @return
     */
    Integer queryCount(Map<String, Object> paramMap);

    /**
     * 给课程的选课数量加1
     * @param courseId
     * @return
     */
    int addStudentNum(Integer courseId);

    /**
     * 查询课程是否已满
     * @param courseId
     * @return
     */
    Course queryCourse(Integer courseId);

    /**
     * 课程的学生数量先减1
     * @param courseId
     * @return
     */
    int reduceStudentNum(Integer courseId);

    /**
     * 查询所有的课程
     * @param ids
     * @return
     */
    List<Course> getCourseById(List<Integer> ids);

    /**
     * 通过课程名字查询课程id
     * @param name
     * @return
     */
    int findByName(String name);

    /**
     * 用课程名和时间查询课程
     * @param course
     * @return
     */
    Course qureyCourseByNameAndDate(Course course);

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
