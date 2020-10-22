package com.wu.freamwork.controller.ws.domian;


import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import lombok.Data;

@Data
@EasyTable(value = "rn_rcm_csmt_comp_administrative_sanction", comment = "25415信用_公路建设市场企业行政处罚信息表")
public class CompAdministrativeSanction {


    @EasyTableField(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasyTableField(value = "enterprise_registration_information_id", type = "VARCHAR2(255)", comment = " 企业登记信息ID ")
    private String QIYEDENGJIXXID;
    @EasyTableField(value = "penalty_title", type = "VARCHAR2(255)", comment = " 处罚标题 ")
    private String CHUFABIAOTI;
    @EasyTableField(value = "category", type = "VARCHAR2(20)", comment = " 类别 ")
    private String LEIBIE;
    @EasyTableField(value = "behavior_code", type = "VARCHAR2(255)", comment = " 行为代码 ")
    private String XINGWEIDAIMA;
    @EasyTableField(value = "punishment_number", type = "VARCHAR2(255)", comment = " 处罚文号 ")
    private String CHUFAWENHAO;
    @EasyTableField(value = "penalty_authorities", type = "VARCHAR2(255)", comment = " 处罚机关 ")
    private String CHUFAJIGUAN;
    @EasyTableField(value = "punishment_date", type = "DATE", comment = " 处罚日期 ")
    private String CHUFARIQI;
    @EasyTableField(value = "content", type = "VARCHAR2(4000)", comment = " 内容 ")
    private String NEIRONG;
    @EasyTableField(value = "expiration_date", type = "DATE", comment = " 失效日期 ")
    private String SHIXIAORIQI;
    @EasyTableField(value = "failure_reason", type = "VARCHAR2(4000)", comment = " 失效原因 ")
    private String SHIXIAOYUANYIN;
    @EasyTableField(value = "creation_time", type = "DATE", comment = " 创建时间 ")
    private String CHUANGJIANSHIJIAN;
    @EasyTableField(value = "founder", type = "VARCHAR2(255)", comment = " 创建人 ")
    private String CHUANGJIANREN;
    @EasyTableField(value = "create_department", type = "VARCHAR2(255)", comment = " 创建部门 ")
    private String CHUANGJIANBUMEN;
    @EasyTableField(value = "data_update_time", type = "DATE", comment = " 更新时间 ")
    private String GENGXINSHIJIAN;
    @EasyTableField(value = "updated_by", type = "VARCHAR2(255)", comment = " 更新人 ")
    private String GENGXINREN;
    @EasyTableField(value = "update_department", type = "VARCHAR2(255)", comment = " 更新部门 ")
    private String GENGXINBUMEN;
    @EasyTableField(value = "delete_time", type = "DATE", comment = " 删除时间 ")
    private String SHANCHUSHIJIAN;
    @EasyTableField(value = "deleted_by", type = "VARCHAR2(255)", comment = " 删除人 ")
    private String SHANCHUREN;
    @EasyTableField(value = "delete_department", type = "VARCHAR2(255)", comment = " 删除部门 ")
    private String SHANCHUBUMEN;
    @EasyTableField(value = "logical_deletion", type = "DATE", comment = " 逻辑删除 ")
    private String LUOJISHANCHU;
    @EasyTableField(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
