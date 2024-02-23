package com.wuframework.system.utils;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.wuframework.system.common.vo.ListNode;
import com.wuframework.system.common.vo.TreeNode;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommonUtils {
    public CommonUtils() {
    }

    public static <T> Object getValue(T t, String fieldName) {
        Object value = null;

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
            PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
            PropertyDescriptor[] var5 = props;
            int var6 = props.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                PropertyDescriptor property = var5[var7];
                if (fieldName.equals(property.getName())) {
                    Method method = property.getReadMethod();
                    value = method.invoke(t);
                }
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        return value;
    }

    public static Map<String, List<String>> different(List<String> oldId, List<String> newId) {
        Predicate<String> addFilter = oldId::contains;
        List<String> addList = (List) newId.stream().filter(addFilter.negate()).collect(Collectors.toList());
        Predicate<String> delFilter = newId::contains;
        List<String> delList = (List) oldId.stream().filter(delFilter.negate()).collect(Collectors.toList());
        List<String> commonList = (List) oldId.stream().filter(delFilter).collect(Collectors.toList());
        Map<String, List<String>> map = Maps.newHashMap();
        map.put("add", addList);
        map.put("del", delList);
        map.put("common", commonList);
        return map;
    }

    public static String getIpAddress(final HttpServletRequest request) {
        String unknown = "unknown";
        String local = "0:0:0:0:0:0:0:1";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip != null && ip.length() != 0) {
            ip = ip.split(",")[0];
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = ip + ":" + request.getRemotePort();
        }

        return ip;
    }

    public static boolean isJson(String str) {
        try {
            JSONObject.parseObject(str);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static boolean isPosOrNegInteger(String str) {
        BigDecimal decimal = new BigDecimal(str);
        return decimal.signum() != -1;
    }

    public static Map<String, Object> colToRow(String rowKey1, String rowKey2, List<Map<String, Object>> mapList) {
        Map<String, Object> baseMap = Maps.newLinkedHashMap();
        Iterator var4 = mapList.iterator();

        while (var4.hasNext()) {
            Map<String, Object> map = (Map) var4.next();
            String key = null;
            Object value = null;
            Iterator var8 = map.entrySet().iterator();

            while (var8.hasNext()) {
                Entry<String, Object> entry = (Entry) var8.next();
                if (rowKey1.equals(entry.getKey())) {
                    key = (String) entry.getValue();
                } else if (rowKey2.equals(entry.getKey())) {
                    value = entry.getValue();
                }
            }

            baseMap.put(key, value);
        }

        return baseMap;
    }

    public static List<TreeNode> list2Tree(List<ListNode> list, String startId) {
        List<TreeNode> treeNodeList = new ArrayList();
        List<ListNode> nodeList = (List) list.stream().filter((d) -> {
            return startId.equals(d.getPid());
        }).collect(Collectors.toList());
        Iterator var4 = nodeList.iterator();

        while (var4.hasNext()) {
            ListNode node = (ListNode) var4.next();
            TreeNode treeNode = new TreeNode();
            treeNode.setKey(node.getKey());
            treeNode.setTitle(node.getTitle());
            treeNode.setValue(node.getTitle() + "," + node.getValue());
            List<TreeNode> treeNodes = list2Tree(list, node.getKey());
            treeNodeList.add(treeNode);
            if (treeNodes.size() == 0) {
                if (node.getKey().indexOf("-") == -1) {
                    treeNodeList.remove(treeNode);
                }
            } else {
                treeNode.setChildren(treeNodes);
                treeNode.setLeaf(treeNodes.size() == 0);
            }
        }

        return treeNodeList;
    }
}
