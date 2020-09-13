package com.mzl.studentmanagesystem.controller;

import com.mzl.studentmanagesystem.entity.Clazz;
import com.mzl.studentmanagesystem.service.ClazzService;
import com.mzl.studentmanagesystem.service.StudentService;
import com.mzl.studentmanagesystem.util.AjaxResult;
import com.mzl.studentmanagesystem.util.Data;
import com.mzl.studentmanagesystem.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :   ClazzController
 * @Description: TODO
 * @Author: 21989
 * @CreateDate: 2020/7/30 0:00
 * @Version: 1.0
 */
@Controller
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private StudentService studentService;

    /**
     * 跳转到班级列表
     */
    @GetMapping("/clazz_list")
    public String classList(){
        return "clazz/clazzList";
    }

    /**
     * 自动已不需请求加载班级列表
     * @param page
     * @param rows
     * @param clazzName
     * @param from
     * @return
     */
    @PostMapping("/getClazzList")
    @ResponseBody
    public Object getClazzList(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "rows", defaultValue = "100")Integer rows, String clazzName, String from){
        System.out.println(page);
        System.out.println(clazzName);
        System.out.println(rows);
        System.out.println(from);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageno", page);
        paramMap.put("pagesize", rows);
        if (!StringUtils.isEmpty(clazzName)){
            paramMap.put("name", clazzName);
        }
        //分页查询
        PageBean<Clazz> pageBean = clazzService.queryPage(paramMap);
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
     * 添加班级
     * @param clazz
     * @return
     */
    @PostMapping("/addClazz")
    @ResponseBody
    public AjaxResult addClazz(Clazz clazz){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(clazz);
        int count = clazzService.addClazz(clazz);
        if (count > 0){
            ajaxResult.setSuccess(true);
            ajaxResult.setMessage("添加成功！");
        }else {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("添加失败！");
        }
        return ajaxResult;
    }

    /**
     * 修改班级
     * @param clazz
     * @return
     */
    @PostMapping("/editClazz")
    @ResponseBody
    public AjaxResult editClazz(Clazz clazz){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(clazz);
        int count = clazzService.editClazz(clazz);
        if (count > 0){
            ajaxResult.setSuccess(true);
            ajaxResult.setMessage("修改成功！");
        }else {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("修改失败！");
        }
        return ajaxResult;
    }

    /**
     * 删除班级
     * @param data
     * @return
     */
    @PostMapping("/deleteClazz")
    @ResponseBody
    public AjaxResult deleteClazz(Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            List<Integer> ids = data.getIds();
            System.out.println(ids);
            //判断课程是否有关联的学生
            for (Integer id : ids){
                if (!studentService.isStudent(id)){  //不为空，返回false
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("无法删除，班级下存在学生，请先删除学生！");
                    return ajaxResult;
                }
            }
            int count = clazzService.deleteClazz(data.getIds());
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("全部删除成功！");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("全部删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，删除失败！");
        }

        return ajaxResult;
    }


    

}
