package com.wu.freamwork.controller.ws.domian;


import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import lombok.Data;

@Data
@EasyTable(value = "rn_rcm_csmt_comp_business_qualification", comment = "25413信用_公路建设市场企业经营资质信息表")
public class CompBusinessQualification {


    @EasyTableField(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasyTableField(value = "unified_social_credit_code", type = "VARCHAR2(18)", comment = " 统一社会信用代码 ")
    private String TONGYISHEHUIXYDM;
    @EasyTableField(value = "qualification_name", type = "VARCHAR2(100)", comment = " 资质名称 ")
    private String ZIZHIMINGCHENG;
    @EasyTableField(value = "industry", type = "VARCHAR2(255)", comment = " 所属行业 ")
    private String SUOSHUXINGYE;
    @EasyTableField(value = "specialty_specialty", type = "VARCHAR2(255)", comment = " 所属专业/专项 ")
    private String SUOSHUZHUANYEZX;
    @EasyTableField(value = "grade_code", type = "VARCHAR2(1000)", comment = " 等级代码 ")
    private String DENGJIDAIMA;
    @EasyTableField(value = "qualification_certificate_no", type = "VARCHAR2(255)", comment = " 资质证书编号 ")
    private String ZIZHIZHENGSHUBH;
    @EasyTableField(value = "issuing_authority", type = "VARCHAR2(255)", comment = " 发证机关 ")
    private String FAZHENGJIGUAN;
    @EasyTableField(value = "issue_date", type = "DATE", comment = " 发证日期 ")
    private String FAZHENGRIQI;
    @EasyTableField(value = "business_scope", type = "VARCHAR2(4000)", comment = " 业务承接范围 ")
    private String YEWUCHENGJIEFW;
    @EasyTableField(value = "certificate_validity_start_date", type = "DATE", comment = " 证书有效期开始日期 ")
    private String ZHENGSHUYOUXIAOQKSRQ;
    @EasyTableField(value = "certificate_expiration_date", type = "DATE", comment = " 证书有效期截至日期 ")
    private String ZHENGSHUYOUXIAOQJZRQ;
    @EasyTableField(value = "remarks", type = "VARCHAR2(1000)", comment = " 备注 ")
    private String BEIZHU;
    @EasyTableField(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @EasyTableField(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasyTableField(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
