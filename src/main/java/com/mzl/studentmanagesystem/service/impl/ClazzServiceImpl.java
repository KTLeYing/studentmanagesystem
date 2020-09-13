package com.mzl.studentmanagesystem.service.impl;

import com.mzl.studentmanagesystem.dao.ClazzDao;
import com.mzl.studentmanagesystem.entity.Clazz;
import com.mzl.studentmanagesystem.service.ClazzService;
import com.mzl.studentmanagesystem.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :   ClazzServiceImpl
 * @Description:  班级业务逻辑
 * @Author: 21989
 * @CreateDate: 2020/7/30 0:02
 * @Version: 1.0
 */
@Service
@Transactional
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzDao clazzDao;

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    @Override
    public PageBean<Clazz> queryPage(Map<String, Object> paramMap) {
        PageBean<Clazz> pageBean = new PageBean<Clazz>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));
        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex", startIndex);
        //查询内容
        List<Clazz> datas = clazzDao.queryList(paramMap);
        pageBean.setDatas(datas);
        System.out.println(datas);
        //查询总记录数
        Integer totalSize = clazzDao.queryCount(paramMap);
        //设置总记录数，同时设置总页数
        pageBean.setTotalsize(totalSize);
        System.out.println(pageBean);
        return pageBean;
    }

    /**
     * 添加班级
     * @param clazz
     * @return
     */
    @Override
    public int addClazz(Clazz clazz) {
        return clazzDao.addClazz(clazz);
    }

    /**
     * 修改班级
     * @param clazz
     * @return
     */
    @Override
    public int editClazz(Clazz clazz) {
        return clazzDao.editClazz(clazz);
    }

    /**
     * 删除班级（包含批量删除）
     * @param ids
     * @return
     */
    @Override
    public int deleteClazz(List<Integer> ids) {
        return clazzDao.deleteClazz(ids);
    }


}
