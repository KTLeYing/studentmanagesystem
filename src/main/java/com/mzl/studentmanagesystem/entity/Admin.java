package com.mzl.studentmanagesystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Admin
 * @Description: 管理员
 * @Author: 21989
 * @CreateDate: 2020/7/29 14:57
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class Admin {

    private int id;
    private String username;
    private String password;

}
