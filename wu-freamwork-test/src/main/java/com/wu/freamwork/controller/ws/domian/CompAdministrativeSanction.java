package com.wu.freamwork.controller.ws.domian;



import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTable;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTableFile;
import lombok.Data;

@Data
@CustomTable(value = "rn_rcm_csmt_comp_administrative_sanction", comment = "25415信用_公路建设市场企业行政处罚信息表")
public class CompAdministrativeSanction {


    @CustomTableFile(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @CustomTableFile(value = "enterprise_registration_information_id", type = "VARCHAR2(255)", comment = " 企业登记信息ID ")
    private String QIYEDENGJIXXID;
    @CustomTableFile(value = "penalty_title", type = "VARCHAR2(255)", comment = " 处罚标题 ")
    private String CHUFABIAOTI;
    @CustomTableFile(value = "category", type = "VARCHAR2(20)", comment = " 类别 ")
    private String LEIBIE;
    @CustomTableFile(value = "behavior_code", type = "VARCHAR2(255)", comment = " 行为代码 ")
    private String XINGWEIDAIMA;
    @CustomTableFile(value = "punishment_number", type = "VARCHAR2(255)", comment = " 处罚文号 ")
    private String CHUFAWENHAO;
    @CustomTableFile(value = "penalty_authorities", type = "VARCHAR2(255)", comment = " 处罚机关 ")
    private String CHUFAJIGUAN;
    @CustomTableFile(value = "punishment_date", type = "DATE", comment = " 处罚日期 ")
    private String CHUFARIQI;
    @CustomTableFile(value = "content", type = "VARCHAR2(4000)", comment = " 内容 ")
    private String NEIRONG;
    @CustomTableFile(value = "expiration_date", type = "DATE", comment = " 失效日期 ")
    private String SHIXIAORIQI;
    @CustomTableFile(value = "failure_reason", type = "VARCHAR2(4000)", comment = " 失效原因 ")
    private String SHIXIAOYUANYIN;
    @CustomTableFile(value = "creation_time", type = "DATE", comment = " 创建时间 ")
    private String CHUANGJIANSHIJIAN;
    @CustomTableFile(value = "founder", type = "VARCHAR2(255)", comment = " 创建人 ")
    private String CHUANGJIANREN;
    @CustomTableFile(value = "create_department", type = "VARCHAR2(255)", comment = " 创建部门 ")
    private String CHUANGJIANBUMEN;
    @CustomTableFile(value = "data_update_time", type = "DATE", comment = " 更新时间 ")
    private String GENGXINSHIJIAN;
    @CustomTableFile(value = "updated_by", type = "VARCHAR2(255)", comment = " 更新人 ")
    private String GENGXINREN;
    @CustomTableFile(value = "update_department", type = "VARCHAR2(255)", comment = " 更新部门 ")
    private String GENGXINBUMEN;
    @CustomTableFile(value = "delete_time", type = "DATE", comment = " 删除时间 ")
    private String SHANCHUSHIJIAN;
    @CustomTableFile(value = "deleted_by", type = "VARCHAR2(255)", comment = " 删除人 ")
    private String SHANCHUREN;
    @CustomTableFile(value = "delete_department", type = "VARCHAR2(255)", comment = " 删除部门 ")
    private String SHANCHUBUMEN;
    @CustomTableFile(value = "logical_deletion", type = "DATE", comment = " 逻辑删除 ")
    private String LUOJISHANCHU;
    @CustomTableFile(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
