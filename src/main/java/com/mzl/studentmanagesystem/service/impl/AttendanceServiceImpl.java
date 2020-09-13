package com.mzl.studentmanagesystem.service.impl;

import com.mzl.studentmanagesystem.dao.AttendanceDao;
import com.mzl.studentmanagesystem.entity.Attendance;
import com.mzl.studentmanagesystem.service.AttendanceService;
import com.mzl.studentmanagesystem.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ClassName :   AttendanceServiceImpl
 * @Description: 缺勤业务逻辑实现层
 * @Author: 21989
 * @CreateDate: 2020/7/31 10:11
 * @Version: 1.0
 */
@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

    /**
     * 分页查询缺勤表
     * @param paramMap
     * @return
     */
    @Override
    public PageBean<Attendance> queryPage(Map<String, Object> paramMap) {
        PageBean<Attendance> pageBean = new PageBean<Attendance>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));
        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex", startIndex);
        //分页查询列表
        System.out.println(paramMap);
        List<Attendance> datas = attendanceDao.queryList(paramMap);
        System.out.println(datas);
        pageBean.setDatas(datas);
        //查询总记录数
        Integer totalsize = attendanceDao.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);

        System.out.println(pageBean);
        return pageBean;
    }

    /**
     * 判断是否签到
     * @param attendance
     * @return
     */
    @Override
    public boolean isAttendance(Attendance attendance) {
        Attendance attendance1 = attendanceDao.isAttendance(attendance);
        if (attendance1 != null){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 添加出勤签到
     * @param attendance
     * @return
     */
    @Override
    public int addAttendance(Attendance attendance) {
        return attendanceDao.addAttendance(attendance);
    }

    /**
     * 删除出勤
     * @param id
     * @return
     */
    @Override
    public int deleteAttendance(Integer id) {
        return attendanceDao.deleteAttendance(id);
    }


}
