package com.mzl.studentmanagesystem.dao;

import com.mzl.studentmanagesystem.entity.SelectedCourse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   SelectedCourseDao
 * @Description: 学生选课dao
 * @Author: 21989
 * @CreateDate: 2020/7/30 13:53
 * @Version: 1.0
 */
@Mapper
public interface SelectedCourseDao {

    /**
     * 分页查询列表
     * @param paramMap
     * @return
     */
    List<SelectedCourse> queryList(Map<String, Object> paramMap);

    /**
     * 总记录数
     * @param paramMap
     * @return
     */
    Integer queryCount(Map<String, Object> paramMap);

    /**
     * 查询是否存在已选的课程
     * @param selectedCourse
     * @return
     */
    SelectedCourse findBySelectedCourse(SelectedCourse selectedCourse);

    /**
     * 添加选课
     * @param selectedCourse
     */
    void addSelectedCourse(SelectedCourse selectedCourse);

    /**
     * 查询出课程的id
     * @param id
     * @return
     */
    SelectedCourse queryById(Integer id);

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
     * @param id
     * @return
     */
    List<SelectedCourse> isStudentId(Integer id);

    /**
     * 查询选课表是否关联到删除的课程
     * @param id
     * @return
     */
    List<SelectedCourse> isCourseInSelectedCourse(Integer id);
}
