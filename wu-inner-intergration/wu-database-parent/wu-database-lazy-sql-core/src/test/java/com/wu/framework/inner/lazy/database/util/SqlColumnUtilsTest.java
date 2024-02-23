package com.wu.framework.inner.lazy.database.util;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.Condition;

import java.util.List;

class SqlColumnUtilsTest {

    public static void main(String[] args) {
        String sql = "SELECT  bs_product_category.avg_purchase_lead_time as avg_purchase_lead_time , bs_product_category.tenant_id as tenant_id , bs_product_category.avg_production_lead_time as avg_production_lead_time , bs_product_category.avg_price as avg_price , bs_product_category.status as status , bs_product_category.pid as pid , bs_product_category.category_code as category_code , bs_product_category.create_user_name as create_user_name , bs_product_category.update_user as update_user , bs_product_category.category_name as category_name , bs_product_category.update_time as update_time , bs_product_category.other_id as other_id , bs_product_category.id as id , bs_product_category.is_delete as is_delete , bs_product_category.update_user_name as update_user_name , bs_product_category.short_name as short_name , bs_product_category.avg_cost as avg_cost , bs_product_category.tenant_code as tenant_code , bs_product_category.create_time as create_time , bs_product_category.tenant_name as tenant_name , bs_product_category.create_user as create_user  from lms_basic.bs_product_category where  bs_product_category.is_delete  =  false  and  bs_product_category.category_code  in  (  'gg'  )";
        List<Condition> conditions = SqlColumnUtils.columnConditionInSql(sql);

        for (Condition condition : conditions) {
            System.out.println(condition.getRowName() + condition.getType() + condition.getRowValue());
        }

        sql = "SELECT  tenant_user.id as id , tenant_user.is_delete as is_delete , tenant_user.work_num as work_num , tenant_user.email as email , tenant_user.full_name as full_name , tenant_user_terrace.user_id as user_id , tenant_user.verifyemail as verifyemail , tenant_user.update_user as update_user , tenant_user.position as position , tenant_user.mobile as mobile , tenant_user.user_name as user_name , tenant_user.verifyphone as verifyphone , tenant_user.wx_union_id as wx_union_id , tenant_user.tenant_id as tenant_id , tenant_user_terrace.create_time as create_time , tenant_user_terrace.update_time as update_time , tenant_user.create_user as create_user , tenant_user_terrace.terrace as terrace , tenant_user.login_name as login_name , tenant_user.password as password , tenant_user.status as status , tenant_user.wx_open_id as wx_open_id  from tenant_user left join  tenant_user_terrace on tenant_user.id  =  tenant_user_terrace.user_id    where  tenant_user.id  = 5617  and  tenant_user.is_delete  = false  and  tenant_user.id   is not  null  and tenant_id= 75478";
        conditions = SqlColumnUtils.columnConditionInSql(sql);

        for (Condition condition : conditions) {
            System.out.println(condition.getRowName() + condition.getType() + condition.getRowValue());
        }

    }
}