package com.wu.freamwork.controller.ws.domian;


import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import lombok.Data;

@Data
@EasySmart(value = "rn_rcm_csmt_pers_rewpun_feedback", comment = "25420信用_公路建设市场人员信用奖惩反馈信息表")
public class PersonRewpunFeedback {


    @EasySmartField(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @EasySmartField(value = "id_card_type_code", type = "VARCHAR2(50)", comment = " 身份证件类型代码 ")
    private String SHENFENZHENGJIANLXDM;
    @EasySmartField(value = "id_card_number", type = "VARCHAR2(18)", comment = " 身份证件号码 ")
    private String SHENFENZHENGJIANHM;
    @EasySmartField(value = "name", type = "VARCHAR2(50)", comment = " 姓名 ")
    private String XINGMING;
    @EasySmartField(value = "trustworthy_dishonest_behavior", type = "VARCHAR2(200)", comment = " 守信/失信行为 ")
    private String SHOUXINSHIXXW;
    @EasySmartField(value = "exec_dept_name", type = "VARCHAR2(100)", comment = " 执行部门名称 ")
    private String ZHIXINGBUMENMC;
    @EasySmartField(value = "start_time", type = "DATE", comment = " 开始时间 ")
    private String KAISHISHIJIAN;
    @EasySmartField(value = "end_time", type = "DATE", comment = " 结束时间 ")
    private String JIESHUSHIJIAN;
    @EasySmartField(value = "implementation_measures", type = "VARCHAR2(100)", comment = " 执行措施 ")
    private String ZHIXINGCUOSHI;
    @EasySmartField(value = "execution_content", type = "VARCHAR2(100)", comment = " 执行内容 ")
    private String ZHIXINGNEIRONG;
    @EasySmartField(value = "field", type = "VARCHAR2(200)", comment = " 所在领域 ")
    private String SUOZAILINGYU;
    @EasySmartField(value = "reward_punishment_type_code", type = "VARCHAR2(1)", comment = " 奖惩类型代码 ")
    private String JIANGCHENGLEIXINGDM;
    @EasySmartField(value = "results", type = "VARCHAR2(200)", comment = " 成效 ")
    private String CHENGXIAO;
    @EasySmartField(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @EasySmartField(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
}
