package com.mzl.studentmanagesystem.controller;

import com.mzl.studentmanagesystem.entity.Attendance;
import com.mzl.studentmanagesystem.entity.Course;
import com.mzl.studentmanagesystem.entity.SelectedCourse;
import com.mzl.studentmanagesystem.entity.Student;
import com.mzl.studentmanagesystem.service.AttendanceService;
import com.mzl.studentmanagesystem.service.CourseService;
import com.mzl.studentmanagesystem.service.SelectedCourseService;
import com.mzl.studentmanagesystem.util.AjaxResult;
import com.mzl.studentmanagesystem.util.Const;
import com.mzl.studentmanagesystem.util.DateFormatUtil;
import com.mzl.studentmanagesystem.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName :   AttendanceController
 * @Description: 缺勤控制器
 * @Author: 21989
 * @CreateDate: 2020/7/31 10:08
 * @Version: 1.0
 */
@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private SelectedCourseService selectedCourseService;
    @Autowired
    private CourseService courseService;

    /**
     * 跳转到缺勤页面
     * @return
     */
    @GetMapping("/attendance_list")
    public String attendanceList(){
        return "attendance/attendanceList";
    }

    /**
     * 查询缺勤列表
     * @param page
     * @param rows
     * @param studentid
     * @param from
     * @param session
     * @return
     */
    @PostMapping("/getAttendanceList")
    @ResponseBody
    public Object getAttendanceList(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "rows", defaultValue = "100")Integer rows, @RequestParam(value = "studentid", defaultValue = "0")String studentid, @RequestParam(value = "courseid", defaultValue = "0")String courseid, String date, String type, String from, HttpSession session){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageno", page);
        paramMap.put("pagesize", rows);
        if (!studentid.equals("0")){
            paramMap.put("studentid", studentid);
        }
        if (!courseid.equals("0")){
            paramMap.put("courseid", courseid);
        }
        if (!StringUtils.isEmpty(type)){
            paramMap.put("type", type);
        }
        if (!StringUtils.isEmpty(date)){
            paramMap.put("date", date);
        }

        //判断是老师还是学生权限
        Student student = (Student) session.getAttribute(Const.STUDENT);
        if(!StringUtils.isEmpty(student)){
            //学生权限，只能查询自己的信息
            paramMap.put("studentId", student.getId());
        }
        //分页查询缺勤表
        PageBean<Attendance> pageBean = attendanceService.queryPage(paramMap);

        if(!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else {
            Map<String, Object> result = new HashMap<>();
            result.put("total", pageBean.getTotalsize());
            result.put("rows", pageBean.getDatas());
            return result;
        }
    }

    /**
     * 通过学生id，查询学生的所有选课
     * @param studentid
     * @return
     */
    @PostMapping("/getStudentSelectedCourseList")
    @ResponseBody
    public Object getStudentSelectedCourseList(@RequestParam(value = "studentid", defaultValue = "0")String studentid){
        System.out.println(studentid);
        //通过学生id查询学生的所有选课
        List<SelectedCourse> selectedCourseList = selectedCourseService.getAllBySid(Integer.parseInt(studentid));
        System.out.println(selectedCourseList);
        //通过选课列表获取所有的课程id
        List<Integer> ids = new ArrayList<>();
        for (SelectedCourse selectedCourse : selectedCourseList){
            ids.add(selectedCourse.getCourseId());
        }
        System.out.println("ids:" + ids);
        //查询所有的课程
        List<Course> courseList = courseService.getCourById(ids);
        System.out.println(courseList);
        return courseList;
    }

    /**
     * 添加出勤(打卡签到)
     * @return
     */
    @PostMapping("/addAttendance")
    @ResponseBody
    public AjaxResult addAttendance(Attendance attendance){
        System.out.println(attendance);
        AjaxResult ajaxResult = new AjaxResult();
        attendance.setDate(DateFormatUtil.getFormatDate(new Date(), "yyyy-MM-dd"));
        //判断是否签到
        if (attendanceService.isAttendance(attendance)){//已经签到
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("已签到，请勿重复签到！");
        }else {//未签到
            //添加出勤签到
            int count = attendanceService.addAttendance(attendance);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("签到成功！");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("系统错误，请重新签到！");
            }
        }
        return ajaxResult;
    }

    /**
     * 删除出勤
     * @param id
     * @return
     */
    @PostMapping("/deleteAttendance")
    @ResponseBody
    public AjaxResult deleteAttendance(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = attendanceService.deleteAttendance(id);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("删除成功！");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，删除失败！");
        }
        return ajaxResult;
    }








}
