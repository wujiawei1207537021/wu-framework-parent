package com.wu.framework.authorization.platform.application.impl;

import com.wu.framework.authorization.platform.application.MenuApplication;
import com.wu.framework.authorization.platform.application.assembler.MenuDTOAssembler;
import com.wu.framework.authorization.platform.application.command.MenuCommand;
import com.wu.framework.authorization.platform.application.dto.MenuTreeDTO;
import com.wu.framework.authorization.platform.model.menu.Menu;
import com.wu.framework.authorization.platform.model.menu.MenuRepository;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.layer.data.dictionary.NormalConvertMapper;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class MenuApplicationImpl implements MenuApplication {

    @Autowired
    MenuRepository menuRepository;

    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Menu> save(MenuCommand menuCommand) {
        Menu menu = MenuDTOAssembler.toMenu(menuCommand);
        return menuRepository.story(menu);
    }

    /**
     * describe 更新
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Menu> update(MenuCommand menuCommand) {
        Menu menu = MenuDTOAssembler.toMenu(menuCommand);
        return menuRepository.story(menu);
    }

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Menu> findOne(MenuCommand menuCommand) {
        Menu menu = MenuDTOAssembler.toMenu(menuCommand);
        return menuRepository.findOne(menu);
    }

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<List<Menu>> findList(MenuCommand menuCommand) {
        Menu menu = MenuDTOAssembler.toMenu(menuCommand);
        return menuRepository.findList(menu);
    }

    /**
     * 查询菜单树
     *
     * @param menuCommand
     * @return
     */
    @NormalConvertMapper
    @Override
    public Result<List<MenuTreeDTO>> findTree(MenuCommand menuCommand) {
        menuCommand.setType(null);
        Result<List<Menu>> result = findList(menuCommand);
        if(result.hasSuccessData()){
            List<Menu> menuList = result.getData();
           List<MenuTreeDTO> menuTreeDTOList= MenuDTOAssembler.treeFromMenu(menuList);
           return ResultFactory.successOf(menuTreeDTOList);
        }
        return ResultFactory.successOf();
    }

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Menu> delete(MenuCommand menuCommand) {
        Menu menu = MenuDTOAssembler.toMenu(menuCommand);
        return menuRepository.delete(menu);
    }

    /**
     * 批量存储 增量更新
     *
     * @param menuCommandList
     */
    @Override
    public Result upsert(List<MenuCommand> menuCommandList) {
        List<Menu> menuList = menuCommandList.stream().map(MenuDTOAssembler::toMenu).collect(Collectors.toList());
        return menuRepository.batchStory(menuList);
    }
}