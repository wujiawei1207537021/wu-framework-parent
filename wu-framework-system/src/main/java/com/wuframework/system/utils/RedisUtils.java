package com.wuframework.system.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wuframework.system.common.consts.CacheConsts;
import com.wuframework.system.common.consts.ConfigConsts;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.SysDept;
import com.wuframework.system.common.entity.SysDict;
import com.wuframework.system.redis.RedisRepository;
import com.wuframework.system.serivce.SysDeptService;
import com.wuframework.system.serivce.SysDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * code is far away from bug with the animal protecting
 *
 * @description: redis缓存的数据获取类
 * 被这个坑了很久，需要多看下
 * @author: maohh
 * @create: 2018-08-20 14:39
 **/
@Slf4j
@Service
public class RedisUtils {

    public static RedisUtils redisUtils;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private SysDictService sysDictService;

    @PostConstruct
    public void init() {
        redisUtils = this;
    }

    /**
     * 根据父部门ID取Redis中的下属部门
     *
     * @param parentDeptId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SysDept> getChildDeptListFromRedisByParentId(Integer parentDeptId) {
        String redisKey = CacheConsts.REDIS_DEPT + parentDeptId;
        boolean isNotExistsKey = !redisRepository.exists(redisKey);
        if (isNotExistsKey) {
            log.error("Redis指定key:[{}]不存在", redisKey);
            this.forceRefreshDept(parentDeptId);
        }
        HashMap<String, Object> deptMap = JSONObject.parseObject(redisRepository.get(redisKey), HashMap.class);
        return deptMap.entrySet().stream().flatMap(entry -> JSON.parseArray(entry.getValue().toString(), SysDept.class).stream())
                .collect(Collectors.toList());
    }

    /**
     * 根据父部门ID取Redis中的下属部门ID字符串（例如：[1,2···]）
     *
     * @param parentDeptId
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getChildDeptListStrFromRedisByParentId(Integer parentDeptId) {
        List<SysDept> deptList = getChildDeptListFromRedisByParentId(parentDeptId);
        return deptList.stream().map(dept -> dept.getDeptId().toString()).collect(Collectors.joining(","));
    }

    /**
     * 根据部门编号及部门类型从 redis 中获取数据
     *
     * @param deptId   部门编号
     * @param deptType 部门类型
     * @return 指定类型的部门数组
     */
    @SuppressWarnings("unchecked")
    public List<SysDept> selectDeptListFromRedisByDeptType(Integer deptId, String deptType) {
        return selectDeptListFromRedisByDeptType(deptId, Collections.singletonList(deptType));
    }

    /**
     * 根据部门编号及部门类型从 redis 中获取数据
     *
     * @param deptId         部门编号
     * @param filterTypeList 部门类型
     * @return 指定类型的部门数组
     */
    @SuppressWarnings("unchecked")
    public List<SysDept> selectDeptListFromRedisByDeptType(Integer deptId, List<String> filterTypeList) {
        List<SysDept> deptList = getChildDeptListFromRedisByParentId(deptId);
        //根据类型进行过滤
        if (!ObjectUtils.isEmpty(filterTypeList)) {
            deptList = deptList.stream()
                    .filter(item -> filterTypeList.contains(String.valueOf(item.getDeptType())))
                    .collect(Collectors.toList());
        }
        return deptList;
    }

    /**
     * 根据部门ID刷新部门信息
     *
     * @param deptId 部门Id
     */
    private void forceRefreshDept(Integer deptId) {
        log.info("刷新redis部门:[{}]", deptId);
        List<SysDept> childList = this.deptService.getDeptChildrenByParentId(deptId, null);
        List<SysDict> sysDictList = sysDictService.listSysDictByTypeOrAll("dept_type");
        Map<String, List<SysDept>> deptMap = sysDictList.stream()
                .collect(Collectors.toMap(SysDict::getValue, sysDict -> childList.stream()
                        .filter(deptLoad -> sysDict.getValue().equals(deptLoad.getDeptType().toString()))
                        .collect(Collectors.toList())));
        redisUtils.redisRepository.setExpire(CacheConsts.REDIS_DEPT + deptId, JSON.toJSONString(deptMap), ConfigConsts.EXPIRE_TIME);
    }

    /**
     * 刷新用户信息
     *
     * @param user 用户信息
     */
    private void forceRefreshUser(DefaultSysUser user) {
        redisUtils.redisRepository.setExpire(user.getUserId().toString(), JSON.toJSONString(user), ConfigConsts.EXPIRE_TIME);
    }
}