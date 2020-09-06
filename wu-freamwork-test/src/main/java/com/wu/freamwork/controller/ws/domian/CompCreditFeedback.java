package com.wu.freamwork.controller.ws.domian;



import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTable;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTableFile;
import lombok.Data;

@Data
@CustomTable(value = "rn_rcm_csmt_comp_credit_reward_punishment_feedback", comment = "25414信用_公路建设市场企业信用奖惩反馈信息表")
public class CompCreditFeedback {


    @CustomTableFile(value = "data_id", type = "VARCHAR2(200)", comment = " guid ")
    private String ID;
    @CustomTableFile(value = "unified_social_credit_code", type = "VARCHAR2(18)", comment = " 统一社会信用代码 ")
    private String TONGYISHEHUIXYDM;
    @CustomTableFile(value = "rewards_punishments_object", type = "VARCHAR2(100)", comment = " 奖惩对象 ")
    private String JIANGCHENGDUIXIANG;
    @CustomTableFile(value = "trustworthy_dishonest_behavior", type = "VARCHAR2(200)", comment = " 守信/失信行为 ")
    private String SHOUXINSHIXXW;
    @CustomTableFile(value = "exec_dept_name", type = "VARCHAR2(100)", comment = " 执行部门名称 ")
    private String ZHIXINGBUMENMC;
    @CustomTableFile(value = "start_time", type = "DATE", comment = " 开始时间 ")
    private String KAISHISHIJIAN;
    @CustomTableFile(value = "end_time", type = "DATE", comment = " 结束时间 ")
    private String JIESHUSHIJIAN;
    @CustomTableFile(value = "implementation_measures", type = "VARCHAR2(100)", comment = " 执行措施 ")
    private String ZHIXINGCUOSHI;
    @CustomTableFile(value = "execution_content", type = "VARCHAR2(100)", comment = " 执行内容 ")
    private String ZHIXINGNEIRONG;
    @CustomTableFile(value = "field", type = "VARCHAR2(200)", comment = " 所在领域 ")
    private String SUOZAILINGYU;
    @CustomTableFile(value = "reward_punishment_type_code", type = "VARCHAR2(1)", comment = " 奖惩类型代码 ")
    private String JIANGCHENGLEIXINGDM;
    @CustomTableFile(value = "results", type = "VARCHAR2(200)", comment = " 成效 ")
    private String CHENGXIAO;
    @CustomTableFile(value = "state", type = "VARCHAR2(10)", comment = " 状态 ")
    private String ZHUANGTAI;
    @CustomTableFile(value = "data_export_time", type = "DATE", comment = " 数据导出时间 ")
    private String SHUJUDAOCHUSJ;
}
