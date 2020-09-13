package com.mzl.studentmanagesystem.service.impl;

import com.mzl.studentmanagesystem.dao.ScoreDao;
import com.mzl.studentmanagesystem.entity.Score;
import com.mzl.studentmanagesystem.entity.ScoreStats;
import com.mzl.studentmanagesystem.service.ScoreService;
import com.mzl.studentmanagesystem.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName :   ScoreServiceImpl
 * @Description: 成绩业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2020/8/3 13:02
 * @Version: 1.0
 */
@Service
@Transactional
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreDao scoreDao;

    /**
     * 查询学生成绩列表
     * @param paramMap
     * @return
     */
    @Override
    public PageBean<Score> queryPage(Map<String, Object> paramMap) {
        System.out.println(paramMap);
        PageBean<Score> pageBean = new PageBean<Score>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));
        //开始位置
        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex", startIndex);

        //查询学生成绩列表
        List<Score> datas = scoreDao.queryList(paramMap);
        pageBean.setDatas(datas);
        System.out.println(datas);
        //查询总记录数
        Integer totalsize = scoreDao.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);

        System.out.println(pageBean);
        return pageBean;
    }

    /**
     * 判断是否已存在数据库中
     * @param score
     * @return
     */
    @Override
    public boolean isScore(Score score) {
        Score score1 = scoreDao.isScore(score);
        if(StringUtils.isEmpty(score1)){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 添加进去数据库
     * @param score
     * @return
     */
    @Override
    public int addScore(Score score) {
        return scoreDao.addScore(score);
    }

    /**
     * 获取所有成绩列表
     * @param score
     * @return
     */
    @Override
    public List<Score> getAll(Score score) {
        return scoreDao.getAll(score);
    }

    /**
     * 查询某个学生的某门课程是否存在
     * @param score
     * @return
     */
    @Override
    public Score findByStuIdAndCouId(Score score) {
        return scoreDao.findByStuIdAndCouId(score);
    }

    /**
     * 修改成绩
     * @param score
     * @return
     */
    @Override
    public int editScore(Score score) {
        return scoreDao.editScore(score);
    }

    /**
     * 删除成绩
     * @param id
     * @return
     */
    @Override
    public int deleteScore(Integer id) {
        return scoreDao.deleteScore(id);
    }

    /**
     * 求和函数和平均值的统计
     * @param courseid
     * @return
     */
    @Override
    public ScoreStats getAvgStats(Integer courseid) {
        return scoreDao.getAvgStats(courseid);
    }

    /**
     * 查询某课程的所有成绩
     * @param score
     * @return
     */
    @Override
    public List<Score> getAllByCourseId(Score score) {
        return scoreDao.getAllByCourseId(score);
    }

}
