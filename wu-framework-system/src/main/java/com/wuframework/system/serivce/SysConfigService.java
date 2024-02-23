package com.wuframework.system.serivce;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wuframework.system.common.entity.SysConfig;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Xiong xz
 * @since 2018-11-20
 */
public interface SysConfigService extends IService<SysConfig> {
    /**
     * 保存系统配置
     *
     * @param sysConfig 系统配置
     * @return
     */
    Integer saveSysConfig(SysConfig sysConfig);

    /**
     * 修改系统配置
     *
     * @param sysConfig 系统配置
     * @return
     */
    Integer updateSysConfig(SysConfig sysConfig);

    /**
     * 根据配置类型查询Redis中的值
     *
     * @param configType
     * @return
     */
    String selectSysConfigByRedisAndType(String configType);

    /**
     * 查询系统配置
     *
     * @param sysConfig 系统配置
     * @param page      分页
     * @return
     */
    Page selectSysConfigListPage(SysConfig sysConfig, Page page);

    /**
     * 查询所有系统配置
     *
     * @param configType 配置类型
     * @return
     */
    List<SysConfig> selectSysConfigListAll(String configType);

    /**
     * 查询配置值
     *
     * @param configType 配置类型
     * @return
     */
    SysConfig selectSysConfigByConfigType(String configType);

}
