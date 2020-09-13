package com.mzl.studentmanagesystem.controller;

import com.mzl.studentmanagesystem.entity.Score;
import com.mzl.studentmanagesystem.entity.ScoreStats;
import com.mzl.studentmanagesystem.entity.Student;
import com.mzl.studentmanagesystem.service.CourseService;
import com.mzl.studentmanagesystem.service.ScoreService;
import com.mzl.studentmanagesystem.service.StudentService;
import com.mzl.studentmanagesystem.util.AjaxResult;
import com.mzl.studentmanagesystem.util.Const;
import com.mzl.studentmanagesystem.util.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.multi.MultiFileChooserUI;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.MulticastChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :   ScoreController
 * @Description: 成绩控制器
 * @Author: mzl
 * @CreateDate: 2020/8/3 12:58
 * @Version: 1.0
 */
@Slf4j
@Controller
@RequestMapping("/score")
public class ScoreController {

    //注入依赖
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;

    /**
     * 跳转到成绩列表
     * @return
     */
    @GetMapping("/score_list")
    public String scoreList(){
        return "score/scoreList";
    }

    /**
     * 异步加载成绩列表
     * @param page
     * @param rows
     * @param studentid
     * @param courseid
     * @param from
     * @param session
     * @return
     */
    @PostMapping("/getScoreList")
    @ResponseBody
    public Object getScoreList(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "rows", defaultValue = "100")Integer rows, @RequestParam(value = "studentid", defaultValue = "0")String studentid, @RequestParam(value = "courseid", defaultValue = "0")String courseid, String from, HttpSession session){
        //封装参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageno", page);
        paramMap.put("pagesize", rows);
        if (!studentid.equals("0")){
            paramMap.put("studentid", studentid);
        }
        if (!courseid.equals("0")){
            paramMap.put("courseid", courseid);
        }
        //判断是学生还是老师权限
        Student student = (Student) session.getAttribute(Const.STUDENT);
        if(!StringUtils.isEmpty(student)){
            //学生权限只能查询自己的信息
            paramMap.put("studentid", student.getId());
        }

        //查询学生成绩列表
        PageBean<Score> pageBean = scoreService.queryPage(paramMap);

        if (!StringUtils.isEmpty(from)){
            return pageBean.getDatas();
        }else {
            Map<String, Object> result = new HashMap<>();
            result.put("total", pageBean.getTotalsize());
            result.put("rows", pageBean.getDatas());
            return result;
        }
    }

    /**
     * 导入Excel表，并把数据存入数据库
     * @param importScore
     * @param response
     */
    @PostMapping("/importScore")
    @ResponseBody
    public void importScore(@RequestParam("importScore")MultipartFile importScore, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        try {
            InputStream inputStream = importScore.getInputStream();
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
            //用来标记添加进去数据库的记录数
            int count = 0;
            String errorMsg = "";
            for (int rowNum = 0; rowNum <= sheetAt.getLastRowNum(); rowNum++) {
                System.out.println("oooo");
                XSSFRow row = sheetAt.getRow(rowNum);//获取第rowNum行
                //第0列为学生姓名
                System.out.println("iii");
                XSSFCell cell = row.getCell(0);  //获取第rowNum行的第0列。即坐标（rowNum, 0）
                //设置cell类型，每一次获取cellValues都要另外重新设置一次cell类型
                cell.setCellType(Cell.CELL_TYPE_STRING);
                System.out.println(cell.getStringCellValue());
                System.out.println("ppp");
                if (cell == null){
                    errorMsg += "第" + rowNum + "行学生缺失！\n";
                    continue;
                }
                //第1列为课程名
                cell = row.getCell(1);
                //设置cell类型，每一次获取cellValues都要另外重新设置一次cell类型
                cell.setCellType(Cell.CELL_TYPE_STRING);
                System.out.println(cell.getStringCellValue());
                if (cell == null){
                    errorMsg += "第" + rowNum + "行课程缺失！\n";
                    continue;
                }
                System.out.println("kkkk");
                //第2列为分数
                cell = row.getCell(2);
                if (cell == null){
                    errorMsg += "第" + rowNum + "行成绩缺失！\n";
                    continue;
                }
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                double scoreValue = cell.getNumericCellValue();
                System.out.println(scoreValue);
                //第3列为remark
                cell = row.getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String remark = null;
                if (cell != null){
                    remark = cell.getStringCellValue();
                }
                System.out.println(remark);
                //将学生课程转换为id，存入数据库
                //1.首先获取对应的id
                cell = row.getCell(0);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                int studentId =  studentService.findByName(cell.getStringCellValue());
                cell = row.getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                int courseId = courseService.findByName(cell.getStringCellValue());
                //2.判断是否已存在数据库中
                Score score = new Score();
                score.setCourseId(courseId);
                score.setStudentId(studentId);
                score.setScore(scoreValue);
                score.setRemark(remark);
                if (!scoreService.isScore(score)){
                    //3.为空，则可以添加进去
                    int num = scoreService.addScore(score);
                    if(num > 0){
                        count++;
                    }
                }else {
                    errorMsg += "第" + rowNum + "行已录入，不重复录入！\n";
                }
            }
            errorMsg += "成功录入" + count + "条成绩信息！";
            response.getWriter().write("<div id = 'message'>" + errorMsg + "</div>");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("<div id = 'message'>上传错误</div>");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 导出Excel表格
     * @param response
     * @param score
     * @param session
     */
    @RequestMapping("/exportScore")
    @ResponseBody
    public void  exportScore(HttpServletResponse response, Score score, HttpSession session){
        //获取用户登录类型
        Student student = (Student) session.getAttribute(Const.STUDENT);
        if(!StringUtils.isEmpty(student)){
            //如果是学生，只能导出自己的成绩
            score.setStudentId(student.getId());
        }
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("score_list_sid_" + score.getStudentId() + ".xls", "UTF-8"));
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/octet-stream");
            //创建服务器输出流对象，用于下载导出表格，从浏览器中下载导出的
            ServletOutputStream outputStream = response.getOutputStream();
            //获取所有成绩列表
            List<Score> scoreList = scoreService.getAll(score);
            System.out.println(scoreList);
            //开始生产导出Excel
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            XSSFSheet createSheet = xssfWorkbook.createSheet("成绩列表");
            //创建一行，从0下标开始，index索引来的
            XSSFRow createRow = createSheet.createRow(0);
            //给该行的对应的列，表格设置对应的值，也是index从0开始的
            createRow.createCell(0).setCellValue("学生");
            createRow.createCell(1).setCellValue("课程");
            createRow.createCell(2).setCellValue("成绩");
            createRow.createCell(3).setCellValue("备注");
            //实现将数据装入到Excel文件中
            int row = 1;    //表格的行数，Index索引那种从0开始
            for (Score s : scoreList){
                createRow = createSheet.createRow(row++);  //创建完该行再加1，后++的
                createRow.createCell(0).setCellValue(s.getStudentName());
                createRow.createCell(1).setCellValue(s.getCourseName());
                createRow.createCell(2).setCellValue(s.getScore());
                createRow.createCell(3).setCellValue(s.getRemark());
            }
            xssfWorkbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("<div id = 'message'>上传错误</div>");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 添加成绩
     * @param score
     * @return
     */
    @PostMapping("/addScore")
    @ResponseBody
    public AjaxResult addScore(Score score){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(score);
        //查询某个学生的某门课程是否存在
        Score score1 = scoreService.findByStuIdAndCouId(score);
        if (!StringUtils.isEmpty(score1)){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("成绩记录已存在，添加失败！");
            return ajaxResult;
        }

        try {
            int count = scoreService.addScore(score);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("成绩添加成功！");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("成绩添加失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，添加失败！");
        }
        return ajaxResult;
    }

    /**
     * 修改成绩
     * @param score
     * @return
     */
    @PostMapping("/editScore")
    @ResponseBody
    public AjaxResult editScore(Score score){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(score);
        try {
            int count = scoreService.editScore(score);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("修改成绩成功！");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("修改成绩失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，修改成功失败！");
        }
        return ajaxResult;
    }

    /**
     * 删除成绩
     * @param id
     * @return
     */
    @PostMapping("/deleteScore")
    @ResponseBody
    public AjaxResult deleteScore(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = scoreService.deleteScore(id);
            if (count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("删除成绩成功！");
            }else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除成绩失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部错误，删除成绩失败！");
        }
        return ajaxResult;
    }

    /**
     * 跳转到成绩统计页面
     * @return
     */
    @GetMapping("/scoreStats")
    public String scoreStats(){
        return "score/scoreStats";
    }

    /**
     * 获取课程统计图数据
     * @param courseid
     * @param searchType
     * @return
     */
    @GetMapping("/getScoreStatsList")
    @ResponseBody
    public Object getScoreStatsList(@RequestParam(value = "courseid", defaultValue = "0")Integer courseid, String searchType){
        //求和函数和平均值的统计
        if (searchType.equals("avg")){
            ScoreStats scoreStats = scoreService.getAvgStats(courseid);
            System.out.println(scoreStats);

            List<Double> scoreList = new ArrayList<>();
            scoreList.add(scoreStats.getMax_score());
            scoreList.add(scoreStats.getMin_score());
            scoreList.add(scoreStats.getAvg_score());

            List<String> avgStringList = new ArrayList<>();
            avgStringList.add("最高分");
            avgStringList.add("最低分");
            avgStringList.add("平均分");

            Map<String, Object> retMap = new HashMap<>();
            retMap.put("courseName", scoreStats.getCourseName());
            retMap.put("scoreList", scoreList);
            retMap.put("avgList", avgStringList);
            retMap.put("type", "success");
            System.out.println(retMap);
            return retMap;
        }

        Score score = new Score();
        score.setCourseId(courseid);
        List<Score> scoreList = scoreService.getAllByCourseId(score);
        System.out.println(scoreList);

        List<Integer> numberList = new ArrayList<>();
        //每个分数段的数量初始化值0
        numberList.add(0);  //60分以下的数量，数组索引为0
        numberList.add(0);  //61~70分的数量，数组索引为1
        numberList.add(0);  //71~80分以下的数量，数组索引为2
        numberList.add(0);  //81~90分以下的数量，数组索引为3
        numberList.add(0);  //90分以上的数量，数组索引为4

        //分数段等级名称
        List<String> rangeStringList = new ArrayList<>();
        rangeStringList.add("60分以下");
        rangeStringList.add("60~70分");
        rangeStringList.add("70~80分");
        rangeStringList.add("80~90分");
        rangeStringList.add("90~100分");

        //统计各个分数段的数量
        String courseName = "";
        for (Score sc : scoreList){
            courseName = sc.getCourseName();
            double scoreValue = sc.getScore();
            //60分以下
            if (scoreValue < 60){
                numberList.set(0, numberList.get(0) + 1);
                continue;   //跳出本次循环，不需要再执行后面的代码
            }
            //61~70分
            if (scoreValue < 70 && scoreValue >= 60){
                numberList.set(1, numberList.get(1) + 1);
                continue;
            }
            //71~80分
            if (scoreValue < 80 && scoreValue >= 70){
                numberList.set(2, numberList.get(2) + 1);
                continue;
            }
            //81~90分
            if (scoreValue < 90 && scoreValue >= 80){
                numberList.set(3, numberList.get(3) + 1);
                continue;
            }
            //91~100分
            if (scoreValue >= 90 && scoreValue <= 100){
                numberList.set(4, numberList.get(4) + 1);
                continue;
            }
        }

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("courseName", courseName);
        retMap.put("numberList", numberList);
        retMap.put("rangeList", rangeStringList);
        retMap.put("type", "success");
        System.out.println(retMap);
        return retMap;
    }




}
