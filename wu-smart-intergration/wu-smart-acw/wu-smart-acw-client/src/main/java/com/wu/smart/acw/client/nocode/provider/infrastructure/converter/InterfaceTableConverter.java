package com.wu.smart.acw.client.nocode.provider.infrastructure.converter;


import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceTableDO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.table.InterfaceTable;

/**
 * describe 接口与表的关联 
 *
 * @author Jia wei Wu
 * @date 2023/08/15 01:03 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class InterfaceTableConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:03 下午
     **/
    public static InterfaceTable toInterfaceTable(InterfaceTableDO interfaceTableDO) {
        if (null != interfaceTableDO) {
        InterfaceTable interfaceTable = new InterfaceTable();
           interfaceTable.setApiId(interfaceTableDO.getApiId());
           interfaceTable.setCreateTime(interfaceTableDO.getCreateTime());
           interfaceTable.setId(interfaceTableDO.getId());
           interfaceTable.setIsDeleted(interfaceTableDO.getIsDeleted());
           interfaceTable.setIsMainTable(interfaceTableDO.getIsMainTable());
           interfaceTable.setTableName(interfaceTableDO.getTableName());
           interfaceTable.setUpdateTime(interfaceTableDO.getUpdateTime());
            return interfaceTable;
        }
        return null;
    }

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:03 下午
     **/
    public static InterfaceTableDO fromInterfaceTable(InterfaceTable interfaceTable) {
        if (null != interfaceTable) {
        InterfaceTableDO interfaceTableDO = new InterfaceTableDO(); 
           interfaceTableDO.setApiId(interfaceTable.getApiId());
           interfaceTableDO.setCreateTime(interfaceTable.getCreateTime());
           interfaceTableDO.setId(interfaceTable.getId());
           interfaceTableDO.setIsDeleted(interfaceTable.getIsDeleted());
           interfaceTableDO.setIsMainTable(interfaceTable.getIsMainTable());
           interfaceTableDO.setTableName(interfaceTable.getTableName());
           interfaceTableDO.setUpdateTime(interfaceTable.getUpdateTime());
            return interfaceTableDO;
        }
        return null;
    }

}