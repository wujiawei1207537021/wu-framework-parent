package com.wu.smart.acw.server.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.dto.JavaModelDto;
import com.wu.smart.acw.server.application.command.AcwGenerateLocalJavaCommand;
import com.wu.smart.acw.server.application.command.AcwTableCommand;
import com.wu.smart.acw.server.application.command.acw.table.AcwTableQueryListCommand;
import com.wu.smart.acw.server.application.dto.AcwExportTableStructureExcelDTO;
import com.wu.smart.acw.server.application.dto.AcwTableDTO;

import java.util.List;

/**
 * describe : 表操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/31 22:42
 */
public interface AcwTableApplication {


    /**
     * 查询所有数据
     *
     * @param acwTableQueryListCommand
     * @return
     */
    Result<List<AcwTableDTO>> retrieveAll(AcwTableQueryListCommand acwTableQueryListCommand);

    /**
     * 查询出分页的数据
     *
     * @param size
     * @param current
     * @param acwTableQueryListCommand
     * @return
     */
    Result<LazyPage<AcwTableDTO>> retrievePage(int size, int current, AcwTableQueryListCommand acwTableQueryListCommand);

    /**
     * describe 数据存储
     *
     * @param tableName 表名
     * @param data      数据
     * @return
     * @author Jia wei Wu
     * @date 2022/1/31 22:46
     **/
    Result dataStorage(String tableName, List<EasyHashMap> data);

    /**
     * describe 根据表生成Java对应模型
     *
     * @param instanceId 实例ID
     * @param schemaName schema
     * @param tableName  表名称
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/26 21:37
     **/
    Result<JavaModelDto> generateJavaModel(String instanceId, String schemaName, String tableName);

    /**
     * 根据表生成本地Java对应模型
     *
     * @param acwGenerateLocalJavaCommand@return
     */
    Result generateLocalJava(AcwGenerateLocalJavaCommand acwGenerateLocalJavaCommand);

    /**
     * describe 删除表
     *
     * @param id 表ID
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/3 19:36
     **/
    Result deleteTable(Long id);


    /**
     * 删除表
     *
     * @param instanceId 实例ID
     * @param schemaName 数据库
     * @param tableName  表名称
     * @return
     */
    Result deleteTable(String instanceId, String schemaName, String tableName);


    /**
     * 批量删除
     *
     * @param acwTableCommandList
     * @return
     */
    Result batchDeleteTable(List<AcwTableCommand> acwTableCommandList);

    /**
     * describe 新增表结构
     *
     * @param acwTableCommand 表
     * @return acwTableColumnUoList 表字段
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/6 00:22
     **/
    Result storage(AcwTableCommand acwTableCommand);

    /**
     * describe 下载 insert-sql
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/2 18:37
     **/
    String exportInsertSql(String instanceId, String schemaName, String tableName);

    /**
     * describe 下载 upsert-sql
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/2 22:21
     **/
    String exportUpsertSql(String instanceId, String schemaName, String tableName);

    /**
     * describe 下载表结构Excel
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/5 21:11
     **/
    List<AcwExportTableStructureExcelDTO> exportTableStructureExcel(String instanceId, String schemaName, String tableName);


    /**
     * 下载表结构SQL
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     */
    String exportTableStructureSql(String instanceId, String schemaName, String tableName);
}
