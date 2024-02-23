package com.wu.framework.authorization.platform.infrastructure.persistence;


import com.wu.framework.authorization.platform.infrastructure.converter.MenuConverter;
import com.wu.framework.authorization.platform.infrastructure.entity.MenuDO;
import com.wu.framework.authorization.platform.model.menu.Menu;
import com.wu.framework.authorization.platform.model.menu.MenuRepository;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class MenuRepositoryImpl extends AbstractLazyCrudRepository<MenuDO,Menu, Integer> implements MenuRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;


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
    public Result<Menu> story(Menu menu) {
        MenuDO menuDO = MenuConverter.fromMenu(menu);
        lazyLambdaStream.upsert(menuDO);
        return ResultFactory.successOf();
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
    public Result<Menu> findOne(Menu menu) {
        MenuDO menuDO = MenuConverter.fromMenu(menu);
        Menu menuOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(menuDO), Menu.class);
        return ResultFactory.successOf(menuOne);
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
    public Result<List<Menu>> findList(Menu menu) {
        MenuDO menuDO = MenuConverter.fromMenu(menu);
        List<Menu> menuList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(menuDO), Menu.class);
        return ResultFactory.successOf(menuList);
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
    public Result<Menu> delete(Menu menu) {
        MenuDO menuDO = MenuConverter.fromMenu(menu);
          lazyLambdaStream.delete(LazyWrappers.<MenuDO>lambdaWrapperBean(menuDO));
        return ResultFactory.successOf();
    }

    /**
     * 批量存储
     *
     * @param menuList
     * @return
     */
    @Override
    public Result batchStory(List<Menu> menuList) {
        List<MenuDO> menuDOList = menuList.stream().map(MenuConverter::fromMenu).collect(Collectors.toList());
        lazyLambdaStream.upsert(menuDOList);
        return ResultFactory.successOf();
    }
}