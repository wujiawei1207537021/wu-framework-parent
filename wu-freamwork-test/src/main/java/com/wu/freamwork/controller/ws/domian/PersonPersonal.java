package com.wu.freamwork.controller.ws.domian;


import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import lombok.Data;

@Data
@EasySmart(value = "rn_rcm_csmt_pers_personal", comment = "25418信用_公路建设市场人员个人信息表")
public class PersonPersonal {


    @EasySmartField(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasySmartField(value = "name", type = "VARCHAR2(255)", comment = " 姓名 ")
    private String XINGMING;
    @EasySmartField(value = "gender", type = "VARCHAR2(10)", comment = " 性别 ")
    private String XINGBIE;
    @EasySmartField(value = "id_card_category_code", type = "VARCHAR2(50)", comment = " 身份证件类别代码 ")
    private String SHENFENZHENGJIANLBDM;
    @EasySmartField(value = "id_card_number", type = "VARCHAR2(255)", comment = " 身份证件号码 ")
    private String SHENFENZHENGJIANHM;
    @EasySmartField(value = "birth_date", type = "DATE", comment = " 出生日期 ")
    private String CHUSHENGRIQI;
    @EasySmartField(value = "nationality", type = "VARCHAR2(255)", comment = " 国籍 ")
    private String GUOJI;
    @EasySmartField(value = "unit_name", type = "VARCHAR2(255)", comment = " 单位名称 ")
    private String DANWEIMINGCHENG;
    @EasySmartField(value = "unit_unified_social_credit_code", type = "VARCHAR2(255)", comment = " 单位统一社会信用代码 ")
    private String DANWEITONGYISHXYDM;
    @EasySmartField(value = "post", type = "VARCHAR2(255)", comment = " 职务 ")
    private String ZHIWU;
    @EasySmartField(value = "major_starting_date", type = "DATE", comment = " 从事本专业起始日期 ")
    private String CONGSHIBENZHUANYQSRQ;
    @EasySmartField(value = "contact_number", type = "VARCHAR2(255)", comment = " 联系电话 ")
    private String LIANXIDIANHUA;
    @EasySmartField(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @EasySmartField(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasySmartField(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
