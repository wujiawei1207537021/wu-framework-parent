package com.wuframework.system.serivce.def;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.system.common.entity.SysDictType;
import com.wuframework.system.persistence.mapper.SysDictTypeMapper;
import com.wuframework.system.serivce.SysDictTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Service
public class DefaultSysDictTypeService extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    /**
     * 添加字典类别
     *
     * @param sysDictType 字典类型
     * @return
     */
    @Override
    public Result saveSysDictType(SysDictType sysDictType) {
        if (ObjectUtils.isEmpty(sysDictType.selectById())) {
            sysDictType.insert();
            return ResultFactory.successOf(1);
        }
        return ResultFactory.of(DefaultResultCode.DUPLICATE_KEY_EXCEPTION, "添加失败");
    }

    /**
     * 修改字典类别
     *
     * @param sysDictType 字典类别
     * @return
     */
    @Override
    public Result updateSysDictType(SysDictType sysDictType) {
        sysDictType.updateById();
        return ResultFactory.successOf();
    }

    /**
     * 根据ID删除字典类别，及其字典信息
     *
     * @param typeId 类别ID
     * @return
     */
    @Override
    public Result deleteSysDictTypeAndDict(String typeId) {
        SysDictType sysDictType = new SysDictType();
        sysDictType.setEnabled(0);
        sysDictType.setTypeId(typeId);
        if (sysDictType.updateById()) {
            return ResultFactory.successOf(1);
        }
        return ResultFactory.of(DefaultResultCode.RESOURCE_NOT_FOUNT);
    }

    /**
     * 根据字典类别判断是否有重复字典类别
     *
     * @param typeId 类别ID
     * @return
     */
    @Override
    public Result<Boolean> isExist(String typeId) {
        SysDictType sysDictType = new SysDictType();
        sysDictType.setEnabled(0);
        sysDictType.setTypeId(typeId);
        return ResultFactory.successOf(sysDictType.selectById() != null);
    }

    /**
     * 查询所有字典类型
     *
     * @return
     */
    @Override
    public Result<List<SysDictType>> selectSysDictTypeList() {
        SysDictType sysDictType = new SysDictType();
        return ResultFactory.successOf(sysDictType.selectAll());
    }

}
