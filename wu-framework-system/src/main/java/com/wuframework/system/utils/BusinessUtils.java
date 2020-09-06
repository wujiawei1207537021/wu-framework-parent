package com.wuframework.system.utils;

import com.wuframework.system.common.entity.SysRole;
import com.wuframework.system.common.entity.DefaultSysUser;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ：wq
 * @date ：Created in 2019/11/13 16:12
 * @description：业务相关工具类
 * @modified By：
 * @version:
 */
public class BusinessUtils {

    /**
     * 获取用户可查看子部门类型
     *
     * @param sysUser
     * @return
     */
    public static List<String> getFilterTypeList(DefaultSysUser sysUser) {
        List<String> dataTypeList = new ArrayList<>();
        if (ObjectUtils.isEmpty(sysUser.getRoles())) {
            return Collections.emptyList();
        }
        for (SysRole role : sysUser.getRoles()) {
            if (null == role.getDataType()) {
                continue;
            }
            String[] tempList = role.getDataType().split(",");
            for (String temp : tempList) {
                if (!dataTypeList.contains(temp)) {
                    dataTypeList.add(temp);
                }
            }
        }
        return dataTypeList;
    }
}
