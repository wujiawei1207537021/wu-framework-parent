package com.wu.freamwork.controller.ws.domian;


import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import lombok.Data;

@Data
@EasyTable(value = "rn_rcm_csmt_comp_register", comment = "25412信用_公路建设市场企业登记信息表")
public class CompRegister {


    @EasyTableField(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasyTableField(value = "unified_social_credit_code", type = "VARCHAR2(255)", comment = " 统一社会信用代码 ")
    private String TONGYISHEHUIXYDM;
    @EasyTableField(value = "unit_name", type = "VARCHAR2(255)", comment = " 单位名称 ")
    private String DANWEIMINGCHENG;
    @EasyTableField(value = "name_used_before", type = "VARCHAR2(255)", comment = " 曾用名 ")
    private String CENGYONGMING;
    @EasyTableField(value = "abbreviation", type = "VARCHAR2(255)", comment = " 简称 ")
    private String JIANCHENG;
    @EasyTableField(value = "unit_type_code", type = "VARCHAR2(50)", comment = " 单位类型代码 ")
    private String DANWEILEIXINGDM;
    @EasyTableField(value = "establishment_date", type = "DATE", comment = " 成立日期 ")
    private String CHENGLIRIQI;
    @EasyTableField(value = "business_license_registration_date", type = "DATE", comment = " 营业执照注册日期 ")
    private String YINGYEZHIZHAOZCRQ;
    @EasyTableField(value = "business_license_valid_date", type = "DATE", comment = " 营业执照有效日期 ")
    private String YINGYEZHIZHAOYXRQ;
    @EasyTableField(value = "registration_authority", type = "VARCHAR2(100)", comment = " 登记机关 ")
    private String DENGJIJIGUAN;
    @EasyTableField(value = "legal_representative", type = "VARCHAR2(255)", comment = " 法定代表人 ")
    private String FADINGDAIBIAOR;
    @EasyTableField(value = "legal_representative_id_card_num", type = "VARCHAR2(255)", comment = " 法定代表人身份证号码 ")
    private String FADINGDAIBIAORSFZHM;
    @EasyTableField(value = "legal_representative_contact_num", type = "VARCHAR2(255)", comment = " 法定代表人联系电话 ")
    private String FADINGDAIBIAORLXDH;
    @EasyTableField(value = "economic_type_code", type = "VARCHAR2(50)", comment = " 经济类型代码 ")
    private String JINGJILEIXINGDM;
    @EasyTableField(value = "registered_city", type = "VARCHAR2(20)", comment = " 注册城市 ")
    private String ZHUCECHENGSHI;
    @EasyTableField(value = "registered_address", type = "VARCHAR2(255)", comment = " 注册地址 ")
    private String ZHUCEDIZHI;
    @EasyTableField(value = "postcode", type = "NUMBER(10)", comment = " 邮编 ")
    private String YOUBIAN;
    @EasyTableField(value = "business_scope", type = "VARCHAR2(4000)", comment = " 营业范围 ")
    private String YINGYEFANWEI;
    @EasyTableField(value = "head_office_name", type = "VARCHAR2(100)", comment = " 总公司名称 ")
    private String ZONGGONGSIMINGC;
    @EasyTableField(value = "head_office_unified_social_credit_code", type = "VARCHAR2(18)", comment = " 总公司统一社会信用代码 ")
    private String ZONGGONGSITONGYSHXYDM;
    @EasyTableField(value = "state", type = "VARCHAR2(20)", comment = " 状态 ")
    private String ZHUANGTAI;
    @EasyTableField(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasyTableField(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
