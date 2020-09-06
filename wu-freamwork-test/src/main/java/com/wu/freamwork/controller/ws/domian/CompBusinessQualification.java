package com.wu.freamwork.controller.ws.domian;



import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTable;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTableFile;
import lombok.Data;

@Data
@CustomTable(value = "rn_rcm_csmt_comp_business_qualification", comment = "25413信用_公路建设市场企业经营资质信息表")
public class CompBusinessQualification {


    @CustomTableFile(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @CustomTableFile(value = "unified_social_credit_code", type = "VARCHAR2(18)", comment = " 统一社会信用代码 ")
    private String TONGYISHEHUIXYDM;
    @CustomTableFile(value = "qualification_name", type = "VARCHAR2(100)", comment = " 资质名称 ")
    private String ZIZHIMINGCHENG;
    @CustomTableFile(value = "industry", type = "VARCHAR2(255)", comment = " 所属行业 ")
    private String SUOSHUXINGYE;
    @CustomTableFile(value = "specialty_specialty", type = "VARCHAR2(255)", comment = " 所属专业/专项 ")
    private String SUOSHUZHUANYEZX;
    @CustomTableFile(value = "grade_code", type = "VARCHAR2(1000)", comment = " 等级代码 ")
    private String DENGJIDAIMA;
    @CustomTableFile(value = "qualification_certificate_no", type = "VARCHAR2(255)", comment = " 资质证书编号 ")
    private String ZIZHIZHENGSHUBH;
    @CustomTableFile(value = "issuing_authority", type = "VARCHAR2(255)", comment = " 发证机关 ")
    private String FAZHENGJIGUAN;
    @CustomTableFile(value = "issue_date", type = "DATE", comment = " 发证日期 ")
    private String FAZHENGRIQI;
    @CustomTableFile(value = "business_scope", type = "VARCHAR2(4000)", comment = " 业务承接范围 ")
    private String YEWUCHENGJIEFW;
    @CustomTableFile(value = "certificate_validity_start_date", type = "DATE", comment = " 证书有效期开始日期 ")
    private String ZHENGSHUYOUXIAOQKSRQ;
    @CustomTableFile(value = "certificate_expiration_date", type = "DATE", comment = " 证书有效期截至日期 ")
    private String ZHENGSHUYOUXIAOQJZRQ;
    @CustomTableFile(value = "remarks", type = "VARCHAR2(1000)", comment = " 备注 ")
    private String BEIZHU;
    @CustomTableFile(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @CustomTableFile(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @CustomTableFile(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
