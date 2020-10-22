package com.wu.freamwork.controller.ws.domian;


import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import lombok.Data;

@Data
@EasyTable(value = "rn_rcm_csmt_pers_professional_qualification", comment = "25423信用_公路建设市场人员职业资格信息表")
public class PersonProfessionalQualification {


    @EasyTableField(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasyTableField(value = "id_card_category_code", type = "VARCHAR2(50)", comment = " 身份证件类别代码 ")
    private String SHENFENZHENGJIANLBDM;
    @EasyTableField(value = "id_card_number", type = "VARCHAR2(18)", comment = " 身份证件号码 ")
    private String SHENFENZHENGJIANHM;
    @EasyTableField(value = "name", type = "VARCHAR2(50)", comment = " 姓名 ")
    private String XINGMING;
    @EasyTableField(value = "practice_qualification_name", type = "VARCHAR2(255)", comment = " 执业资格名称 ")
    private String ZHIYEZIGEMC;
    @EasyTableField(value = "qualification_level", type = "VARCHAR2(10)", comment = " 执业资格等级 ")
    private String ZHIYEZIGEDJ;
    @EasyTableField(value = "registration_certificate_no", type = "VARCHAR2(255)", comment = " 注册证书编号 ")
    private String ZHUCEZHENGSHUBH;
    @EasyTableField(value = "certification_unit", type = "VARCHAR2(255)", comment = " 发证单位 ")
    private String FAZHENGDANWEI;
    @EasyTableField(value = "issue_date", type = "VARCHAR2(255)", comment = " 发证日期 ")
    private String FAZHENGRIQI;
    @EasyTableField(value = "registration_category_code", type = "VARCHAR2(255)", comment = " 注册类别代码 ")
    private String ZHUCELEIBIEDM;
    @EasyTableField(value = "registered_professional_code", type = "VARCHAR2(50)", comment = " 注册专业代码 ")
    private String ZHUCEZHUANYEDM;
    @EasyTableField(value = "registration_level_code", type = "VARCHAR2(50)", comment = " 注册等级代码 ")
    private String ZHUCEDENGJIDM;
    @EasyTableField(value = "registration_date", type = "DATE", comment = " 注册日期 ")
    private String ZHUCERIQI;
    @EasyTableField(value = "effective_registration_date", type = "DATE", comment = " 注册有效起始日 ")
    private String ZHUCEYOUXIAOQSR;
    @EasyTableField(value = "registration_valid_until", type = "DATE", comment = " 注册有效期至 ")
    private String ZHUCEYOUXIAOQZ;
    @EasyTableField(value = "qualification_status", type = "VARCHAR2(10)", comment = " 资格状态 ")
    private String ZIGEZHUANGTAI;
    @EasyTableField(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
