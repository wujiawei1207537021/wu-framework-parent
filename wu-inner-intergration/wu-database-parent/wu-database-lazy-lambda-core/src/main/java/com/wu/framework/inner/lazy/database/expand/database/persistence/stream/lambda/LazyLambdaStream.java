package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.Build;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.LambdaBuild;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.delete.SimpleDeleteBuild;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.update.SimpleUpdateBuild;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.config.LazyDataBaseStreamConfig;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.Execute;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.delete.SimpleDeleteLazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.dml.SimpleDMLLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.insert.MultipleInsertLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.script.SimpleScriptRunnerLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.select.AbstractSelectLazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.select.SimpleSelectLazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.update.SimpleUpdateLazyLambdaStream;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * description 数据库操作lambda 表达式写法 LazyLambdaStream
 *
 * @author Jia wei Wu
 * @date 2021/4/27 3:41 下午
 * @see LazyDataBaseStreamConfig
 */
public class LazyLambdaStream extends AbstractSelectLazyLambdaStream
        implements SimpleSelectLazyLambdaStream,
        SimpleUpdateLazyLambdaStream,
        SimpleDeleteLazyLambdaStream,
        SimpleScriptRunnerLambdaStream,
        SimpleDMLLambdaStream,
        MultipleInsertLambdaStream {
    private final LazyBaseOperation lazyOperation;

    public LazyLambdaStream(LazyBaseOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }

    /**
     * 创建一个class的绑定
     *
     * @param clazz 表对应的class
     * @param <T>   表对应class 的范型
     * @return 返回绑定
     */
    public <T> Build<T> of(Class<T> clazz) {
        return new LambdaBuild<>(clazz, lazyOperation);
    }

    /**
     * 直接使用查询
     *
     * @param comparison 条件
     * @param <T>        范型
     * @return 返回执行对象
     */
    @Override
    public <T> Execute<T> select(BasicComparison<T, ?, ?, ?> comparison) {
        // TODO
        // @see select(BasicComparison<T, ?, ?, ?> comparison, Snippet<T, ?>... snippets)
//        return select(comparison, null);
        return new LambdaBuild<T>(lazyOperation).select(comparison);
    }

    /**
     * 查询数据
     *
     * @param sql        执行sql
     * @param returnType 返回数据类型
     * @return Execute<T>
     */
    @Override
    public <T> Execute<T> select(String sql, Class<T> returnType) {
        LambdaBuild<T> lambdaBuild = new LambdaBuild<>(returnType, lazyOperation);
        return lambdaBuild.select(sql, returnType);
    }



    /**
     * describe upsert
     *
     * @param save 存储对象
     * @author Jia wei Wu
     * @date 2022/1/20 8:44 下午
     **/
    @Override
    public <T> void upsert(T save) {
        if (ObjectUtils.isEmpty(save)) {
//            Assert.notNull(null,"存储数据不能为空");
            return;
        }
        lazyOperation.upsert(save);
    }

    /**
     * describe 更新或者插入单个执行 去除空值
     *
     * @param save 操作的对象
     *             返回的数据 void
     * @author Jia wei Wu
     * @date 2022/1/20 8:44 下午
     **/
    @Override
    public <T> void upsertRemoveNull(T save) {
        lazyOperation.upsertRemoveNull(save);
    }

    /**
     * 数据插入
     *
     * @param save 存储对象
     * @param <T>  存储对象范型
     */
    @Override
    public <T> void insert(T save) {
        lazyOperation.insert(save);
    }

    @Override
    public <T> void saveOrUpdate(T save) {
        lazyOperation.saveOrUpdate(save);
    }

    /**
     * 数据更新
     *
     * @param t          实体对象
     * @param comparison 条件
     * @return 影响行数
     */
    @Override
    public <T> Integer update(T t, BasicComparison<T, ?, ?, ?> comparison) {
        SimpleUpdateBuild<T> simpleUpdateBuild = new LambdaBuild<>(lazyOperation);
        return simpleUpdateBuild.update(t, comparison);
    }

    /**
     * 数据删除
     *
     * @param comparison 条件
     * @return 影响行数
     */
    @Override
    public <T> Integer delete(BasicComparison<T, ?, ?, ?> comparison) {
        SimpleDeleteBuild<T> simpleUpdateBuild = new LambdaBuild<>(lazyOperation);
        return simpleUpdateBuild.delete(comparison);
    }

    /**
     * 执行sql 文件
     *
     * <p>
     * 本地文件导入：
     * Resource fileResource = new FileSystemResource("/Users/wujiawei/IdeaProjects/wu-framework-parent/wu-inner-intergration/wu-database-parent/wu-database-lazy-sql-core/src/test/resources/schema.sql");
     * </p>
     *
     * <p>
     * resource 下文件导出：
     * ClassPathResource classPathResource = new ClassPathResource("schema.sql");
     * </p>
     *
     * <p>
     * 远程文件导入：
     * UrlResource urlResource = new UrlResource("<a href="http://ip:port/sql-173.sql">...</a>");
     * </p>
     * <p>
     * MultipartFile 转换成 Resource：
     * MultipartFile::getResource
     * </p>
     *
     * @param resources sql 文件
     * @return Object
     */
    @Override
    public Object scriptRunner(Resource... resources) {
        return lazyOperation.scriptRunner(resources);
    }

    /**
     * 执行sql 脚本
     *
     * @param scripts sql 脚本
     * @return Object
     */
    @Override
    public Object stringScriptRunner(String... scripts) {
        return lazyOperation.stringScriptRunner(scripts);
    }

    /**
     * describe 完善表
     *
     * @param entityClasses class 对象数组
     * @param <T>           范型
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/2 5:05 下午
     **/
    @SafeVarargs
    @Override
    public final <T> T perfect(Class<T>... entityClasses) {
        return lazyOperation.perfect(entityClasses);
    }

    /**
     * describe 完善表
     *
     * @param tableEndpoints 表schema
     * @return 返回
     * @author Jia wei Wu
     * @date 2022/10/6 14:44
     **/
    @Override
    public <T> T perfect(LazyTableEndpoint... tableEndpoints) {
        return lazyOperation.perfect(tableEndpoints);
    }

    /**
     * 执行sql 返回数据
     *
     * @param sql    sql 模版 "select * from information_schema.tables where table_schema=''{0}'' and table_name=''{1}'' "
     * @param t      返回数据范型
     * @param params sql 模版参数
     * @return T
     */
    @Override
    public <T> T executeSQLForBean(String sql, Class<T> t, Object... params) {
        return lazyOperation.executeSQLForBean(sql, t, params);
    }

    /**
     * 执行sql 返回数据
     *
     * @param sql    sql 模版 "select * from information_schema.tables where table_schema=''{0}'' and table_name=''{1}'' "
     * @param t      返回数据范型
     * @param params sql 模版参数
     * @return 返回结果集
     */
    @Override
    public <T> List<T> executeSQL(String sql, Class<T> t, Object... params) {
        return lazyOperation.executeSQL(sql, t, params);
    }

    /**
     * 执行操作
     *
     * @param persistenceRepository 执行的脚本
     * @return 返回结果集
     */
    @Override
    public List<Object> execute(PersistenceRepository persistenceRepository) {
        return lazyOperation.execute(persistenceRepository);
    }

    /**
     * 执行操作
     *
     * @param persistenceRepository 执行的脚本
     * @return 返回结果
     */
    @Override
    public Object executeOne(PersistenceRepository persistenceRepository) {
        return lazyOperation.executeOne(persistenceRepository);
    }

    /**
     * describe 创建表
     *
     * @param entityClasses@return
     * @author Jia wei Wu
     * @date 2022/1/2 7:48 下午
     **/
    @SafeVarargs
    @Override
    public final <T> T createTable(Class<T>... entityClasses) {
        return lazyOperation.createTable(entityClasses);
    }

    /**
     * describe 更新表
     *
     * @param entityClasses@return
     * @author Jia wei Wu
     * @date 2022/1/2 7:49 下午
     **/
    @SafeVarargs
    @Override
    public final <T> T updateTable(Class<T>... entityClasses) {
        return lazyOperation.updateTable(entityClasses);
    }

    /**
     * 统计数量
     *
     * @param comparison 条件
     * @return 返回存在数据量
     */
    @Override
    public <T> Long count(BasicComparison<T, ?, ?, ?> comparison) {
        return new LambdaBuild<T>(lazyOperation).count(comparison);
    }

    /**
     * 判断是否存在
     *
     * @param comparison 条件
     * @return boolean true存在 false 不存在
     */
    @Override
    public <T> boolean exists(BasicComparison<T, ?, ?, ?> comparison) {
        return new LambdaBuild<T>(lazyOperation).exists(comparison);
    }
}
