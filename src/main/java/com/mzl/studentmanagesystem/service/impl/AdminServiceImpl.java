package com.mzl.studentmanagesystem.service.impl;

import com.mzl.studentmanagesystem.dao.AdminDao;
import com.mzl.studentmanagesystem.entity.Admin;
import com.mzl.studentmanagesystem.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName :   AdminServiceImpl
 * @Description: 管理员业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2020/8/10 14:20
 * @Version: 1.0
 */
@Slf4j
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    /**
     * 管理员登录（用密码和用户名检查）
     * @param admin
     * @return
     */
    @Override
    public Admin findByAdmin(Admin admin) {
        return adminDao.findByAdmin(admin);
    }

    /**
     * 修改密码
     * @param admin
     * @return
     */
    @Override
    public int editPswByAdmin(Admin admin) {
        return adminDao.editPswByAdmin(admin);
    }
}
