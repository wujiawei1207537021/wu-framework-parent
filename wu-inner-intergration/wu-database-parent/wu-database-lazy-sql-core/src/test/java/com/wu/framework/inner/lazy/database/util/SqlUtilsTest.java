package com.wu.framework.inner.lazy.database.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class SqlUtilsTest {

    public static void main(String[] args) {
        String selectSql1 = "select * from students1,course2 ;";
        String selectSql2 = "select * from students1-1 inner join course2-1;";
        String sql = "UPDATE product3-1 p, product_price4-1 pp SET pp.price = p.price * 0.8 WHERE p.productid= pp.productId; " +
                "     * UPDATE product5-1 p INNER JOIN product_price6-1 pp ON p.productid= pp.productid SET pp.price = p.price * 0.8; " +
                "     * UPDATE product7-1 p LEFT JOIN product_price8-1 pp ON p.productid= pp.productid SET p.isdelete = 1 WHERE pp.productid IS NULL;";
        String insertSql = "insert into table (xx,x) values(1,2)";

        List<String> tables = SqlUtils.tablesInSql(Arrays.asList(selectSql1, selectSql2, sql, insertSql));
//        tables=SqlUtils.selectTable(selectSql1);
//        tables=SqlUtils.updateTable(sql);
//        tables= SqlUtils.selectTable(selectSql2);
        System.out.println(tables);

        String countSql = " SELECT COUNT(*) FROM (SELECT u.id, u.user_name AS userName, GROUP_CONCAT(DISTINCT r.`name` SEPARATOR ',') AS roleName, GROUP_CONCAT(DISTINCT d.`name` SEPARATOR ',') AS deptName, GROUP_CONCAT(DISTINCT r.`id` SEPARATOR ',') AS roleId, GROUP_CONCAT(DISTINCT d.`id` SEPARATOR ',') AS deptId, ttu.work_num AS workNum, u.mobile, ttu.position, ttu.email, u.`status`, u.wx_open_id AS wxOpenId, t.id AS tenantId, t.name AS tenantName FROM `tenant_user` u LEFT JOIN tenant_tenant_user ttu ON u.id = ttu.user_id AND ttu.is_delete = false LEFT JOIN tenant t ON t.id = ttu.tenant_id AND t.is_delete = false LEFT JOIN tenant_user_role ur ON u.id = ur.user_id AND ur.is_delete = false LEFT JOIN tenant_user_dept ud ON u.id = ud.user_id AND ud.is_delete = false LEFT JOIN tenant_role r ON r.id = ur.role_id AND r.is_delete = FALSE AND r.tenant_id = t.id LEFT JOIN tenant_dept d ON d.id = ud.dept_id AND d.is_delete = FALSE AND d.tenant_id = t.id WHERE u.is_delete = false GROUP BY u.id, t.id ORDER BY u.create_time DESC) TOTAL";

        String selectSql3 = "SELECT " +
                " u.id, " +
                " t.id AS tenantId, " +
                " t.NAME AS tenantName, " +
                " t.logo_img AS tenantLogoUrl, " +
                " u.user_name AS userName, " +
                " ( " +
                " SELECT " +
                " GROUP_CONCAT( DISTINCT r.`name` SEPARATOR ',' )  " +
                " FROM " +
                " tenant_role r " +
                " LEFT JOIN tenant_user_role ur ON r.id = ur.role_id " +
                " LEFT JOIN tenant_user u ON ur.user_id = u.id  " +
                " WHERE " +
                " r.tenant_id = ?  " +
                " AND u.id = ?  " +
                " AND ur.is_delete = FALSE  " +
                " AND u.is_delete = FALSE  " +
                " ) AS roleName  " +
                "FROM " +
                " `tenant_user` u " +
                " LEFT JOIN tenant_tenant_user ttu ON u.id = ttu.user_id  " +
                " AND ttu.is_delete = " +
                " FALSE LEFT JOIN tenant t ON t.id = ttu.tenant_id  " +
                " AND t.is_delete = FALSE  " +
                "WHERE " +
                " u.id = ?  " +
                " AND t.id = ?";
        List<String> countTable = SqlUtils.tablesInSql(countSql);
        System.out.println("count sql table:" + countTable);
        List<String> selectSQL3Tables = SqlUtils.tablesInSql(selectSql3);
        System.out.println("select sql3 " + selectSQL3Tables);
        String upsertSql = "insert into tenant_user_terrace (user_id,terrace,is_delete,tenant_id) VALUES (2,'MIDDLEGROUND',false,31)  ON DUPLICATE KEY UPDATE \n" +
                " user_id=values (user_id),terrace=values (terrace),is_delete=values (is_delete),tenant_id=values (tenant_id)";
        List<String> upsertSqlTable = SqlUtils.tablesInSql(upsertSql);
        System.out.println(upsertSqlTable);

        // ### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column 't2.inventory_organization_id' in 'field list'
        //### The error may exist in class path resource [mapper/PurchaseDpOrderMapper.xml]
        //### The error may involve defaultParameterMap
        //### The error occurred while setting parameters
        //### SQL: SELECT COUNT(*) FROM (select         t1.*,         t2.actual_collection_money         ,t2.arrival_amount          ,t2.discount_money         ,t2.discount_unit_price         ,t2.dp_order_id          ,t2.loss_amount         ,t2.loss_money         ,t2.loss_scheme         ,t2.order_goods_id         ,t2.out_amount         ,t2.out_money         ,t2.out_price         ,t2.product_code         ,t2.product_id         ,t2.product_name         ,t2.product_price           ,t2.send_amount         ,t2.settlement_amount         ,t2.settlement_money         ,t2.settlement_price           ,t2.transport_price          ,t2.inventory_organization_id         ,t2.inventory_organization_name         ,t2.meter_no         ,t2.model_material         ,t2.order_no         ,t2.other_amount         ,t2.purchase_company_id         ,t2.purchase_company_name         ,t2.receive_warehouse_id         ,t2.receive_warehouse_name         ,t2.requirement_dept_id         ,t2.requirement_dept_name         ,t2.specification         ,t2.tax_rate         ,t2.unit_code         ,t2.unit_id         ,t2.unit_name         ,t2.unload_method_code         ,t2.net           from         purchase_dp_order t1         inner join         purchase_dp_order_goods t2 on t1.id = t2.dp_order_id and t2.is_delete = false         where         t1.is_delete = false                                                                                                                                                                                                     and                                                                                                                    (t1.weight_exception_code=3 or t1.weight_exception_code is null)) TOTAL

        String error = "### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column 't2.inventory_organization_id' in 'field list'\n" +
                "### The error may exist in class path resource [mapper/PurchaseDpOrderMapper.xml]\n" +
                "### The error may involve defaultParameterMap\n" +
                "### The error occurred while setting parameters\n" +
                "### SQL: SELECT COUNT(*) FROM (select         t1.*,         t2.actual_collection_money         ,t2.arrival_amount          ,t2.discount_money         ,t2.discount_unit_price         ,t2.dp_order_id          ,t2.loss_amount         ,t2.loss_money         ,t2.loss_scheme         ,t2.order_goods_id         ,t2.out_amount         ,t2.out_money         ,t2.out_price         ,t2.product_code         ,t2.product_id         ,t2.product_name         ,t2.product_price           ,t2.send_amount         ,t2.settlement_amount         ,t2.settlement_money         ,t2.settlement_price           ,t2.transport_price          ,t2.inventory_organization_id         ,t2.inventory_organization_name         ,t2.meter_no         ,t2.model_material         ,t2.order_no         ,t2.other_amount         ,t2.purchase_company_id         ,t2.purchase_company_name         ,t2.receive_warehouse_id         ,t2.receive_warehouse_name         ,t2.requirement_dept_id         ,t2.requirement_dept_name         ,t2.specification         ,t2.tax_rate         ,t2.unit_code         ,t2.unit_id         ,t2.unit_name         ,t2.unload_method_code         ,t2.net           from         purchase_dp_order t1         inner join         purchase_dp_order_goods t2 on t1.id = t2.dp_order_id and t2.is_delete = false         where         t1.is_delete = false                                                                                                                                                                                                     and                                                                                                                    (t1.weight_exception_code=3 or t1.weight_exception_code is null)) TOTAL";
        List<String> errorTable = SqlUtils.tablesInSql(error);
        System.out.println("error:" + errorTable);

        String xx = "SELECT tenant_role.* FROM tenant_user_role LEFT JOIN tenant_role ON tenant_user_role.role_id = tenant_role.id AND tenant_id = 2578 WHERE tenant_role.id IS NOT NULL AND user_id = ? AND tenant_user_role.is_delete = FALSE AND tenant_id = 2578";
        List<String> tableList = SqlUtils.tablesInSql(xx);
        for (String table : tableList) {
            System.out.println("table " + table);
        }
        xx = "SELECT  tenant_address.country as country , tenant_address.create_user as create_user , tenant_address.is_default as is_default , tenant_address.address_name as address_name , tenant_address.tenant_id as tenant_id , tenant_address.id as id , tenant_address.status as status , tenant_address.detailed as detailed , tenant_address.province as province , tenant_address.update_user as update_user , tenant_address.city_code as city_code , tenant_address.city as city , tenant_address.update_time as update_time , tenant_address.area as area , tenant_address.description as description , tenant_address.is_delete as is_delete , tenant_address.province_code as province_code , tenant_address.country_code as country_code , tenant_address.area_code as area_code , tenant_address.create_time as create_time  from tenant_address where  tenant_address.tenant_id  = 75478  and  tenant_address.is_default  = true  and  tenant_address.is_delete  = false  and  tenant_address.tenant_id  = '75478'  limit 1 ";
        tableList = SqlUtils.tablesInSql(xx);
        for (String table : tableList) {
            System.out.println("table " + table);
        }
    }

    @Test
    void tablesInSql() {

    }
}