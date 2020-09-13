package com.mzl.studentmanagesystem.dao;

import com.mzl.studentmanagesystem.entity.Score;
import com.mzl.studentmanagesystem.entity.ScoreStats;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   ScoreDao
 * @Description: 分数dao
 * @Author: mzl
 * @CreateDate: 2020/8/3 13:03
 * @Version: 1.0
 */
@Mapper
public interface ScoreDao {

    /**
     * 查询学生成绩列表
     * @param paramMap
     * @return
     */
    List<Score> queryList(Map<String, Object> paramMap);

    /**
     * 查询总记录数
     * @param paramMap
     * @return
     */
    Integer queryCount(Map<String, Object> paramMap);

    /**
     * 判断是否已存在数据库中
     * @param score
     * @return
     */
    Score isScore(Score score);

    /**
     * 添加成绩进去数据库
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

    /**
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
