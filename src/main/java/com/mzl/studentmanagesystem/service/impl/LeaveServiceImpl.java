package com.mzl.studentmanagesystem.service.impl;

import com.mzl.studentmanagesystem.dao.LeaveDao;
import com.mzl.studentmanagesystem.entity.Leave;
import com.mzl.studentmanagesystem.service.LeaveService;
import com.mzl.studentmanagesystem.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ClassName :   LeaveServiceImpl
 * @Description: 请假业务逻辑
 * @Author: 21989
 * @CreateDate: 2020/7/31 21:40
 * @Version: 1.0
 */
@Service
@Transactional
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveDao leaveDao;

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    @Override
    public PageBean<Leave> queryPage(Map<String, Object> paramMap) {
        PageBean<Leave> pageBean = new PageBean<Leave>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));
        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex", startIndex);
        //分页查询列表
        List<Leave> datas = leaveDao.queryList(paramMap);
        pageBean.setDatas(datas);
        //查询总记录数
        Integer totalsize = leaveDao.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);

        System.out.println(pageBean);
        return pageBean;
    }

    /**
     * 添加请假
     * @param leave
     * @return
     */
    @Override
    public int addLeave(Leave leave) {
        return leaveDao.addLeave(leave);
    }

    /**
     * 修改请假
     * @param leave
     * @return
     */
    @Override
    public int editLeave(Leave leave) {
        return leaveDao.editLeave(leave);
    }

    /**
     * 删除请假
     * @param id
     * @return
     */
    @Override
    public int deleteLeave(Integer id) {
        return leaveDao.deleteLeave(id);
    }

    /**
     * 检查审核学生请假
     * @param leave
     * @return
     */
    @Override
    public int checkLeave(Leave leave) {
        return leaveDao.checkLeave(leave);
    }


}
