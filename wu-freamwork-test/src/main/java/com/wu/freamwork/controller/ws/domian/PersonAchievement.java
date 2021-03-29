package com.wu.freamwork.controller.ws.domian;


import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import lombok.Data;

@Data
@EasySmart(value = "rn_rcm_csmt_pers_achievement", comment = "25422信用_公路建设市场人员业绩信息表")
public class PersonAchievement {


    @EasySmartField(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasySmartField(value = "id_card_category_code", type = "VARCHAR2(50)", comment = " 身份证件类别代码 ")
    private String SHENFENZHENGJIANLBDM;
    @EasySmartField(value = "id_card_number", type = "VARCHAR2(18)", comment = " 身份证件号码 ")
    private String SHENFENZHENGJIANHM;
    @EasySmartField(value = "methodName", type = "VARCHAR2(50)", comment = " 姓名 ")
    private String XINGMING;
    @EasySmartField(value = "project_number", type = "VARCHAR2(255)", comment = " 项目编号 ")
    private String XIANGMUBIANHAO;
    @EasySmartField(value = "entry_name", type = "VARCHAR2(255)", comment = " 项目名称 ")
    private String XIANGMUMINGCHENG;
    @EasySmartField(value = "project_type", type = "VARCHAR2(255)", comment = " 项目类型 ")
    private String XIANGMULEIXING;
    @EasySmartField(value = "project_construction_unit", type = "VARCHAR2(255)", comment = " 项目建设单位 ")
    private String XIANGMUJIANSHEDW;
    @EasySmartField(value = "lot_no", type = "VARCHAR2(255)", comment = " 标段编号 ")
    private String BIAODUANBIANHAO;
    @EasySmartField(value = "bid_section_name", type = "VARCHAR2(255)", comment = " 标段名称 ")
    private String BIAODUANMINGCHENG;
    @EasySmartField(value = "lot_type_code", type = "VARCHAR2(255)", comment = " 标段类型代码 ")
    private String BIAODUANLEIXINGDM;
    @EasySmartField(value = "lot_status", type = "VARCHAR2(10)", comment = " 标段状态 ")
    private String BIAODUANZHUANGTAI;
    @EasySmartField(value = "performance_unit", type = "VARCHAR2(100)", comment = " 业绩所属单位 ")
    private String YEJISUOSHUDW;
    @EasySmartField(value = "job_code", type = "VARCHAR2(50)", comment = " 工作岗位代码 ")
    private String GONGZUOGANGWEIDM;
    @EasySmartField(value = "start_date", type = "DATE", comment = " 在岗起始日期 ")
    private String ZAIGANGQISHIRQ;
    @EasySmartField(value = "end_date", type = "DATE", comment = " 在岗结束日期 ")
    private String ZAIGANGJIESHURQ;
    @EasySmartField(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasySmartField(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
