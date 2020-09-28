package com.wu.freamwork.controller.ws.domian;



import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
import lombok.Data;

/**
 * description  信用_公路建设市场企业业绩信息表
 * @author 吴佳伟
 * @date 2020/7/14 下午1:34
 */
@Data
@EasyTable(value = "rn_rcm_csmt_comp_achievement", comment = "信用_公路建设市场企业业绩信息表 ")
public class CompAchievement {


    @EasyTableFile(value = "data_id", type = "VARCHAR2(200)", comment = " IDguid ")
    private String ID;
    @EasyTableFile(value = "unified_social_credit_code", type = "VARCHAR2(18)", comment = " 统一社会信用代码 ")
    private String TONGYISHEHUIXYDM;
    @EasyTableFile(value = "project_number", type = "VARCHAR2(255)", comment = " 项目编号 ")
    private String XIANGMUBIANHAO;
    @EasyTableFile(value = "entry_name", type = "VARCHAR2(255)", comment = " 项目名称 ")
    private String XIANGMUMINGCHENG;
    @EasyTableFile(value = "construction_unit", type = "VARCHAR2(200)", comment = " 建设单位 ")
    private String JIANSHEDANWEI;
    @EasyTableFile(value = "lot_no", type = "VARCHAR2(100)", comment = " 标段编号 ")
    private String BIAODUANBIANHAO;
    @EasyTableFile(value = "bid_section_name", type = "VARCHAR2(200)", comment = " 标段名称 ")
    private String BIAODUANMINGCHENG;
    @EasyTableFile(value = "lot_type_code", type = "VARCHAR2(20)", comment = " 标段类型代码 ")
    private String BIAODUANLEIXINGDM;
    @EasyTableFile(value = "project_location_code", type = "VARCHAR2(6)", comment = " 项目所在地代码 ")
    private String XIANGMUSUOZAIDDM;
    @EasyTableFile(value = "project_overview", type = "VARCHAR2(4000)", comment = " 项目概述 ")
    private String XIANGMUGAISHU;
    @EasyTableFile(value = "performance_reporting_type", type = "VARCHAR2(50)", comment = " 业绩填报类型 ")
    private String YEJITIANBAOLX;
    @EasyTableFile(value = "contract_amount", type = "NUMBER(20)", comment = " 合同金额 ")
    private String HETONGJINE;
    @EasyTableFile(value = "settlement_price", type = "NUMBER(20)", comment = " 结算价 ")
    private String JIESUANJIA;
    @EasyTableFile(value = "construction_scale", type = "VARCHAR2(4000)", comment = " 建设规模 ")
    private String JIANSHEGUIMO;
    @EasyTableFile(value = "main_construction_contents", type = "VARCHAR2(4000)", comment = " 主要建设内容 ")
    private String ZHUYAOJIANSHENR;
    @EasyTableFile(value = "actual_commencement_date", type = "DATE", comment = " 实际开工日期 ")
    private String SHIJIKAIGONGRQ;
    @EasyTableFile(value = "planned_delivery_acceptance_date", type = "DATE", comment = " 计划交工验收日期 ")
    private String JIHUAJIAOGONGYSRQ;
    @EasyTableFile(value = "delivery_acceptance_date", type = "DATE", comment = " 交工验收日期 ")
    private String JIAOGONGYANSHOURQ;
    @EasyTableFile(value = "completion_date", type = "DATE", comment = " 竣工日期 ")
    private String JUNGONGRIQI;
    @EasyTableFile(value = "completion_quality", type = "VARCHAR2(255)", comment = " 竣工质量 ")
    private String JUNGONGZHILIANG;
    @EasyTableFile(value = "project_leader_name", type = "VARCHAR2(30)", comment = " 项目负责人姓名 ")
    private String XIANGMUFUZERXM;
    @EasyTableFile(value = "project_leader_id_card_num", type = "VARCHAR2(18)", comment = " 项目负责人身份证号码 ")
    private String XIANGMUFUZERSFZHM;
    @EasyTableFile(value = "technical_director_name", type = "VARCHAR2(30)", comment = " 技术负责人姓名 ")
    private String JISHUFUZERXM;
    @EasyTableFile(value = "technical_director_id_card_num", type = "VARCHAR2(18)", comment = " 技术负责人身份证号码 ")
    private String JISHUFUZERSFZHM;
    @EasyTableFile(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @EasyTableFile(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasyTableFile(value = "collection_time", type = "VARCHAR2(200)", comment = " 采集时间 ")
    private String BEGINTIME;
}
