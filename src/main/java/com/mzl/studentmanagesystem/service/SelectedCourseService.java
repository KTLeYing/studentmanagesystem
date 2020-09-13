package com.mzl.studentmanagesystem.service;

import com.mzl.studentmanagesystem.entity.SelectedCourse;
import com.mzl.studentmanagesystem.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   SelectedCourseService
 * @Description: 选课业务逻辑接口
 * @Author: 21989
 * @CreateDate: 2020/7/30 13:52
 * @Version: 1.0
 */
public interface SelectedCourseService {

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    PageBean<SelectedCourse> queryPage(Map<String, Object> paramMap);

    /**
     * 添加课程（选课）
     * @param selectedCourse
     * @return
     */
    int addSelectedCourse(SelectedCourse selectedCourse);

    /**
     * 查询出课程的id
     * @return
     */
    SelectedCourse queryById(Integer id);

    int reduceStuentNum(Integer courseId);

    /**
     * 删除选课信息
     * @param id
     * @return
     */
    int deleteSelectedCourse(Integer id);

    /**
     * 通过学生id查询学生的所有选课
     * @param sid
     * @return
     */
    List<SelectedCourse> getAllBySid(int sid);

    /**
     * 判断是否存在课程关联学生
     * @param next
     * @return
     */
    boolean isStudentId(Integer next);
}
