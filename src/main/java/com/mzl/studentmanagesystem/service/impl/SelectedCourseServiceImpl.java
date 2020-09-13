package com.mzl.studentmanagesystem.service.impl;

import com.mzl.studentmanagesystem.dao.CourseDao;
import com.mzl.studentmanagesystem.dao.SelectedCourseDao;
import com.mzl.studentmanagesystem.entity.Course;
import com.mzl.studentmanagesystem.entity.SelectedCourse;
import com.mzl.studentmanagesystem.service.SelectedCourseService;
import com.mzl.studentmanagesystem.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName :   SelectedCourseServiceImpl
 * @Description: 选课业务逻辑
 * @Author: 21989
 * @CreateDate: 2020/7/30 13:52
 * @Version: 1.0
 */
@Service
@Transactional
public class SelectedCourseServiceImpl implements SelectedCourseService {

    @Autowired
    private SelectedCourseDao selectedCourseDao;
    @Autowired
    private CourseDao courseDao;

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    @Override
    public PageBean<SelectedCourse> queryPage(Map<String, Object> paramMap) {
        PageBean<SelectedCourse> pageBean = new PageBean<SelectedCourse>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));
        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex", startIndex);
        System.out.println("SelectedCourse ParamMap：" + paramMap);
        //分页查询列表
        List<SelectedCourse> datas = selectedCourseDao.queryList(paramMap);
        pageBean.setDatas(datas);
        //总记录数
        Integer totalsize = selectedCourseDao.queryCount(paramMap);
        System.out.println(totalsize);
        pageBean.setTotalsize(totalsize);

        System.out.println(pageBean);
        return pageBean;
    }

    /**
     * 添加课程（选课）
     * @param selectedCourse
     * @return
     */
    @Override
    public int addSelectedCourse(SelectedCourse selectedCourse) {
        System.out.println(selectedCourse);
        //查询是否存在已选的课程
        SelectedCourse s = selectedCourseDao.findBySelectedCourse(selectedCourse);
        if (StringUtils.isEmpty(s)){
            //如果为空，可以添加选课
            //查询课程是否已满
            Course course = courseDao.queryCourse(selectedCourse.getCourseId());
            System.out.println(course);
            if (course.getMaxNum() == course.getSelectedNum()){//已满
                return 0;
            }else {//未满
                //给课程的选课学生数量加1
                int count = courseDao.addStudentNum(selectedCourse.getCourseId());
                System.out.println("update count:" + count);
                if (count == 1){
                    //添加选课
                    selectedCourseDao.addSelectedCourse(selectedCourse);
                    //选课成功
                    return count;
                }
            }
        }else {   //已经选择了这门课
            return 2;
        }
        return 3;
    }

    /**
     * 查询出课程的id
     * @return
     */
    @Override
    public SelectedCourse queryById(Integer id) {
        return selectedCourseDao.queryById(id);
    }

    /**
     * 课程的学生数量先减1
     * @param courseId
     * @return
     */
    @Override
    public int reduceStuentNum(Integer courseId) {
        return courseDao.reduceStudentNum(courseId);
    }

    /**
     * 删除选课信息
     * @param id
     * @return
     */
    @Override
    public int deleteSelectedCourse(Integer id) {
        return selectedCourseDao.deleteSelectedCourse(id);
    }

    /**
     * 通过学生id查询学生的所有选课
     * @param sid
     * @return
     */
    @Override
    public List<SelectedCourse> getAllBySid(int sid) {
        return selectedCourseDao.getAllBySid(sid);
    }

    /**
     * 判断是否存在课程关联学生
     * @param id
     * @return
     */
    @Override
    public boolean isStudentId(Integer id) {
        List<SelectedCourse> selectedCourseList = selectedCourseDao.isStudentId(id);
        if (selectedCourseList.isEmpty()){
            return true;
        }else {
            return false;
        }
    }



}
