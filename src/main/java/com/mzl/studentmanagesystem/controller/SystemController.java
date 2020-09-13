package com.mzl.studentmanagesystem.controller;

import com.mzl.studentmanagesystem.entity.Admin;
import com.mzl.studentmanagesystem.entity.Student;
import com.mzl.studentmanagesystem.entity.Teacher;
import com.mzl.studentmanagesystem.service.AdminService;
import com.mzl.studentmanagesystem.service.StudentService;
import com.mzl.studentmanagesystem.service.TeacherService;
import com.mzl.studentmanagesystem.util.AjaxResult;
import com.mzl.studentmanagesystem.util.Const;
import com.mzl.studentmanagesystem.util.CpachaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @ClassName :   SystemController
 * @Description: 系统控制器
 * @Author: 21989
 * @CreateDate: 2020/7/29 11:02
 * @Version: 1.0
 */
@Slf4j
@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AdminService adminService;

    /**
     * 运行后跳转到登录页面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/checkCode")
    public void genterateCpacha(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "vl", defaultValue = "4", required = false) Integer vl, @RequestParam(value = "w", defaultValue = "110", required = false) Integer w, @RequestParam(value = "h", defaultValue = "39", required = false) Integer h){
        log.debug("debug");
        log.error("error");
        log.warn("warn");
        CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
        String generatorVcode = cpachaUtil.generatorVCode();
        System.out.println(generatorVcode);
        request.getSession().setAttribute(Const.CODE, generatorVcode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVcode, true);
        try {
            ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录表单提交 校验
     * @param username
     * @param password
     * @param code
     * @param type
     * @param session
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public AjaxResult submitlogin(String username, String password, String code, String type, HttpSession session){
        System.out.println(username);
        System.out.println(password);
        System.out.println(code);
        AjaxResult ajaxResult = new AjaxResult();

        if (StringUtils.isEmpty(username)){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("请填写密码");
            return ajaxResult;
        }

        if (StringUtils.isEmpty(password)){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("请填写密码");
            return ajaxResult;
        }

        if (StringUtils.isEmpty(code)){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("请填写验证码");
            return ajaxResult;
        }

        if (StringUtils.isEmpty(session.getAttribute(Const.CODE))){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("会话时间过长，请刷新");
            return ajaxResult;
        }else {
            if (!code.equalsIgnoreCase((String) session.getAttribute(Const.CODE))){
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("验证码错误");
                return ajaxResult;
            }
        }

        //数据库的校验
        switch (type){
            case "1":{  //管理员身份
                Admin admin = new Admin();
                admin.setPassword(password);
                admin.setUsername(username);
                Admin admin1 = adminService.findByAdmin(admin);
                System.out.println(admin1);
                if (StringUtils.isEmpty(admin1)){
                    //为空，登录失败
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("用户名或密码错误！");
                    return ajaxResult;
                }
                //登录成功
                ajaxResult.setSuccess(true);
                session.setAttribute(Const.ADMIN, admin1);
                session.setAttribute(Const.USERTYPE, "1");
                break;
            }
            case "2":{  //学生身份
                Student student = new Student();
                student.setPassword(password);
                student.setUsername(username);
                Student student1 = studentService.findByStudent(student);
                System.out.println(student1);
                if (StringUtils.isEmpty(student1)){
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("用户名或密码错误");
                    return ajaxResult;
                }
                ajaxResult.setSuccess(true);
                session.setAttribute(Const.STUDENT, student1);
                session.setAttribute(Const.USERTYPE, "2");
                break;
            }
            case "3":{   //教师身份
                Teacher teacher = new Teacher();
                teacher.setPassword(password);
                teacher.setUsername(username);
                //老师登录，使用用户名和密码
                Teacher teacher1 = teacherService.findByTeacher(teacher);
                if (StringUtils.isEmpty(teacher1)){
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("用户名或密码错误");
                }else {
                    ajaxResult.setSuccess(true);
                    session.setAttribute(Const.TEACHER, teacher1);
                    session.setAttribute(Const.USERTYPE, "3");
                }
                break;
            }
        }
        return ajaxResult;
    }

    /**
     * 跳转到后台首页（管理员、教师和学生都可以用）
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "system/index";
    }

    /**
     * 退出登录（所有的session无效）
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        //设置所有的session无效,它实际上调用的是session对象中的destroy方法
        session.invalidate();
        return "login";
    }

    /**
     * 获取图片地址（教师和学生均可用）
     * @param sid
     * @param tid
     * @return
     */
    @PostMapping("/getPhoto")
    @ResponseBody
    public AjaxResult getPhoto(@RequestParam(value = "sid", defaultValue = "0")Integer sid, @RequestParam(value = "tid", defaultValue = "0")Integer tid){
        AjaxResult ajaxResult = new AjaxResult();
        //学生的
        if (sid != 0){
            Student student = studentService.findById(sid);
            System.out.println(student);
            ajaxResult.setImgurl(student.getPhoto());
            return ajaxResult;
        }
        //教师的
        if (tid != 0){
            Teacher teacher = teacherService.findById(tid);
            System.out.println(teacher);
            ajaxResult.setImgurl(teacher.getPhoto());
            return ajaxResult;
        }

        return ajaxResult;
    }

    /**
     * 跳转到修改密码页面
     * @return
     */
    @GetMapping("/personalView")
    public String personalView(){
        return "system/personalView";
    }

    @PostMapping("/editPassword")
    @ResponseBody
    public AjaxResult editPassword(String password, HttpSession session, String newpassword){
        AjaxResult ajaxResult = new AjaxResult();
        String userType = (String)session.getAttribute(Const.USERTYPE);
        if (userType.equals("1")){ //管理员
            Admin admin = (Admin) session.getAttribute(Const.ADMIN);
            if (!password.equals(admin.getPassword())){
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("原密码错误！");
            }else {
                admin.setPassword(newpassword);
                //修改密码
                int count = adminService.editPswByAdmin(admin);
                if (count > 0){
                    ajaxResult.setSuccess(true);
                    ajaxResult.setMessage("修改密码成功");
                }else {
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("修改密码失败！");
                }
            }
            return ajaxResult;
        }else if(userType.equals("2")){  //学生
            Student student = (Student) session.getAttribute(Const.STUDENT);
            if (!password.equals(student.getPassword())){  //原密码错误
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("原密码错误！");
            }else {  //原密码错误
                student.setPassword(newpassword);
                //修改密码
                int count = studentService.editPswByStudent(student);
                if (count > 0){
                    ajaxResult.setSuccess(true);
                    ajaxResult.setMessage("修改密码成功！");
                }else {
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("修改密码失败");
                }
            }
            return ajaxResult;
        }else {   //教师
            //获取原登录者的信息
            Teacher teacher = (Teacher) session.getAttribute(Const.TEACHER);
            if (!teacher.getPassword().equals(password)){
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("原密码错误！");
            }else {
                teacher.setPassword(newpassword);
                //修改老师密码
                int count = teacherService.editPswByTeacher(teacher);
                if (count > 0){
                    ajaxResult.setSuccess(true);
                    ajaxResult.setMessage("修改成功！");
                }else {
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("修改失败！");
                }
            }
            return ajaxResult;
        }
    }






}
