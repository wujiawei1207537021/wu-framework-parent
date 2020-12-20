package com.wu.freamwork.controller.ws.domian;


import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import lombok.Data;

@Data
@EasySmart(value = "rn_rcm_csmt_comp_commendation", comment = "25411信用_公路建设市场企业表彰信息表")
public class CompCommendation {

    @EasySmartField(value = "", type = "类型", comment = " 描述 ")
    private String 数据项;
    @EasySmartField(value = "data_id", type = "VARCHAR2(200)", comment = "  ")
    private String ID;
    @EasySmartField(value = "unified_social_credit_code", type = "VARCHAR2(18)", comment = " 统一社会信用代码 ")
    private String TONGYISHEHUIXYDM;
    @EasySmartField(value = "commendation_title", type = "VARCHAR2(255)", comment = " 表彰标题 ")
    private String BIAOZHANGBIAOTI;
    @EasySmartField(value = "commendation_number", type = "VARCHAR2(255)", comment = " 表彰文号 ")
    private String BIAOZHANGWENHAO;
    @EasySmartField(value = "commendation_year", type = "NUMBER(4)", comment = " 表彰年度 ")
    private String BIAOZHANGNIANDU;
    @EasySmartField(value = "recognition_level_code", type = "VARCHAR2(50)", comment = " 表彰等级代码 ")
    private String BIAOZHANGDENGJIDM;
    @EasySmartField(value = "issued_by", type = "VARCHAR2(255)", comment = " 发布单位 ")
    private String FABUDANWEI;
    @EasySmartField(value = "issue_date", type = "DATE", comment = " 发布日期 ")
    private String FABURIQI;
    @EasySmartField(value = "commendation_content", type = "VARCHAR2(4000)", comment = " 表彰内容 ")
    private String BIAOZHANGNEIRONG;
    @EasySmartField(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @EasySmartField(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasySmartField(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
