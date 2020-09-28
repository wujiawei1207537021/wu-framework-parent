package com.wu.freamwork.controller.ws.domian;




import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
import lombok.Data;

@Data
@EasyTable(value = "rn_rcm_csmt_pers_achievement", comment = "25422信用_公路建设市场人员业绩信息表")
public class PersonAchievement {


    @EasyTableFile(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasyTableFile(value = "id_card_category_code", type = "VARCHAR2(50)", comment = " 身份证件类别代码 ")
    private String SHENFENZHENGJIANLBDM;
    @EasyTableFile(value = "id_card_number", type = "VARCHAR2(18)", comment = " 身份证件号码 ")
    private String SHENFENZHENGJIANHM;
    @EasyTableFile(value = "name", type = "VARCHAR2(50)", comment = " 姓名 ")
    private String XINGMING;
    @EasyTableFile(value = "project_number", type = "VARCHAR2(255)", comment = " 项目编号 ")
    private String XIANGMUBIANHAO;
    @EasyTableFile(value = "entry_name", type = "VARCHAR2(255)", comment = " 项目名称 ")
    private String XIANGMUMINGCHENG;
    @EasyTableFile(value = "project_type", type = "VARCHAR2(255)", comment = " 项目类型 ")
    private String XIANGMULEIXING;
    @EasyTableFile(value = "project_construction_unit", type = "VARCHAR2(255)", comment = " 项目建设单位 ")
    private String XIANGMUJIANSHEDW;
    @EasyTableFile(value = "lot_no", type = "VARCHAR2(255)", comment = " 标段编号 ")
    private String BIAODUANBIANHAO;
    @EasyTableFile(value = "bid_section_name", type = "VARCHAR2(255)", comment = " 标段名称 ")
    private String BIAODUANMINGCHENG;
    @EasyTableFile(value = "lot_type_code", type = "VARCHAR2(255)", comment = " 标段类型代码 ")
    private String BIAODUANLEIXINGDM;
    @EasyTableFile(value = "lot_status", type = "VARCHAR2(10)", comment = " 标段状态 ")
    private String BIAODUANZHUANGTAI;
    @EasyTableFile(value = "performance_unit", type = "VARCHAR2(100)", comment = " 业绩所属单位 ")
    private String YEJISUOSHUDW;
    @EasyTableFile(value = "job_code", type = "VARCHAR2(50)", comment = " 工作岗位代码 ")
    private String GONGZUOGANGWEIDM;
    @EasyTableFile(value = "start_date", type = "DATE", comment = " 在岗起始日期 ")
    private String ZAIGANGQISHIRQ;
    @EasyTableFile(value = "end_date", type = "DATE", comment = " 在岗结束日期 ")
    private String ZAIGANGJIESHURQ;
    @EasyTableFile(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
    @EasyTableFile(value = "begin_time", type = "VARCHAR2(200)", comment = "  ")
    private String BEGINTIME;
}
