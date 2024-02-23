package com.wu.framework.easy.temple.domain.excel;

import com.alibaba.fastjson.annotation.JSONField;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.EasySmartField;
import lombok.Data;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 用户角色导出Excel
 * @date : 2020/9/18 下午11:33
 */
@Data
@EasySmart(perfectTable = true)
public class UseUserRoleExcel {

    @EasyExcelFiled(name = "原生注解-用户ID")
    @JSONField(name = "JSONField注解-id")
    private Integer userId;

    @EasySmartField(name = "角色ID")
    @EasyExcelFiled(name = "原生注解-角色ID")
    @JSONField(name = "JSONField注解-角色ID")
    private Integer roleId;

    @EasySmartField(name = "角色名称")
    @EasyExcelFiled(name = "原生注解-角色名称")
    @JSONField(name = "JSONField注解-角色名称")
    private String roleName;

    @EasySmartField(name = "角色编码")
    @EasyExcelFiled(name = "原生注解-角色编码")
    @JSONField(name = "JSONField注解-角色编码")
    private String roleCode;


}
