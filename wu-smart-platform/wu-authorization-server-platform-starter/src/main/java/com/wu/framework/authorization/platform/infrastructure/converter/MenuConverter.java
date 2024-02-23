package com.wu.framework.authorization.platform.infrastructure.converter;

import com.wu.framework.authorization.platform.infrastructure.entity.MenuDO;
import com.wu.framework.authorization.platform.model.menu.Menu;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class MenuConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static Menu toMenu(MenuDO menuDO) {
        if (null != menuDO) {
            Menu menu = new Menu();
            menu.setDesc(menuDO.getDesc());
            menu.setCode(menuDO.getCode());
            menu.setCreateTime(menuDO.getCreateTime());
            menu.setIcon(menuDO.getIcon());
            menu.setId(menuDO.getId());
            menu.setIframe(menuDO.getIframe());
            menu.setIsDeleted(menuDO.getIsDeleted());
            menu.setMenu(menuDO.getMenu());
            menu.setName(menuDO.getName());
            menu.setParentCode(menuDO.getParentCode());
            menu.setSort(menuDO.getSort());
            menu.setType(menuDO.getType());
            menu.setUpdateTime(menuDO.getUpdateTime());
            menu.setUrl(menuDO.getUrl());
            return menu;
        }
        return null;
    }

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static MenuDO fromMenu(Menu menu) {
        if (null != menu) {
            MenuDO menuDO = new MenuDO();
            menuDO.setDesc(menu.getDesc());
            menuDO.setCode(menu.getCode());
            menuDO.setCreateTime(menu.getCreateTime());
            menuDO.setIcon(menu.getIcon());
            menuDO.setId(menu.getId());
            menuDO.setIframe(menu.getIframe());
            menuDO.setIsDeleted(menu.getIsDeleted());
            menuDO.setMenu(menu.getMenu());
            menuDO.setName(menu.getName());
            menuDO.setParentCode(menu.getParentCode());
            menuDO.setSort(menu.getSort());
            menuDO.setType(menu.getType());
            menuDO.setUpdateTime(menu.getUpdateTime());
            menuDO.setUrl(menu.getUrl());
            return menuDO;
        }
        return null;
    }

}