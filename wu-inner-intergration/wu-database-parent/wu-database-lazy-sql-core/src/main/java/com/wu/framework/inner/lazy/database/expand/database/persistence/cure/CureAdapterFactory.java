package com.wu.framework.inner.lazy.database.expand.database.persistence.cure;

import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDDLOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDQLOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.lazy.LazySchemaCure;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.lazy.LazyTableColumnCure;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.lazy.LazyTableCure;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyOperationDDLProxyFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyOperationDQLProxyFactory;

import java.util.Arrays;
import java.util.List;

/**
 * describe : 治愈适配器工厂
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/12 22:29
 */
public class CureAdapterFactory {

    public static CureAdapter createCureAdapter(LazyDataSourceProperties properties) {

        return new CureAdapter(defaultCureList(properties));
    }

    private static List<Cure> defaultCureList(LazyDataSourceProperties sourceProperties) {
        LazyBaseDQLOperation lazyBaseDQLOperation = LazyOperationDQLProxyFactory.createLazyDQLOperation(sourceProperties);
        LazyBaseDDLOperation lazyBaseDDLOperation = LazyOperationDDLProxyFactory.createLazyDDLOperation(sourceProperties);
        return Arrays.asList(new LazyTableCure(lazyBaseDDLOperation, lazyBaseDQLOperation),
                new LazyTableColumnCure(lazyBaseDDLOperation, lazyBaseDQLOperation),
                new LazySchemaCure(sourceProperties, lazyBaseDDLOperation)
        );

    }
}
