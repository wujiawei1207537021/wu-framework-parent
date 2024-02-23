package com.wu.framework.authorization.platform.application;

import com.wu.framework.authorization.platform.application.command.MenuCommand;
import com.wu.framework.authorization.platform.application.dto.MenuTreeDTO;
import com.wu.framework.authorization.platform.model.menu.Menu;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface MenuApplication {


    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Menu> save(MenuCommand menuCommand);

    /**
     * describe 更新
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Menu> update(MenuCommand menuCommand);

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Menu> findOne(MenuCommand menuCommand);

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<Menu>> findList(MenuCommand menuCommand);


    /**
     * 查询菜单树
     * @param menuCommand
     * @return
     */
    Result<List<MenuTreeDTO>> findTree(MenuCommand menuCommand);
    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Menu> delete(MenuCommand menuCommand);

    /**
     * 批量存储 增量更新
     *
     * @param menuCommandList
     * @return
     */
   Result upsert(List<MenuCommand> menuCommandList);

}