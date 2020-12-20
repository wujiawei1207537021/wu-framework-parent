package com.wu.freamwork.controller.ws.domian;


import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import lombok.Data;

@Data
@EasySmart(value = "rn_rcm_csmt_comp_business_qualification", comment = "25413信用_公路建设市场企业经营资质信息表")
public class CompBusinessQualification {


    @EasySmartField(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasySmartField(value = "unified_social_credit_code", type = "VARCHAR2(18)", comment = " 统一社会信用代码 ")
    private String TONGYISHEHUIXYDM;
    @EasySmartField(value = "qualification_name", type = "VARCHAR2(100)", comment = " 资质名称 ")
    private String ZIZHIMINGCHENG;
    @EasySmartField(value = "industry", type = "VARCHAR2(255)", comment = " 所属行业 ")
    private String SUOSHUXINGYE;
    @EasySmartField(value = "specialty_specialty", type = "VARCHAR2(255)", comment = " 所属专业/专项 ")
    private String SUOSHUZHUANYEZX;
    @EasySmartField(value = "grade_code", type = "VARCHAR2(1000)", comment = " 等级代码 ")
    private String DENGJIDAIMA;
    @EasySmartField(value = "qualification_certificate_no", type = "VARCHAR2(255)", comment = " 资质证书编号 ")
    private String ZIZHIZHENGSHUBH;
    @EasySmartField(value = "issuing_authority", type = "VARCHAR2(255)", comment = " 发证机关 ")
    private String FAZHENGJIGUAN;
    @EasySmartField(value = "issue_date", type = "DATE", comment = " 发证日期 ")
    private String FAZHENGRIQI;
    @EasySmartField(value = "business_scope", type = "VARCHAR2(4000)", comment = " 业务承接范围 ")
    private String YEWUCHENGJIEFW;
    @EasySmartField(value = "certificate_validity_start_date", type = "DATE", comment = " 证书有效期开始日期 ")
    private String ZHENGSHUYOUXIAOQKSRQ;
    @EasySmartField(value = "certificate_expiration_date", type = "DATE", comment = " 证书有效期截至日期 ")
    private String ZHENGSHUYOUXIAOQJZRQ;
    @EasySmartField(value = "remarks", type = "VARCHAR2(1000)", comment = " 备注 ")
    private String BEIZHU;
    @EasySmartField(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @EasySmartField(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasySmartField(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
