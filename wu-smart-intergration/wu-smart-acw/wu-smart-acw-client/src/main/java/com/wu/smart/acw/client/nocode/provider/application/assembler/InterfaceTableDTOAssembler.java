package com.wu.smart.acw.client.nocode.provider.application.assembler;


import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceTableCommand;
import com.wu.smart.acw.client.nocode.provider.application.dto.InterfaceTableDTO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.table.InterfaceTable;

/**
 * describe 接口与表的关联 
 *
 * @author Jia wei Wu
 * @date 2023/08/15 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/

public class InterfaceTableDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/
    public static InterfaceTable toInterfaceTable(InterfaceTableCommand interfaceTableCommand) {
        if (null != interfaceTableCommand) {
        InterfaceTable interfaceTable = new InterfaceTable(); 
           interfaceTable.setApiId(interfaceTableCommand.getApiId());
           interfaceTable.setCreateTime(interfaceTableCommand.getCreateTime());
           interfaceTable.setId(interfaceTableCommand.getId());
           interfaceTable.setIsDeleted(interfaceTableCommand.getIsDeleted());
           interfaceTable.setIsMainTable(interfaceTableCommand.getIsMainTable());
           interfaceTable.setTableName(interfaceTableCommand.getTableName());
           interfaceTable.setUpdateTime(interfaceTableCommand.getUpdateTime());
            return interfaceTable;
        }
        return null;
    }

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/
    public static InterfaceTableDTO fromInterfaceTable(InterfaceTable interfaceTable) {
        if (null != interfaceTable) {
        InterfaceTableDTO interfaceTableDTO = new InterfaceTableDTO(); 
           interfaceTableDTO.setApiId(interfaceTable.getApiId());
           interfaceTableDTO.setCreateTime(interfaceTable.getCreateTime());
           interfaceTableDTO.setId(interfaceTable.getId());
           interfaceTableDTO.setIsDeleted(interfaceTable.getIsDeleted());
           interfaceTableDTO.setIsMainTable(interfaceTable.getIsMainTable());
           interfaceTableDTO.setTableName(interfaceTable.getTableName());
           interfaceTableDTO.setUpdateTime(interfaceTable.getUpdateTime());
            return interfaceTableDTO;
        }
        return null;
    }

}