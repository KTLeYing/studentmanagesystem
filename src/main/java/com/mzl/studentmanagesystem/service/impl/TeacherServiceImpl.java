package com.mzl.studentmanagesystem.service.impl;

import com.mzl.studentmanagesystem.dao.TeacherDao;
import com.mzl.studentmanagesystem.entity.Teacher;
import com.mzl.studentmanagesystem.service.TeacherService;
import com.mzl.studentmanagesystem.util.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName :   TeacherServiceImpl
 * @Description: 教师业务逻辑
 * @Author: 21989
 * @CreateDate: 2020/7/30 8:52
 * @Version: 1.0
 */
@Slf4j
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    /**
     * 用教师id查找教师
     * @param tid
     * @return
     */
    @Override
    public Teacher findById(Integer tid) {
        return teacherDao.findById(tid);
    }

    /**
     * 老师登录，使用用户名和密码
     * @param teacher
     * @return
     */
    @Override
    public Teacher findByTeacher(Teacher teacher) {
        return teacherDao.findByTeacher(teacher);
    }

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    @Override
    public PageBean<Teacher> queryPage(Map<String, Object> paramMap) {
        log.debug("debug");
        log.error("error");
        log.warn("warn");
        System.out.println(paramMap);
        PageBean<Teacher> pageBean = new PageBean<>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));
        //查询开始的位置
        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex", startIndex);
        //分页查询教师每页内容
        List<Teacher> datas = teacherDao.queryList(paramMap);
        System.out.println(datas);
        pageBean.setDatas(datas);
        //查询总记录数
        Integer totalsize = teacherDao.queryCount(paramMap);
        System.out.println(totalsize);
        pageBean.setTotalsize(totalsize);

        System.out.println(pageBean);
        return pageBean;
    }

    /**
     * 修改教师
     * @param teacher
     * @return
     */
    @Override
    public int editTeacher(Teacher teacher) {
        return teacherDao.editTeacher(teacher);
    }

    /**
     * 修改老师密码
     * @param teacher
     * @return
     */
    @Override
    public int editPswByTeacher(Teacher teacher) {
        return teacherDao.editPswByTeacher(teacher);
    }

    /**
     * 添加老师
     * @param teacher
     * @return
     */
    @Override
    public int addTeacher(Teacher teacher) {
        return teacherDao.addTeacher(teacher);
    }

    /**
     * 删除教师
     * @param ids
     * @return
     */
    @Override
    public int deleteTeacher(List<Integer> ids) {
        return teacherDao.deleteTeacher(ids);
    }


}
