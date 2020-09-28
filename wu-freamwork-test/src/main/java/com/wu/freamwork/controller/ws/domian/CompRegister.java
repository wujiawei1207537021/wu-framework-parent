package com.wu.freamwork.controller.ws.domian;



import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
import lombok.Data;

@Data
@EasyTable(value = "rn_rcm_csmt_comp_register", comment = "25412信用_公路建设市场企业登记信息表")
public class CompRegister {


    @EasyTableFile(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasyTableFile(value = "unified_social_credit_code", type = "VARCHAR2(255)", comment = " 统一社会信用代码 ")
    private String TONGYISHEHUIXYDM;
    @EasyTableFile(value = "unit_name", type = "VARCHAR2(255)", comment = " 单位名称 ")
    private String DANWEIMINGCHENG;
    @EasyTableFile(value = "name_used_before", type = "VARCHAR2(255)", comment = " 曾用名 ")
    private String CENGYONGMING;
    @EasyTableFile(value = "abbreviation", type = "VARCHAR2(255)", comment = " 简称 ")
    private String JIANCHENG;
    @EasyTableFile(value = "unit_type_code", type = "VARCHAR2(50)", comment = " 单位类型代码 ")
    private String DANWEILEIXINGDM;
    @EasyTableFile(value = "establishment_date", type = "DATE", comment = " 成立日期 ")
    private String CHENGLIRIQI;
    @EasyTableFile(value = "business_license_registration_date", type = "DATE", comment = " 营业执照注册日期 ")
    private String YINGYEZHIZHAOZCRQ;
    @EasyTableFile(value = "business_license_valid_date", type = "DATE", comment = " 营业执照有效日期 ")
    private String YINGYEZHIZHAOYXRQ;
    @EasyTableFile(value = "registration_authority", type = "VARCHAR2(100)", comment = " 登记机关 ")
    private String DENGJIJIGUAN;
    @EasyTableFile(value = "legal_representative", type = "VARCHAR2(255)", comment = " 法定代表人 ")
    private String FADINGDAIBIAOR;
    @EasyTableFile(value = "legal_representative_id_card_num", type = "VARCHAR2(255)", comment = " 法定代表人身份证号码 ")
    private String FADINGDAIBIAORSFZHM;
    @EasyTableFile(value = "legal_representative_contact_num", type = "VARCHAR2(255)", comment = " 法定代表人联系电话 ")
    private String FADINGDAIBIAORLXDH;
    @EasyTableFile(value = "economic_type_code", type = "VARCHAR2(50)", comment = " 经济类型代码 ")
    private String JINGJILEIXINGDM;
    @EasyTableFile(value = "registered_city", type = "VARCHAR2(20)", comment = " 注册城市 ")
    private String ZHUCECHENGSHI;
    @EasyTableFile(value = "registered_address", type = "VARCHAR2(255)", comment = " 注册地址 ")
    private String ZHUCEDIZHI;
    @EasyTableFile(value = "postcode", type = "NUMBER(10)", comment = " 邮编 ")
    private String YOUBIAN;
    @EasyTableFile(value = "business_scope", type = "VARCHAR2(4000)", comment = " 营业范围 ")
    private String YINGYEFANWEI;
    @EasyTableFile(value = "head_office_name", type = "VARCHAR2(100)", comment = " 总公司名称 ")
    private String ZONGGONGSIMINGC;
    @EasyTableFile(value = "head_office_unified_social_credit_code", type = "VARCHAR2(18)", comment = " 总公司统一社会信用代码 ")
    private String ZONGGONGSITONGYSHXYDM;
    @EasyTableFile(value = "state", type = "VARCHAR2(20)", comment = " 状态 ")
    private String ZHUANGTAI;
    @EasyTableFile(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasyTableFile(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
