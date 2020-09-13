package com.mzl.studentmanagesystem.service;

import com.mzl.studentmanagesystem.entity.Admin;

/**
 * @InterfaceName :   AdminService
 * @Description: 管理员业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2020/8/10 14:19
 * @Version: 1.0
 */
public interface AdminService {

    /**
     * 管理员登录（用密码和用户名检查）
     * @param admin
     * @return
     */
    Admin findByAdmin(Admin admin);

    /**
     * 修改密码
     * @param admin
     * @return
     */
    int editPswByAdmin(Admin admin);
}
