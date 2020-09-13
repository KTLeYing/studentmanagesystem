package com.mzl.studentmanagesystem.controller;

import com.mzl.studentmanagesystem.entity.Student;
import com.mzl.studentmanagesystem.service.SelectedCourseService;
import com.mzl.studentmanagesystem.service.StudentService;
import com.mzl.studentmanagesystem.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName :   StudentController
 * @Description: 学生实体类
 * @Author: 21989
 * @CreateDate: 2020/7/29 17:06
 * @Version: 1.0
 */
@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private SelectedCourseService selectedCourseService;

    /**
     * 跳转到学生列表页面
     * @return
     */
    @GetMapping("/student_list")
    public String studentList(){
        return "student/studentList";
    }

    /**
     * 异步请求加载学生列表
     * @param page
     * @param rows
     * @return
     */
    @PostMapping("/getStudentList")
    @ResponseBody
    public Object getStudentList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "100")Integer rows, String studentName, @RequestParam(value = "clazzid", defaultValue = "0")String clazzid, String from, HttpSession session){
        System.out.println(page);
        System.out.println(rows);
        System.out.println(studentName);
        System.out.println(clazzid);
        System.out.println(from);
        Map<String, Object> paramMap = new HashMap<>();
        //查询条件的封装，用map
        paramMap.put("pageno", page);
        paramMap.put("pagesize", rows);
        if (!StringUtils.isEmpty(studentName)){
            paramMap.put("username", studentName);
        }
        if (!clazzid.equals("0")){
            paramMap.put("clazzid", clazzid);
        }
        //判断老师还是学生权限
        Student student = (Student) session.getAttribute(Const.STUDENT);
        if (!StringUtils.isEmpty(student)){
            //是学生权限，只能查询自己的信息
            paramMap.put("studentid", student.getId());
        }

        //分页查询
        PageBean<Student> pagBean = studentService.queryPage(paramMap);
        if (!StringUtils.isEmpty(from) && from.equals("combox")){
            return pagBean.getDatas();
        }
        else {
            //把结果封装在map中，返回给前端，回调函数的data就是map的result来的
            Map<String, Object> result = new HashMap<>();
            result.put("total", pagBean.getTotalsize());
            result.put("rows", pagBean.getDatas());
            return result;
        }
    }

    /**
     * 编辑学生信息
     * @param files
     * @param student
     * @return
     */
    @PostMapping("/editStudent")
    @ResponseBody
    public AjaxResult editStudent(@RequestParam("file")MultipartFile[] files, Student student){
        AjaxResult ajaxResult = new AjaxResult();
        //存放上传图片的文件夹
        File fileDir = UploadUtil.getImgDirFile();

        for (MultipartFile fileImg : files){
            String name = fileImg.getOriginalFilename();
            if (name.equals("")){
                break;
            }
            //获取扩展名（.jpg）
            String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
            //获取全局唯一标识符（图片名）
            String uuidName = UUID.randomUUID().toString();
            try {
                //构建真实的文件路径
                File newFile = new File(fileDir.getAbsolutePath() + File.separator + uuidName + extName);
                System.out.println("yyyy");
                System.out.println(fileDir.getAbsolutePath() + File.separator + uuidName + extName);
                System.out.println(newFile);
                //上传图片，绝对路径
                fileImg.transferTo(newFile);

                //用户查询以前的图片，并删除
                Student byId= studentService.findById(student.getId());
                System.out.println(byId);
                File file = new File(fileDir.getAbsolutePath() + File.separator + byId.getPhoto());
                if (file != null){
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //设置获取的全局唯一标识名的（图片名）
            System.out.println("kkk");
            System.out.println(uuidName);
            System.out.println(extName);
            student.setPhoto(uuidName + extName);
        }

        try {
            //修改学生信息
            System.out.println(student);
            int count = studentService.editStudent(student);
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
            ajaxResult.setMessage("修改失败");
        }

        System.out.println("ajaxResult：" + ajaxResult);
        return ajaxResult;
    }

    /**
     * 添加学生
     * @param files
     * @param student
     * @return
     * @throws IOException
     */
    @PostMapping("/addStudent")
    @ResponseBody
    public AjaxResult addStudent(@RequestParam("file")MultipartFile[] files, Student student ) throws IOException {
        AjaxResult ajaxResult = new AjaxResult();
        //随机生成一个学号
        student.setSn(SnGenerateUtil.generateSn(student.getClazzId()));
        //存放上传图片的文件夹
        File fileDir = UploadUtil.getImgDirFile();
        System.out.println(fileDir);
        //处理设置学生的头像
        for (MultipartFile fileImg : files) {
            System.out.println(files);
            //拿到文件扩展名
            String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
            System.out.println(extName);
            String uuidName = UUID.randomUUID().toString();
            try {
                //构建真实的文件路径
                File newFile = new File(fileDir.getAbsolutePath() + File.separator + uuidName + extName);  //获取文件的绝对路径
                //上传图片到---》绝对路径
                fileImg.transferTo(newFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            student.setPhoto(uuidName + extName);
        }
        //保存学生信息到数据库
        try {
            int count = studentService.addStudent(student);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("保存成功");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，保存失败！");
        }
        return ajaxResult;
    }

    /**
     * 删除学生（包含批量删除）
     * @param data
     * @return
     */
    @PostMapping("/deleteStudent")
    @ResponseBody
    public AjaxResult deleteStudent(Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            List<Integer> ids = data.getIds();
            Iterator<Integer> iterator = ids.iterator();
            while (iterator.hasNext()){   //循环迭代student的id
                //判断是否存在课程关联学生
                if (!selectedCourseService.isStudentId(iterator.next())){   //存在学生关联的课程不为空·
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("无法删除，存在课程关联学生，清闲");
                    return ajaxResult;
                }
            }
            //删除头像
            File fileDir = UploadUtil.getImgDirFile();
            for (Integer id : ids){
                Student byId = studentService.findById(id);
                if (!byId.getPhoto().isEmpty()){
                    File file = new File(fileDir.getAbsolutePath() +fileDir.getAbsolutePath() + File.separator + byId.getPhoto());
                    if (file != null){
                        //根据路径删除文件中的头像
                        file.delete();
                    }
                }
            }
            //删除学生
            int count = studentService.deleteStudent(ids);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("全部删除成功");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败");
        }
        return ajaxResult;
    }





}
