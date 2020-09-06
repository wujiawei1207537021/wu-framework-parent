package com.wuframework.system.serivce.def;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.SysDict;
import com.wuframework.system.persistence.mapper.SysDictMapper;
import com.wuframework.system.serivce.SysDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.*;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Service
public class DefaultSysDictService extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    /**
     * 保存字典信息
     *
     * @param sysUser
     * @param dict    字典
     * @return
     */
    @Override
    public Result saveSysDict(DefaultSysUser sysUser, SysDict dict) {
        dict.setCreateBy(sysUser.getUserId().intValue());
        if (dict.insert()) {
            return ResultFactory.successOf(1);
        }
        return ResultFactory.of(DefaultResultCode.DEFAULT_ERROR);
    }

    /**
     * 修改字典信息
     *
     * @param dict 字典
     * @return
     */
    @Override
    public Integer updateSysDict(SysDict dict) {
        return this.sysDictMapper.updateById(dict);
    }

    /**
     * 根据ID删除字典信息
     *
     * @param dictId 字典ID
     * @return
     */
    @Override
    public Integer batchDeleteSysDict(Long[] dictId) {
        SysDict sysDict = new SysDict();
        sysDict.setEnabled(0);
        int row = 0;
        for (Long ids : dictId) {
            sysDict.setId(ids);
            row += this.sysDictMapper.updateById(sysDict);
        }
        return row;
    }

    /**
     * @param dictType 词典类型
     * @return
     */
    @Override
    public List<SysDict> listSysDictByTypeOrAll(String dictType) {
        List<SysDict> Result = new ArrayList<>();
        if (StringUtils.isNotBlank(dictType)) {
            Result = this.sysDictMapper.selectList(new EntityWrapper<SysDict>().eq("type", dictType)
                    .eq("enabled", true));
        } else {
            Result = this.sysDictMapper.selectList(null);
        }
        //排序参数null时排序
        final Comparator<SysDict> comparator = comparing(SysDict::getSort, nullsLast(naturalOrder()));
        Result.sort(comparator);
        return Result;
    }

    /**
     * 获取字典列表
     *
     * @return
     */
    @Override
    public List<SysDict> getList() {
        return this.sysDictMapper.selectList(null);
    }

    /**
     * 根据词典类型与值查找字典
     *
     * @param dictType 词典类型
     * @param value    词典值
     * @return
     */
    @Override
    public SysDict getSysDictByTypeAndValue(String dictType, String value) {
        SysDict sysDict = new SysDict();
        sysDict.setType(dictType);
        sysDict.setValue(value);
        return this.sysDictMapper.selectOne(sysDict);
    }

    /**
     * 根据经营返回查询字典对应的名称
     *
     * @param manageScope
     * @return
     */
    @Override
    public List<String> getManageScope(String manageScope) {
        return sysDictMapper.selectManageScope(manageScope);
    }

    @Override
    public Result all() {
        List<SysDict> sysDictList= sysDictMapper.selectList(null);
        return ResultFactory.successOf(sysDictList);
    }
}