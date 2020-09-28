package com.wu.freamwork.controller.ws.domian;



import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
import lombok.Data;

@Data
@EasyTable(value = "rn_rcm_csmt_pers_personal", comment = "25418信用_公路建设市场人员个人信息表")
public class PersonPersonal {


    @EasyTableFile(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasyTableFile(value = "name", type = "VARCHAR2(255)", comment = " 姓名 ")
    private String XINGMING;
    @EasyTableFile(value = "gender", type = "VARCHAR2(10)", comment = " 性别 ")
    private String XINGBIE;
    @EasyTableFile(value = "id_card_category_code", type = "VARCHAR2(50)", comment = " 身份证件类别代码 ")
    private String SHENFENZHENGJIANLBDM;
    @EasyTableFile(value = "id_card_number", type = "VARCHAR2(255)", comment = " 身份证件号码 ")
    private String SHENFENZHENGJIANHM;
    @EasyTableFile(value = "birth_date", type = "DATE", comment = " 出生日期 ")
    private String CHUSHENGRIQI;
    @EasyTableFile(value = "nationality", type = "VARCHAR2(255)", comment = " 国籍 ")
    private String GUOJI;
    @EasyTableFile(value = "unit_name", type = "VARCHAR2(255)", comment = " 单位名称 ")
    private String DANWEIMINGCHENG;
    @EasyTableFile(value = "unit_unified_social_credit_code", type = "VARCHAR2(255)", comment = " 单位统一社会信用代码 ")
    private String DANWEITONGYISHXYDM;
    @EasyTableFile(value = "post", type = "VARCHAR2(255)", comment = " 职务 ")
    private String ZHIWU;
    @EasyTableFile(value = "major_starting_date", type = "DATE", comment = " 从事本专业起始日期 ")
    private String CONGSHIBENZHUANYQSRQ;
    @EasyTableFile(value = "contact_number", type = "VARCHAR2(255)", comment = " 联系电话 ")
    private String LIANXIDIANHUA;
    @EasyTableFile(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @EasyTableFile(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasyTableFile(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
