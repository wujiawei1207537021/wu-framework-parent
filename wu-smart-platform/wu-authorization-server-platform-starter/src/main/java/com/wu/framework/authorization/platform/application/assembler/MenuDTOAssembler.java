package com.wu.framework.authorization.platform.application.assembler;

import com.wu.framework.authorization.platform.application.command.MenuCommand;
import com.wu.framework.authorization.platform.application.dto.MenuDTO;
import com.wu.framework.authorization.platform.application.dto.MenuTreeDTO;
import com.wu.framework.authorization.platform.model.menu.Menu;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/

public class MenuDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static Menu toMenu(MenuCommand menuCommand) {
        if (null != menuCommand) {
            Menu menu = new Menu();
            menu.setDesc(menuCommand.getDesc());
            menu.setCode(menuCommand.getCode());
            menu.setCreateTime(menuCommand.getCreateTime());
            menu.setIcon(menuCommand.getIcon());
            menu.setId(menuCommand.getId());
            menu.setIframe(menuCommand.getIframe());
            menu.setIsDeleted(menuCommand.getIsDeleted());
            menu.setMenu(menuCommand.getMenu());
            menu.setName(menuCommand.getName());
            menu.setParentCode(menuCommand.getParentCode());
            menu.setSort(menuCommand.getSort());
            menu.setType(menuCommand.getType());
            menu.setUpdateTime(menuCommand.getUpdateTime());
            menu.setUrl(menuCommand.getUrl());
            return menu;
        }
        return null;
    }

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static MenuDTO fromMenu(Menu menu) {
        if (null != menu) {
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setDesc(menu.getDesc());
            menuDTO.setCode(menu.getCode());
            menuDTO.setCreateTime(menu.getCreateTime());
            menuDTO.setIcon(menu.getIcon());
            menuDTO.setId(menu.getId());
            menuDTO.setIframe(menu.getIframe());
            menuDTO.setIsDeleted(menu.getIsDeleted());
            menuDTO.setMenu(menu.getMenu());
            menuDTO.setName(menu.getName());
            menuDTO.setParentCode(menu.getParentCode());
            menuDTO.setSort(menu.getSort());
            menuDTO.setType(menu.getType());
            menuDTO.setUpdateTime(menu.getUpdateTime());
            menuDTO.setUrl(menu.getUrl());
            return menuDTO;
        }
        return null;
    }
    public static MenuTreeDTO treeFromMenu(Menu menu) {
        if (null != menu) {
            MenuTreeDTO menuTreeDTO = new MenuTreeDTO();
            menuTreeDTO.setDesc(menu.getDesc());
            menuTreeDTO.setCode(menu.getCode());
            menuTreeDTO.setCreateTime(menu.getCreateTime());
            menuTreeDTO.setIcon(menu.getIcon());
            menuTreeDTO.setId(menu.getId());
            menuTreeDTO.setIframe(menu.getIframe());
            menuTreeDTO.setIsDeleted(menu.getIsDeleted());
            menuTreeDTO.setMenu(menu.getMenu());
            menuTreeDTO.setName(menu.getName());
            menuTreeDTO.setParentCode(menu.getParentCode());
            menuTreeDTO.setSort(menu.getSort());
            menuTreeDTO.setType(menu.getType());
            menuTreeDTO.setUpdateTime(menu.getUpdateTime());
            menuTreeDTO.setUrl(menu.getUrl());
            return menuTreeDTO;
        }
        return null;
    }

    public static List<MenuTreeDTO> getChildren(MenuTreeDTO menuTreeDTO, List<MenuTreeDTO> menuTreeDTOList){
        return menuTreeDTOList.stream().filter(u -> u.getType() != 2).filter(u -> Objects.equals(u.getParentCode(), menuTreeDTO.getCode())).peek(
                u -> {
                    List<MenuTreeDTO> buttonList = menuTreeDTOList.stream()
                            .filter(menu -> Objects.equals(menu.getParentCode(), u.getCode()))
                            .filter(menu -> menu.getType() == 2)
                            .collect(Collectors.toList());
                    u.setChildren(getChildren(u, menuTreeDTOList));
                    u.setButtonList(buttonList);

                }
        ).collect(Collectors.toList());
    }

    public static List<MenuTreeDTO> treeFromMenu(List<Menu> menuList) {
        List<MenuTreeDTO> menuTreeDTOList = menuList.stream().parallel().map(MenuDTOAssembler::treeFromMenu).collect(Collectors.toList());
        MenuTreeDTO menuTreeDTO = new MenuTreeDTO();
        menuTreeDTO.setCode("-1");
        return getChildren(menuTreeDTO, menuTreeDTOList);
    }
}