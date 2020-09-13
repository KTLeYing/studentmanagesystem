package com.mzl.studentmanagesystem.service.impl;

import com.mzl.studentmanagesystem.dao.StudentDao;
import com.mzl.studentmanagesystem.entity.Student;
import com.mzl.studentmanagesystem.service.StudentService;
import com.mzl.studentmanagesystem.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :   StudentServiceImpl
 * @Description: 学生业务逻辑实现类
 * @Author: 21989
 * @CreateDate: 2020/7/29 11:07
 * @Version: 1.0
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    /**
     * 查询学生（登录）
     * @param student
     * @return
     */
    @Override
    public Student findByStudent(Student student) {
        return studentDao.findByStudent(student);
    }

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    @Override
    public PageBean<Student> queryPage(Map<String, Object> paramMap) {
        PageBean<Student> pagBean = new PageBean<>((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));

        Integer startIndex = pagBean.getStartIndex();
        paramMap.put("startIndex", startIndex);
        List<Student> datas = studentDao.queryList(paramMap);
        pagBean.setDatas(datas);

        //查询总记录数
        Integer totalsize = studentDao.queryCount(paramMap);
        //设置总记录数，同时也设置了总页数
        pagBean.setTotalsize(totalsize);
        System.out.println(totalsize);
        System.out.println(pagBean);
        return pagBean;
    }

    /**
     * 获取图片地址（教师和学生均可用）
     * @param sid
     * @return
     */
    @Override
    public Student findById(Integer sid) {
        return studentDao.findById(sid);
    }

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    @Override
    public int editStudent(Student student) {
        return studentDao.editStudent(student);
    }

    /**
     * 通过同学名字找到id
     * @param name
     * @return
     */
    @Override
    public int findByName(String name) {
        return studentDao.findByName(name);
    }

    /**
     * 修改密码
     * @param student
     * @return
     */
    @Override
    public int editPswByStudent(Student student) {
        return studentDao.editPswByStudent(student);
    }

    /**
     * 保存学生信息到数据库
     * @param student
     * @return
     */
    @Override
    public int addStudent(Student student) {
        return studentDao.addStudent(student);
    }

    /**
     * 删除学生（包含批量删除）
     * @param ids
     * @return
     */
    @Override
    public int deleteStudent(List<Integer> ids) {
        return studentDao.deleteStudent(ids);
    }

    /**
     * 判断课程是否有关联的学生
     * @param id
     * @return
     */
    @Override
    public boolean isStudent(Integer id) {
        List<Student> list = studentDao.findByClazzId(id);
        if (list.isEmpty()){
            return true;
        }else {
            return false;
        }
    }


}
