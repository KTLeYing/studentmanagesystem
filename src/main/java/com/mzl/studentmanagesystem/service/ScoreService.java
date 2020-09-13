package com.mzl.studentmanagesystem.service;

import com.mzl.studentmanagesystem.entity.Score;
import com.mzl.studentmanagesystem.entity.ScoreStats;
import com.mzl.studentmanagesystem.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   ScoreService
 * @Description: 成绩业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/8/3 13:02
 * @Version: 1.0
 */
public interface ScoreService {

    /**
     * 查询学生成绩列表
     * @param paramMap
     * @return
     */
    PageBean<Score> queryPage(Map<String, Object> paramMap);

    /**
     * 判断是否已存在数据库中
     * @param score
     * @return
     */
    boolean isScore(Score score);

    /**
     * 添加进去数据库
     * @param score
     * @return
     */
    int addScore(Score score);

    /**
     * 获取所有成绩列表
     * @param score
     * @return
     */
    List<Score> getAll(Score score);

    /**
     * 查询某个学生的某门课程是否存在
     * @param score
     * @return
     */
    Score findByStuIdAndCouId(Score score);

    /**
     * 修改成绩
     * @param score
     * @return
     */
    int editScore(Score score);

    /**
     * 删除成绩
     * @param id
     * @return
     */
    int deleteScore(Integer id);

    /***
     * 求和函数和平均值的统计
     * @param courseid
     * @return
     */
    ScoreStats getAvgStats(Integer courseid);

    /**
     * 查询某课程的所有成绩
     * @param score
     * @return
     */
    List<Score> getAllByCourseId(Score score);
}
