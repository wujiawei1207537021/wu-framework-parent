package wu.framework.automation.server.platform.starter.infrastructure.entity;

import com.wu.framework.automation.platform.server.starter.infrastructure.entity.AutomationNodeDO;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;


class AutomationNodeDOTest {

    public static void main(String[] args) {
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(AutomationNodeDO.class);

        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(AutomationNodeDO.class);
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
        System.out.println(lazyTableEndpoint);
    }
}