package com.wu.freamwork.controller.ws.domian;



import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTable;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTableFile;
import lombok.Data;

@Data
@CustomTable(value = "rn_rcm_csmt_pers_reward", comment = "25419信用_公路建设市场人员奖励信息表")
public class PersonReward {


    @CustomTableFile(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @CustomTableFile(value = "id_card_category_code", type = "VARCHAR2(50)", comment = " 身份证件类别代码 ")
    private String SHENFENZHENGJIANLBDM;
    @CustomTableFile(value = "id_card_number", type = "VARCHAR2(18)", comment = " 身份证件号码 ")
    private String SHENFENZHENGJIANHM;
    @CustomTableFile(value = "name", type = "VARCHAR2(50)", comment = " 姓名 ")
    private String XINGMING;
    @CustomTableFile(value = "award_name", type = "VARCHAR2(255)", comment = " 奖项名称 ")
    private String JIANGXIANGMINGCHENG;
    @CustomTableFile(value = "award_year", type = "VARCHAR2(4)", comment = " 奖项年度 ")
    private String JIANGXIANGNIANDU;
    @CustomTableFile(value = "award_category_code", type = "VARCHAR2(255)", comment = " 奖项类别代码 ")
    private String JIANGXIANGLEIBIEDM;
    @CustomTableFile(value = "award_level_code", type = "VARCHAR2(50)", comment = " 获奖等级代码 ")
    private String HUOJIANGDENGJIDM;
    @CustomTableFile(value = "certificate_number", type = "VARCHAR2(255)", comment = " 证书编号 ")
    private String ZHENGSHUBIANHAO;
    @CustomTableFile(value = "personal_ranking", type = "VARCHAR2(255)", comment = " 个人排名 ")
    private String GERENPAIMING;
    @CustomTableFile(value = "awarding_unit", type = "VARCHAR2(255)", comment = " 颁奖单位 ")
    private String BANJIANGDANWEI;
    @CustomTableFile(value = "award_time", type = "DATE", comment = " 获奖时间 ")
    private String HUOJIANGSHIJIAN;
    @CustomTableFile(value = "award_content", type = "VARCHAR2(1000)", comment = " 奖项内容 ")
    private String JIANGXIANGNEIRONG;
    @CustomTableFile(value = "participation_property_code", type = "VARCHAR2(255)", comment = " 参建属性代码 ")
    private String CANJIANSHUXINGDM;
    @CustomTableFile(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @CustomTableFile(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @CustomTableFile(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
