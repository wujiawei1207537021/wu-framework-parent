package com.wu.freamwork.controller.ws.domian;


import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import lombok.Data;

@Data
@EasyTable(value = "rn_rcm_csmt_pers_reward", comment = "25419信用_公路建设市场人员奖励信息表")
public class PersonReward {


    @EasyTableField(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasyTableField(value = "id_card_category_code", type = "VARCHAR2(50)", comment = " 身份证件类别代码 ")
    private String SHENFENZHENGJIANLBDM;
    @EasyTableField(value = "id_card_number", type = "VARCHAR2(18)", comment = " 身份证件号码 ")
    private String SHENFENZHENGJIANHM;
    @EasyTableField(value = "name", type = "VARCHAR2(50)", comment = " 姓名 ")
    private String XINGMING;
    @EasyTableField(value = "award_name", type = "VARCHAR2(255)", comment = " 奖项名称 ")
    private String JIANGXIANGMINGCHENG;
    @EasyTableField(value = "award_year", type = "VARCHAR2(4)", comment = " 奖项年度 ")
    private String JIANGXIANGNIANDU;
    @EasyTableField(value = "award_category_code", type = "VARCHAR2(255)", comment = " 奖项类别代码 ")
    private String JIANGXIANGLEIBIEDM;
    @EasyTableField(value = "award_level_code", type = "VARCHAR2(50)", comment = " 获奖等级代码 ")
    private String HUOJIANGDENGJIDM;
    @EasyTableField(value = "certificate_number", type = "VARCHAR2(255)", comment = " 证书编号 ")
    private String ZHENGSHUBIANHAO;
    @EasyTableField(value = "personal_ranking", type = "VARCHAR2(255)", comment = " 个人排名 ")
    private String GERENPAIMING;
    @EasyTableField(value = "awarding_unit", type = "VARCHAR2(255)", comment = " 颁奖单位 ")
    private String BANJIANGDANWEI;
    @EasyTableField(value = "award_time", type = "DATE", comment = " 获奖时间 ")
    private String HUOJIANGSHIJIAN;
    @EasyTableField(value = "award_content", type = "VARCHAR2(1000)", comment = " 奖项内容 ")
    private String JIANGXIANGNEIRONG;
    @EasyTableField(value = "participation_property_code", type = "VARCHAR2(255)", comment = " 参建属性代码 ")
    private String CANJIANSHUXINGDM;
    @EasyTableField(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @EasyTableField(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasyTableField(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
