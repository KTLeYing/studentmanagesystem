package com.mzl.studentmanagesystem.dao;

import com.mzl.studentmanagesystem.entity.Clazz;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   ClazzDao
 * @Description: 班级dao层
 * @Author: 21989
 * @CreateDate: 2020/7/30 0:03
 * @Version: 1.0
 */
@Mapper
public interface ClazzDao {

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    List<Clazz> queryList(Map<String, Object> paramMap);

    /**
     * 查询总记录数
     * @param paramMap
     * @return
     */
    Integer queryCount(Map<String, Object> paramMap);

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
