package com.wuframework.system.persistence.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wuframework.system.common.entity.SysDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Repository
public interface SysDictMapper extends BaseMapper<SysDict> {
    /**
     * 获取ID在指定范围内的数据字典名称
     *
     * @param manageScope ID字符串:1,2,3
     * @return
     */
    List<String> selectManageScope(@Param(value = "manageScope") String manageScope);

}

