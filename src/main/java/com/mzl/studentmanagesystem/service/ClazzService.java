package com.mzl.studentmanagesystem.service;

import com.mzl.studentmanagesystem.entity.Clazz;
import com.mzl.studentmanagesystem.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   ClazzService
 * @Description: 班级业务逻辑接口
 * @Author: 21989
 * @CreateDate: 2020/7/30 0:01
 * @Version: 1.0
 */
public interface ClazzService {

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    PageBean<Clazz> queryPage(Map<String, Object> paramMap);

    /**
     * 添加班级
     * @param clazz
     * @return
     */
    int addClazz(Clazz clazz);

    /**
     * 修改班级
     * @param clazz
     * @return
     */
    int editClazz(Clazz clazz);

    /**
     * 删除班级（包含批量删除）
     * @param ids
     * @return
     */
    int deleteClazz(List<Integer> ids);
}
