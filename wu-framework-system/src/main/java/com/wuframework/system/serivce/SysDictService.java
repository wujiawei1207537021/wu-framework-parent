package com.wuframework.system.serivce;


import com.baomidou.mybatisplus.service.IService;
import com.wuframework.response.Result;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.SysDict;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
public interface SysDictService extends IService<SysDict> {
    /**
     * 保存字典信息
     *
     * @param sysUser
     * @param dict    字典
     * @return
     */
    Result saveSysDict(DefaultSysUser sysUser, SysDict dict);

    /**
     * 修改字典信息
     *
     * @param dict 字典
     * @return
     */
    Integer updateSysDict(SysDict dict);

    /**
     * 根据ID删除字典信息
     *
     * @param dictId 字典ID
     * @return
     */
    Integer batchDeleteSysDict(Long[] dictId);

    /**
     * 根据词典类型获取词典列表
     *
     * @param dictType 词典类型
     * @return
     */
    List<SysDict> listSysDictByTypeOrAll(String dictType);

    /**
     * 获取字典列表
     *
     * @return
     */
    List<SysDict> getList();

    /**
     * 根据词典类型与值查找字典
     *
     * @param dictType 词典类型
     * @param value    词典值
     * @return
     */
    SysDict getSysDictByTypeAndValue(String dictType, String value);

    /**
     * 根据经营返回查询字典对应的名称
     *
     * @param manageScope
     * @return
     */
    List<String> getManageScope(String manageScope);

    /**
     * 获取所有字典并转换成树
     *
     * @return
     */
    Result all();
}
