package com.wu.framework.inner.lazy.database.expand.database.persistence;

/**
 * describe : DCL 语言
 * <p>
 * 数据控制语言DCL用来授予或回收访问数据库的某种特权，并控制
 * 数据库操纵事务发生的时间及效果，对数据库实行监视等。如：
 * 1) GRANT：授权。
 * <p>
 * <p>
 * 2) ROLLBACK [WORK] TO [SAVEPOINT]：回退到某一点。
 * 回滚---ROLLBACK
 * 回滚命令使数据库状态回到上次最后提交的状态。其格式为：
 * SQL>ROLLBACK;
 * <p>
 * <p>
 * 3) COMMIT [WORK]：提交。
 * <p>
 * <p>
 * 在数据库的插入、删除和修改操作时，只有当事务在提交到数据
 * 库时才算完成。在事务提交前，只有操作数据库的这个人才能有权看
 * 到所做的事情，别人只有在最后提交完成后才可以看到。
 * 提交数据有三种类型：显式提交、隐式提交及自动提交。下面分
 * 别说明这三种类型。
 * <p>
 * <p>
 * (1) 显式提交
 * 用COMMIT命令直接完成的提交为显式提交。其格式为：
 * SQL>COMMIT；
 * <p>
 * <p>
 * (2) 隐式提交
 * 用SQL命令间接完成的提交为隐式提交。这些命令是：
 * ALTER，AUDIT，COMMENT，CONNECT，CREATE，DISCONNECT，DROP，
 * EXIT，GRANT，NOAUDIT，QUIT，REVOKE，RENAME。
 * <p>
 * <p>
 * (3) 自动提交
 * 若把AUTOCOMMIT设置为ON，则在插入、修改、删除语句执行后，
 * 系统将自动进行提交，这就是自动提交。其格式为：
 * SQL>SET AUTOCOMMIT ON
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/4/23 22:58
 */
public interface LazyBaseDCLOperation {
}
