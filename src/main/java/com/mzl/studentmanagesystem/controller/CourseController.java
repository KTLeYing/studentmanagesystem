package com.mzl.studentmanagesystem.controller;

import com.mzl.studentmanagesystem.entity.Course;
import com.mzl.studentmanagesystem.entity.Teacher;
import com.mzl.studentmanagesystem.service.CourseService;
import com.mzl.studentmanagesystem.util.AjaxResult;
import com.mzl.studentmanagesystem.util.Const;
import com.mzl.studentmanagesystem.util.Data;
import com.mzl.studentmanagesystem.util.PageBean;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName :   CourseController
 * @Description: 课程控制器
 * @Author: 21989
 * @CreateDate: 2020/7/30 14:43
 * @Version: 1.0
 */
@Slf4j
@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 课程列表
     * @return
     */
    @GetMapping("/course_list")
    public String courseList(){
        return "course/courseList";
    }

    /**
     * 异步加载课程信息
     * @return
     */
    @PostMapping("/getCourseList")
    @ResponseBody
    public Object getCourseList(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "rows", defaultValue = "100")Integer rows, String name, @RequestParam(value = "teacherid", defaultValue = "0")String teacherid, String from){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageno", page);
        paramMap.put("pagesize", rows);
        if (!StringUtils.isEmpty(name)){
            paramMap.put("name", name);
        }
        if (!teacherid.equals("0")){
            paramMap.put("teacherId", teacherid);
        }

        //分页查询
        PageBean<Course> pageBean = courseService.qureyPage(paramMap);
        System.out.println("Course PageBean:" + pageBean);
        if(!StringUtils.isEmpty(from)){
            return pageBean.getDatas();
        }else {
            Map<String, Object> result = new HashMap<>();
            result.put("total", pageBean.getTotalsize());
            result.put("rows", pageBean.getDatas());

            System.out.println(result);
            return result;
        }
    }

    /**
     * 添加课程
     * @param course
     * @return
     */
    @PostMapping("/addCourse")
    @ResponseBody
    public AjaxResult addCourse(Course course){
        System.out.println(course);
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //用课程名和时间查询课程
            Course course1 = courseService.queryCourseByNameAndDate(course);
            if (!StringUtils.isEmpty(course1)){ //不为空，已存在
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("添加失败，此课程已存在！");
            }else {  //为空，可以添加
                int count = courseService.addCourse(course);
                if (count > 0){
                    ajaxResult.setSuccess(true);
                    ajaxResult.setMessage("添加成功！");
                }else {
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("添加失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，添加失败！");
        }
        return ajaxResult;
    }

    /**
     * 修改课程
     * @param course
     * @return
     */
    @PostMapping("/editCourse")
    @ResponseBody
    public AjaxResult editCourse(Course course){
        System.out.println(course);
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = courseService.editCourse(course);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("修改成功！");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("修改失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误！修改失败！");
        }
        return ajaxResult;
    }

    /**
     * 删除课程
     * @param data
     * @return
     */
    @PostMapping("/deleteCourse")
    @ResponseBody
    public AjaxResult deleteCourse(Data data, HttpSession session){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(data);
        Teacher teacher = (Teacher) session.getAttribute(Const.TEACHER);
        //判断是老师权限还是管理员权限
        if (!StringUtils.isEmpty(teacher)){
            //为老师权限，只能删除自己的课程
            for (Integer id : data.getIds()) {
                System.out.println("id:" + id);
                //用课程id查找教师id
                Integer tid = courseService.findById(id);
                System.out.println(teacher.getId());
                System.out.println(tid);
                if (tid != teacher.getId()){
                    System.out.println("iii");
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("只能删除自己的课程！");
                    System.out.println(ajaxResult);
                    System.out.println("yyy");
                    return ajaxResult;
                }
            }
        }

        //删除中不存在别的老师的课程
        try {
            //查询选课表是否关联到删除的课程
            int num = courseService.isCourseInSelectedCourse(data.getIds());
            System.out.println("num:" + num);
            if (num > 0){  //选课表存在关联的课程，不能删除
                System.out.println("ooo");
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("选课表存在课程关联，不能删除！");
            }else {  //选课表不存在关联的课程，能删除
                System.out.println("ppp");
                int count = courseService.deleteCourse(data.getIds());
                if (count > 0){
                    ajaxResult.setSuccess(true);
                    ajaxResult.setMessage("删除成功！");
                }else {
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("删除失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，删除失败！");
        }
        System.out.println(ajaxResult);
        return ajaxResult;
    }




}
