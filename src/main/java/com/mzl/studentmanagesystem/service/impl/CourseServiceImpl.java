package com.mzl.studentmanagesystem.service.impl;

import com.mzl.studentmanagesystem.dao.CourseDao;
import com.mzl.studentmanagesystem.dao.SelectedCourseDao;
import com.mzl.studentmanagesystem.entity.Course;
import com.mzl.studentmanagesystem.entity.SelectedCourse;
import com.mzl.studentmanagesystem.entity.Student;
import com.mzl.studentmanagesystem.entity.Teacher;
import com.mzl.studentmanagesystem.service.CourseService;
import com.mzl.studentmanagesystem.util.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ClassName :   CourseServiceImpl
 * @Description: 课程业务逻辑实现类
 * @Author: 21989
 * @CreateDate: 2020/7/30 14:44
 * @Version: 1.0
 */
@Slf4j
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private SelectedCourseDao selectedCourseDao;

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    @Override
    public PageBean<Course> qureyPage(Map<String, Object> paramMap) {
        PageBean<Course> pageBean = new PageBean<Course>((Integer)paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex", startIndex);
        System.out.println("Course ParamMap:" + paramMap);
        List<Course> datas = courseDao.queryList(paramMap);
        System.out.println(datas);
        pageBean.setDatas(datas);

        //总记录数
        Integer totalsize = courseDao.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);

        System.out.println(pageBean);
        return pageBean;
    }

    /**
     * 查询所有的课程
     * @param ids
     * @return
     */
    @Override
    public List<Course> getCourById(List<Integer> ids) {
        return courseDao.getCourseById(ids);
    }

    /**
     * 通过课程名字查询课程id
     * @param name
     * @return
     */
    @Override
    public int findByName(String name) {
        return courseDao.findByName(name);
    }

    /**
     * 用课程名和时间查询课程
     * @param course
     * @return
     */
    @Override
    public Course queryCourseByNameAndDate(Course course) {
        return courseDao.qureyCourseByNameAndDate(course);
    }

    /**
     * 添加课程
     * @param course
     * @return
     */
    @Override
    public int addCourse(Course course) {
        return courseDao.addCourse(course);
    }

    /**
     * 修改课程
     * @param course
     * @return
     */
    @Override
    public int editCourse(Course course) {
        return courseDao.editCourse(course);
    }

    /**
     * 删除课程
     * @param ids
     * @return
     */
    @Override
    public int deleteCourse(List<Integer> ids) {
        return courseDao.deleteCourse(ids);
    }

    /**
     * 查询选课表是否关联到删除的课程
     * @param ids
     * @return
     */
    @Override
    public int isCourseInSelectedCourse(List<Integer> ids) {
        Iterator<Integer> iterator = ids.iterator();
        int num = 0;  //存在外键关联的课程
        while (iterator.hasNext()){
            Integer id = iterator.next();
            System.out.println(id);
            List<SelectedCourse> selectedCourseList = selectedCourseDao.isCourseInSelectedCourse(id);
            if (!selectedCourseList.isEmpty()){
                //不为空，加1
                num++;
                break;
            }
        }
        return num;
    }

    /**
     * 用课程id查找教师id
     * @param id
     * @return
     */
    @Override
    public Integer findById(Integer id) {
        return courseDao.findById(id);
    }

    /**
     * 判课程表是否存在关联删除的老师
     * @param ids
     * @return
     */
    @Override
    public int isTeacherInCourse(List<Integer> ids) {
        return courseDao.isTeacherInCourse(ids);
    }


}
