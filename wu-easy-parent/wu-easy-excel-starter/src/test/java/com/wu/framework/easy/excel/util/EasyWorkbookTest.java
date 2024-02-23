package com.wu.framework.easy.excel.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wu.framework.easy.excel.adapter.ExcelExcelServiceAdapter;
import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.easy.excel.factory.ExcelExcelServiceAdapterFactory;
import com.wu.framework.easy.excel.util.demo.UserDemo;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.layer.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/08/03 11:13
 */
public class EasyWorkbookTest {
    public static void main(String[] args) throws IOException {
        ExcelExcelServiceAdapter excelExcelServiceAdapter = ExcelExcelServiceAdapterFactory.excelExcelServiceAdapter();

        String localClassPath = FileUtil.readLocalClassFolder(EasyWorkbookTest.class);
        File easyExcelWorkbookTest = FileUtil.createFile(localClassPath, "EasyExcelWorkbookTest.xls");
        EasyExcelPoint easyExcelPoint = new EasyExcelPoint();
        easyExcelPoint.setUseAnnotation(false);
        FileOutputStream fileOutputStream = new FileOutputStream(easyExcelWorkbookTest.getPath());

        UserDemo userDemo = new UserDemo();
        userDemo.setId(1);
        userDemo.setUserName("SN");
        userDemo.setBirthday(LocalDate.now());
        UserDemo.CityDemo cityDemo = new UserDemo.CityDemo();
        cityDemo.setCityName("浙江");
        cityDemo.setCityId(377);
        cityDemo.setLat(22.00);
        cityDemo.setLng(33.00);
        userDemo.setCityDemo(cityDemo);
        List<UserDemo.RoleDemo> roleDemoList = DataTransformUntil.simulationBeanList(UserDemo.RoleDemo.class, 10);

        userDemo.setRoleDemoList(roleDemoList);
        excelExcelServiceAdapter.exportExcel(Arrays.asList(userDemo, userDemo), easyExcelPoint, fileOutputStream);

        // map 数据导出
        String json = "[{\"id\":9699,\"userName\":\"宋帅冰new\",\"deptName\":\"中台测试\",\"deptId\":\"485\",\"roleName\":\"租户管理角色\",\"roleId\":\"303\",\"terraceList\":[],\"workNum\":null,\"mobile\":\"18337129240\",\"email\":\"\",\"position\":\"\",\"status\":0,\"statusName\":\"启用\",\"tenantId\":56,\"tenantName\":\"中台超级管理员\",\"firstChar\":null,\"tenantUserId\":\"null\",\"isBindWx\":false},{\"id\":9626,\"userName\":\"朱伟琦\",\"deptName\":\"中台测试\",\"deptId\":\"485\",\"roleName\":\"租户管理角色\",\"roleId\":\"303\",\"terraceList\":[\"MIDDLEGROUND\"],\"workNum\":null,\"mobile\":\"15167165084\",\"email\":null,\"position\":null,\"status\":0,\"statusName\":\"启用\",\"tenantId\":56,\"tenantName\":\"中台超级管理员\",\"firstChar\":null,\"tenantUserId\":\"null\",\"isBindWx\":false},{\"id\":7327,\"userName\":\"严伟\",\"deptName\":null,\"deptId\":null,\"roleName\":\"租户管理角色\",\"roleId\":\"303\",\"terraceList\":[],\"workNum\":null,\"mobile\":\"17858860646\",\"email\":\"\",\"position\":\"\",\"status\":0,\"statusName\":\"启用\",\"tenantId\":56,\"tenantName\":\"中台超级管理员\",\"firstChar\":null,\"tenantUserId\":\"null\",\"isBindWx\":false},{\"id\":7325,\"userName\":\"龚小枚\",\"deptName\":\"中台测试\",\"deptId\":\"485\",\"roleName\":\"租户管理角色\",\"roleId\":\"303\",\"terraceList\":[\"MIDDLEGROUND\"],\"workNum\":null,\"mobile\":\"18268834263\",\"email\":\"\",\"position\":\"\",\"status\":0,\"statusName\":\"启用\",\"tenantId\":56,\"tenantName\":\"中台超级管理员\",\"firstChar\":null,\"tenantUserId\":\"null\",\"isBindWx\":false},{\"id\":8505,\"userName\":\"周子赞\",\"deptName\":\"中台测试\",\"deptId\":\"485\",\"roleName\":\"租户管理角色\",\"roleId\":\"303\",\"terraceList\":[],\"workNum\":null,\"mobile\":\"17319201747\",\"email\":\"\",\"position\":\"\",\"status\":0,\"statusName\":\"启用\",\"tenantId\":56,\"tenantName\":\"中台超级管理员\",\"firstChar\":null,\"tenantUserId\":\"null\",\"isBindWx\":false},{\"id\":7442,\"userName\":\"张莉\",\"deptName\":null,\"deptId\":null,\"roleName\":\"租户管理角色\",\"roleId\":\"303\",\"terraceList\":[],\"workNum\":null,\"mobile\":\"18768196430\",\"email\":\"\",\"position\":\"\",\"status\":0,\"statusName\":\"启用\",\"tenantId\":56,\"tenantName\":\"中台超级管理员\",\"firstChar\":null,\"tenantUserId\":\"null\",\"isBindWx\":false},{\"id\":7838,\"userName\":\"陈七画\",\"deptName\":\"中台测试\",\"deptId\":\"485\",\"roleName\":\"租户管理角色\",\"roleId\":\"303\",\"terraceList\":[\"MIDDLEGROUND\"],\"workNum\":null,\"mobile\":\"17770031915\",\"email\":null,\"position\":null,\"status\":0,\"statusName\":\"启用\",\"tenantId\":56,\"tenantName\":\"中台超级管理员\",\"firstChar\":null,\"tenantUserId\":\"null\",\"isBindWx\":false},{\"id\":7835,\"userName\":\"叶文博\",\"deptName\":\"中台测试\",\"deptId\":\"485\",\"roleName\":\"租户管理角色\",\"roleId\":\"303\",\"terraceList\":[\"MIDDLEGROUND\"],\"workNum\":null,\"mobile\":\"17316910161\",\"email\":\"\",\"position\":\"\",\"status\":0,\"statusName\":\"启用\",\"tenantId\":56,\"tenantName\":\"中台超级管理员\",\"firstChar\":null,\"tenantUserId\":\"null\",\"isBindWx\":false},{\"id\":7606,\"userName\":\"陈健鹏\",\"deptName\":\"中台测试\",\"deptId\":\"485\",\"roleName\":\"租户管理角色\",\"roleId\":\"303\",\"terraceList\":[\"MIDDLEGROUND\"],\"workNum\":null,\"mobile\":\"15258810542\",\"email\":\"\",\"position\":\"\",\"status\":0,\"statusName\":\"启用\",\"tenantId\":56,\"tenantName\":\"中台超级管理员\",\"firstChar\":null,\"tenantUserId\":\"null\",\"isBindWx\":false},{\"id\":7404,\"userName\":\"余坚锋\",\"deptName\":\"中台测试\",\"deptId\":\"485\",\"roleName\":null,\"roleId\":null,\"terraceList\":[],\"workNum\":58,\"mobile\":\"15888878458\",\"email\":\"\",\"position\":\"产品管理\",\"status\":0,\"statusName\":\"启用\",\"tenantId\":56,\"tenantName\":\"中台超级管理员\",\"firstChar\":null,\"tenantUserId\":\"null\",\"isBindWx\":false}]";

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> map = objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
        });
        System.out.println(map);
        easyExcelWorkbookTest = FileUtil.createFile(localClassPath, "EasyExcelWorkbookMapTest.xls");

        fileOutputStream = new FileOutputStream(easyExcelWorkbookTest.getPath());
        excelExcelServiceAdapter.exportExcel(map, easyExcelPoint, fileOutputStream);
    }
}
