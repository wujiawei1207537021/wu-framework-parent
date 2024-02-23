package com.wuframework.system.serivce;

import com.baomidou.mybatisplus.service.IService;
import com.wuframework.response.Result;
import com.wuframework.system.common.entity.SysDictType;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
public interface SysDictTypeService extends IService<SysDictType> {
    /**
     * 添加字典类别
     *
     * @param sysDictType 字典类别
     * @return
     */
    Result saveSysDictType(SysDictType sysDictType);

    /**
     * 修改字典类别
     *
     * @param sysDictType 字典类别
     * @return
     */
    Result updateSysDictType(SysDictType sysDictType);

    /**
     * 根据ID删除字典类别，及其字典信息
     *
     * @param typeId 类别ID
     * @return
     */
    Result deleteSysDictTypeAndDict(String typeId);

    /**
     * 根据字典类别判断是否有重复字典类别
     *
     * @param typeId 类别ID
     * @return
     */
    Result<Boolean> isExist(String typeId);

    /**
     * 查询所有字典类型
     *
     * @return
     */
    Result<List<SysDictType>> selectSysDictTypeList();
}
