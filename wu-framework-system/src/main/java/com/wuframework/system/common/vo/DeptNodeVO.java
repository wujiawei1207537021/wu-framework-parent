package com.wuframework.system.common.vo;

import lombok.Data;

/**
 * @description: 部门子节点的递归查询结果的VO类
 * @author: Zeng Ao
 * @create: 2018-08-15 10:06
 **/
@Data
public class DeptNodeVO {
    private Long deptId;
    private String deptName;
    private Long parentId;
    /**
     * 根节点到当前节点的级数
     */
    private Integer levels;

    /**
     * 根节点到当前节点的路径,以逗号连接的字符串拼接形式
     */
    private String pathnodes;
    private Integer deptType;
}
