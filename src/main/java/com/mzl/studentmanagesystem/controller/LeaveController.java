package com.mzl.studentmanagesystem.controller;

import com.mzl.studentmanagesystem.entity.Leave;
import com.mzl.studentmanagesystem.entity.Student;
import com.mzl.studentmanagesystem.service.LeaveService;
import com.mzl.studentmanagesystem.util.AjaxResult;
import com.mzl.studentmanagesystem.util.PageBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName :   LeaveController
 * @Description: 请假控制器
 * @Author: 21989
 * @CreateDate: 2020/7/31 21:39
 * @Version: 1.0
 */
@Controller
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/leave_list")
    public String leaveList(){
        return "leave/leaveList";
    }

    /**
     * 分页查询请假列表
     * @param page
     * @param rows
     * @param studentid
     * @param from
     * @param session
     * @return
     */
    @PostMapping("/getLeaveList")
    @ResponseBody
    public Object getLeaveList(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "rows", defaultValue = "100")Integer rows, @RequestParam(value = "studentid", defaultValue = "0")String studentid, String from, HttpSession session){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageno", page);
        paramMap.put("pagesize", rows);
        if (!studentid.equals("0")){
            paramMap.put("studentId", studentid);
        }
        //判断权限
        Student student = (Student) session.getAttribute("student");
        if (!StringUtils.isEmpty(student)){
            paramMap.put("studentid", student.getId());
        }
        //分页查询
        PageBean<Leave> pageBean = leaveService.queryPage(paramMap);
        if (!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else {
            Map<String, Object> result = new HashMap<>();
            result.put("total", pageBean.getTotalsize());
            result.put("rows", pageBean.getDatas());
            return result;
        }
    }

    /**
     * 添加请假
     * @param leave
     * @return
     */
    @PostMapping("/addLeave")
    @ResponseBody
    public AjaxResult addLeave(Leave leave){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(leave);
        try {
            int count = leaveService.addLeave(leave);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("添加成功");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统错异常，请重试");
        }
        return ajaxResult;
    }

    /**
     * 修改请假
     * @param leave
     * @return
     */
    @PostMapping("/editLeave")
    @ResponseBody
    public AjaxResult editLeave(Leave leave){
        System.out.println(leave);
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = leaveService.editLeave(leave);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("修改成功");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系院内部错误，请重试");
        }
        return ajaxResult;
    }

    /**
     * 删除请假
     * @return
     */
    @PostMapping("/deleteLeave")
    @ResponseBody
    public AjaxResult deleteLeave(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = leaveService.deleteLeave(id);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("删除成功");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，请重试");
        }
        return ajaxResult;
    }

    /**
     * 检查审核学生请假
     * @param leave
     * @return
     */
    @PostMapping("/checkLeave")
    @ResponseBody
    public AjaxResult checkLeave(Leave leave){
        System.out.println(leave);
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = leaveService.checkLeave(leave);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("审核成功！");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("审核失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，审核失败！");
        }
        return ajaxResult;
    }




}
