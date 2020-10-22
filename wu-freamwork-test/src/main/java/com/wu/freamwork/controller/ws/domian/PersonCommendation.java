package com.wu.freamwork.controller.ws.domian;


import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import lombok.Data;

@Data
@EasyTable(value = "rn_rcm_csmt_pers_commendation", comment = "25417信用_公路建设市场人员表彰信息表")
public class PersonCommendation {


    @EasyTableField(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasyTableField(value = "id_card_category_code", type = "VARCHAR2(50)", comment = " 身份证件类别代码 ")
    private String SHENFENZHENGJIANLBDM;
    @EasyTableField(value = "id_card_number", type = "VARCHAR2(18)", comment = " 身份证件号码 ")
    private String SHENFENZHENGJIANHM;
    @EasyTableField(value = "name", type = "VARCHAR2(50)", comment = " 姓名 ")
    private String XINGMING;
    @EasyTableField(value = "commendation_title", type = "VARCHAR2(255)", comment = " 表彰标题 ")
    private String BIAOZHANGBIAOTI;
    @EasyTableField(value = "commendation_number", type = "VARCHAR2(255)", comment = " 表彰文号 ")
    private String BIAOZHANGWENHAO;
    @EasyTableField(value = "commendation_year", type = "VARCHAR2(2000)", comment = " 表彰年度 ")
    private String BIAOZHANGNIANDU;
    @EasyTableField(value = "recognition_level_code", type = "VARCHAR2(255)", comment = " 表彰等级代码 ")
    private String BIAOZHANGDENGJIDM;
    @EasyTableField(value = "issued_by", type = "VARCHAR2(255)", comment = " 发布单位 ")
    private String FABUDANWEI;
    @EasyTableField(value = "issue_date", type = "DATE", comment = " 发布日期 ")
    private String FABURIQI;
    @EasyTableField(value = "commendation_content", type = "VARCHAR2(4000)", comment = " 表彰内容 ")
    private String BIAOZHANGNEIRONG;
    @EasyTableField(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @EasyTableField(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasyTableField(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
