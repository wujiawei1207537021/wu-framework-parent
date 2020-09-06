package com.wu.freamwork.controller.ws.domian;



import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTable;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTableFile;
import lombok.Data;

@Data
@CustomTable(value = "rn_rcm_csmt_pers_commendation", comment = "25417信用_公路建设市场人员表彰信息表")
public class PersonCommendation {


    @CustomTableFile(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @CustomTableFile(value = "id_card_category_code", type = "VARCHAR2(50)", comment = " 身份证件类别代码 ")
    private String SHENFENZHENGJIANLBDM;
    @CustomTableFile(value = "id_card_number", type = "VARCHAR2(18)", comment = " 身份证件号码 ")
    private String SHENFENZHENGJIANHM;
    @CustomTableFile(value = "name", type = "VARCHAR2(50)", comment = " 姓名 ")
    private String XINGMING;
    @CustomTableFile(value = "commendation_title", type = "VARCHAR2(255)", comment = " 表彰标题 ")
    private String BIAOZHANGBIAOTI;
    @CustomTableFile(value = "commendation_number", type = "VARCHAR2(255)", comment = " 表彰文号 ")
    private String BIAOZHANGWENHAO;
    @CustomTableFile(value = "commendation_year", type = "VARCHAR2(2000)", comment = " 表彰年度 ")
    private String BIAOZHANGNIANDU;
    @CustomTableFile(value = "recognition_level_code", type = "VARCHAR2(255)", comment = " 表彰等级代码 ")
    private String BIAOZHANGDENGJIDM;
    @CustomTableFile(value = "issued_by", type = "VARCHAR2(255)", comment = " 发布单位 ")
    private String FABUDANWEI;
    @CustomTableFile(value = "issue_date", type = "DATE", comment = " 发布日期 ")
    private String FABURIQI;
    @CustomTableFile(value = "commendation_content", type = "VARCHAR2(4000)", comment = " 表彰内容 ")
    private String BIAOZHANGNEIRONG;
    @CustomTableFile(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @CustomTableFile(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @CustomTableFile(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
