package com.wuframework.system.serivce.def;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.wuframework.system.common.consts.CacheConsts;
import com.wuframework.system.common.entity.SysConfig;
import com.wuframework.system.persistence.mapper.SysConfigMapper;
import com.wuframework.system.redis.RedisRepository;
import com.wuframework.system.serivce.SysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Xiong xz
 * @since 2018-11-20
 */
@Service
public class DefaultSysConfigService extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Autowired
    private RedisRepository redisRepository;

    /**
     * 保存系统配置
     *
     * @param sysConfig 系统配置
     * @return
     */
    @Override
    public Integer saveSysConfig(SysConfig sysConfig) {
        int row = -1;
        EntityWrapper<SysConfig> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("config_type", sysConfig.getConfigType());
        int count = this.selectCount(entityWrapper);
        if (count > 0) {
            return row;
        }
        row = this.sysConfigMapper.insert(sysConfig);
        if (row > 0) {
            this.redisRepository.del(CacheConsts.SYS_CONFIG);
        }
        return row;
    }

    /**
     * 修改系统配置
     *
     * @param sysConfig 系统配置
     * @return
     */
    @Override
    public Integer updateSysConfig(SysConfig sysConfig) {
        int row = this.sysConfigMapper.updateById(sysConfig);
        if (row > 0) {
            this.redisRepository.del(CacheConsts.SYS_CONFIG);
        }
        return row;
    }

    /**
     * 根据配置类型查询Redis中的值
     *
     * @param configType 配置类型
     * @return
     **/
    @Override
    public String selectSysConfigByRedisAndType(String configType) {
        if (redisRepository.exists(configType)) {
            return this.redisRepository.get(configType);
        }
        return this.selectSysConfigByConfigType(configType).getConfigValue();
    }

    /**
     * 查询系统配置
     *
     * @param sysConfig 系统配置
     * @param page      分页
     * @return
     */
    @Override
    public Page selectSysConfigListPage(SysConfig sysConfig, Page page) {
        EntityWrapper<SysConfig> entityWrapper = new EntityWrapper<>();
        entityWrapper.like("config_type", sysConfig.getConfigType());
        entityWrapper.like("config_name", sysConfig.getConfigName());
        return page.setRecords(this.sysConfigMapper.selectPage(page, entityWrapper));
    }

    /**
     * 查询所有系统配置
     *
     * @param configType 配置类型
     * @return
     */
    @Override
    public List<SysConfig> selectSysConfigListAll(String configType) {
        return this.refreshRedis(configType);
    }

    /**
     * 刷新redis
     *
     * @param configType 配置类型
     * @return
     */
    public List<SysConfig> refreshRedis(String configType) {
        List<SysConfig> list = new ArrayList<>();
        try {
            String redisData = this.redisRepository.get(CacheConsts.SYS_CONFIG);
            if (StringUtils.isBlank(redisData)) {
                list = this.sysConfigMapper.selectList(null);
                if (CollectionUtils.isNotEmpty(list)) {
                    this.redisRepository.set(CacheConsts.SYS_CONFIG, JSON.toJSONString(list));
                }
            } else {
                list = JSON.parseArray(redisData, SysConfig.class);
            }
        } catch (Exception e) {
            list = this.sysConfigMapper.selectList(null);
            if (CollectionUtils.isNotEmpty(list)) {
                this.redisRepository.set(CacheConsts.SYS_CONFIG, JSON.toJSONString(list));
            }
        }
        if (CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(configType)) {
            list = list.stream().filter(s -> s.getConfigType().equals(configType)).collect(Collectors.toList());
        }
        return list;
    }

    /**
     * 查询配置值
     *
     * @param configType 配置类型
     * @return
     */
    @Override
    public SysConfig selectSysConfigByConfigType(String configType) {
        EntityWrapper<SysConfig> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("config_type", configType);
        return this.selectOne(entityWrapper);
    }

}
