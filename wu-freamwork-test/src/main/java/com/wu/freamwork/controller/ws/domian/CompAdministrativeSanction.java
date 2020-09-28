package com.wu.freamwork.controller.ws.domian;



import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
import lombok.Data;

@Data
@EasyTable(value = "rn_rcm_csmt_comp_administrative_sanction", comment = "25415信用_公路建设市场企业行政处罚信息表")
public class CompAdministrativeSanction {


    @EasyTableFile(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasyTableFile(value = "enterprise_registration_information_id", type = "VARCHAR2(255)", comment = " 企业登记信息ID ")
    private String QIYEDENGJIXXID;
    @EasyTableFile(value = "penalty_title", type = "VARCHAR2(255)", comment = " 处罚标题 ")
    private String CHUFABIAOTI;
    @EasyTableFile(value = "category", type = "VARCHAR2(20)", comment = " 类别 ")
    private String LEIBIE;
    @EasyTableFile(value = "behavior_code", type = "VARCHAR2(255)", comment = " 行为代码 ")
    private String XINGWEIDAIMA;
    @EasyTableFile(value = "punishment_number", type = "VARCHAR2(255)", comment = " 处罚文号 ")
    private String CHUFAWENHAO;
    @EasyTableFile(value = "penalty_authorities", type = "VARCHAR2(255)", comment = " 处罚机关 ")
    private String CHUFAJIGUAN;
    @EasyTableFile(value = "punishment_date", type = "DATE", comment = " 处罚日期 ")
    private String CHUFARIQI;
    @EasyTableFile(value = "content", type = "VARCHAR2(4000)", comment = " 内容 ")
    private String NEIRONG;
    @EasyTableFile(value = "expiration_date", type = "DATE", comment = " 失效日期 ")
    private String SHIXIAORIQI;
    @EasyTableFile(value = "failure_reason", type = "VARCHAR2(4000)", comment = " 失效原因 ")
    private String SHIXIAOYUANYIN;
    @EasyTableFile(value = "creation_time", type = "DATE", comment = " 创建时间 ")
    private String CHUANGJIANSHIJIAN;
    @EasyTableFile(value = "founder", type = "VARCHAR2(255)", comment = " 创建人 ")
    private String CHUANGJIANREN;
    @EasyTableFile(value = "create_department", type = "VARCHAR2(255)", comment = " 创建部门 ")
    private String CHUANGJIANBUMEN;
    @EasyTableFile(value = "data_update_time", type = "DATE", comment = " 更新时间 ")
    private String GENGXINSHIJIAN;
    @EasyTableFile(value = "updated_by", type = "VARCHAR2(255)", comment = " 更新人 ")
    private String GENGXINREN;
    @EasyTableFile(value = "update_department", type = "VARCHAR2(255)", comment = " 更新部门 ")
    private String GENGXINBUMEN;
    @EasyTableFile(value = "delete_time", type = "DATE", comment = " 删除时间 ")
    private String SHANCHUSHIJIAN;
    @EasyTableFile(value = "deleted_by", type = "VARCHAR2(255)", comment = " 删除人 ")
    private String SHANCHUREN;
    @EasyTableFile(value = "delete_department", type = "VARCHAR2(255)", comment = " 删除部门 ")
    private String SHANCHUBUMEN;
    @EasyTableFile(value = "logical_deletion", type = "DATE", comment = " 逻辑删除 ")
    private String LUOJISHANCHU;
    @EasyTableFile(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
