package com.mzl.studentmanagesystem.dao;

import com.mzl.studentmanagesystem.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @InterfaceName :   AdminDao
 * @Description: 管理员dao
 * @Author: mzl
 * @CreateDate: 2020/8/10 14:25
 * @Version: 1.0
 */
@Mapper
public interface AdminDao {

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
