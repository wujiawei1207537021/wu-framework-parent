package com.wu.freamwork.controller.ws.domian;



import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTable;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTableFile;
import lombok.Data;

@Data
@CustomTable(value = "rn_rcm_csmt_comp_register", comment = "25412信用_公路建设市场企业登记信息表")
public class CompRegister {


    @CustomTableFile(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @CustomTableFile(value = "unified_social_credit_code", type = "VARCHAR2(255)", comment = " 统一社会信用代码 ")
    private String TONGYISHEHUIXYDM;
    @CustomTableFile(value = "unit_name", type = "VARCHAR2(255)", comment = " 单位名称 ")
    private String DANWEIMINGCHENG;
    @CustomTableFile(value = "name_used_before", type = "VARCHAR2(255)", comment = " 曾用名 ")
    private String CENGYONGMING;
    @CustomTableFile(value = "abbreviation", type = "VARCHAR2(255)", comment = " 简称 ")
    private String JIANCHENG;
    @CustomTableFile(value = "unit_type_code", type = "VARCHAR2(50)", comment = " 单位类型代码 ")
    private String DANWEILEIXINGDM;
    @CustomTableFile(value = "establishment_date", type = "DATE", comment = " 成立日期 ")
    private String CHENGLIRIQI;
    @CustomTableFile(value = "business_license_registration_date", type = "DATE", comment = " 营业执照注册日期 ")
    private String YINGYEZHIZHAOZCRQ;
    @CustomTableFile(value = "business_license_valid_date", type = "DATE", comment = " 营业执照有效日期 ")
    private String YINGYEZHIZHAOYXRQ;
    @CustomTableFile(value = "registration_authority", type = "VARCHAR2(100)", comment = " 登记机关 ")
    private String DENGJIJIGUAN;
    @CustomTableFile(value = "legal_representative", type = "VARCHAR2(255)", comment = " 法定代表人 ")
    private String FADINGDAIBIAOR;
    @CustomTableFile(value = "legal_representative_id_card_num", type = "VARCHAR2(255)", comment = " 法定代表人身份证号码 ")
    private String FADINGDAIBIAORSFZHM;
    @CustomTableFile(value = "legal_representative_contact_num", type = "VARCHAR2(255)", comment = " 法定代表人联系电话 ")
    private String FADINGDAIBIAORLXDH;
    @CustomTableFile(value = "economic_type_code", type = "VARCHAR2(50)", comment = " 经济类型代码 ")
    private String JINGJILEIXINGDM;
    @CustomTableFile(value = "registered_city", type = "VARCHAR2(20)", comment = " 注册城市 ")
    private String ZHUCECHENGSHI;
    @CustomTableFile(value = "registered_address", type = "VARCHAR2(255)", comment = " 注册地址 ")
    private String ZHUCEDIZHI;
    @CustomTableFile(value = "postcode", type = "NUMBER(10)", comment = " 邮编 ")
    private String YOUBIAN;
    @CustomTableFile(value = "business_scope", type = "VARCHAR2(4000)", comment = " 营业范围 ")
    private String YINGYEFANWEI;
    @CustomTableFile(value = "head_office_name", type = "VARCHAR2(100)", comment = " 总公司名称 ")
    private String ZONGGONGSIMINGC;
    @CustomTableFile(value = "head_office_unified_social_credit_code", type = "VARCHAR2(18)", comment = " 总公司统一社会信用代码 ")
    private String ZONGGONGSITONGYSHXYDM;
    @CustomTableFile(value = "state", type = "VARCHAR2(20)", comment = " 状态 ")
    private String ZHUANGTAI;
    @CustomTableFile(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @CustomTableFile(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
