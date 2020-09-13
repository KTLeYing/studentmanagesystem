package com.mzl.studentmanagesystem.controller;

import com.mzl.studentmanagesystem.entity.Teacher;
import com.mzl.studentmanagesystem.service.CourseService;
import com.mzl.studentmanagesystem.service.TeacherService;
import com.mzl.studentmanagesystem.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName :   TeacherController
 * @Description: 教师控制器
 * @Author: 21989
 * @CreateDate: 2020/7/30 8:50
 * @Version: 1.0
 */
@Slf4j
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    /**
     * 跳转到教师页面
     * @return
     */
    @GetMapping("/teacher_list")
    public String teacherList(){
        return "teacher/teacherList";
    }

    /**
     * 异步加载教师列表
     * @param page
     * @param rows
     * @param teacherName
     * @param clazzid
     * @param from
     * @param session
     * @return
     */
    @PostMapping("/getTeacherList")
    @ResponseBody
    public Object getTeacherList(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "rows", defaultValue = "100")Integer rows, String teacherName, @RequestParam(value = "clazzid", defaultValue = "0")String clazzid, String from, HttpSession session){
        log.debug("debug");
        log.error("error");
        log.warn("warn");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageno", page);
        paramMap.put("pagesize", rows);
        if (!StringUtils.isEmpty(teacherName)){
            paramMap.put("username", teacherName);
        }
        if (!clazzid.equals("0")){
            paramMap.put("clazzid", clazzid);
        }

        //判断是老师还是学生
        Teacher teacher = (Teacher) session.getAttribute(Const.TEACHER);
        if (!StringUtils.isEmpty(teacher)){
            paramMap.put("teacherid", teacher.getId());
        }

        //分页查询
        PageBean<Teacher> pageBean = teacherService.queryPage(paramMap);
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
     * 修改老师
     * @param teacher
     * @return
     */
    @PostMapping("/editTeacher")
    @ResponseBody
    public AjaxResult editTeacher(Teacher teacher, @RequestParam("file")MultipartFile[] files){
        System.out.println(teacher);
        AjaxResult ajaxResult = new AjaxResult();
        //存放上传图片的文件夹
        File fileDir = UploadUtil.getImgDirFile();
        for (MultipartFile fileImg : files){
            String name = fileImg.getOriginalFilename();
            if (name.equals("")){
                break;
            }
            //拿到文件名
            String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
            System.out.println(extName);
            String uuidName = UUID.randomUUID().toString();
            System.out.println(uuidName);

            try {
                //构建真实的文件路径
                File newFile = new File(fileDir.getAbsolutePath() + File.separator + uuidName + extName);
                //上传图片--》绝对路径
                fileImg.transferTo(newFile);

                //查询原教师信息，用于获取教师图像路径，删除原图像
                Teacher byId = teacherService.findById(teacher.getId());
                File file = new File(fileDir.getAbsolutePath() + File.separator + byId.getPhoto());
                if (file != null){
                    //删除原头像
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            teacher.setPhoto(uuidName + extName);
        }
        //修改教师
        try {
            int count = teacherService.editTeacher(teacher);
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
            ajaxResult.setMessage("系统内部错误，修改失败！");
        }
        return ajaxResult;
    }

    /**
     * 添加老师
     * @param teacher
     * @return
     */
    @PostMapping("/addTeacher")
    @ResponseBody
    public AjaxResult addTeacher(@RequestParam("file")MultipartFile[] files, Teacher teacher){
        System.out.println(teacher);
        AjaxResult ajaxResult = new AjaxResult();
        //随机生成一个学号
        String sn = SnGenerateUtil.generateTeacherSn(teacher.getClazzId());
        System.out.println(sn);
        teacher.setSn(sn);

        //存放图片的文件夹
        File fileDir = UploadUtil.getImgDirFile();
        System.out.println(fileDir);
        //处理设置老师头像
        for (MultipartFile fileImg : files){
            System.out.println(fileImg);
            //获取文件扩展名
            String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
            System.out.println(extName);
            //随机生成一个头像文件名
            String uuidName = UUID.randomUUID().toString();
            System.out.println(uuidName);
            try {
                //构建头像存储的真实路径
                File newFile = new File(fileDir.getAbsolutePath() + File.separator + uuidName + extName);
                System.out.println(newFile);
                //上传图片,用绝地路径
                fileImg.transferTo(newFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //设置老师的头像
            teacher.setPhoto(uuidName + extName);
        }

        //添加老师信息
        try {
            int count = teacherService.addTeacher(teacher);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("添加成功！");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("添加失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，添加失败！");
        }
        return ajaxResult;
    }

    /**
     * 删除教师
     * @param
     * @return
     */
    @PostMapping("/deleteTeacher")
    @ResponseBody
    public AjaxResult deleteTeacher(Data data){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(data.getIds());
        //先判课程表是否存在关联删除的老师
        int num = courseService.isTeacherInCourse(data.getIds());
        if (num > 0){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("课程表中存在老师关联，删除失败！");
            return ajaxResult;
        }

        //不存在要删除的教师的关联，可以删除教师
        try {
            int count = teacherService.deleteTeacher(data.getIds());
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("删除教师成功！");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除教师失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，删除教师失败！");
        }
        return ajaxResult;
    }




}
