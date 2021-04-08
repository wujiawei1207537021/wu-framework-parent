package com.wu.framework.inner.lazy.hbase.expland.persistence.method;


import com.wu.framework.inner.layer.stereotype.LayerDefault;
import org.apache.hadoop.hbase.client.Connection;

public interface HBaseOperationMethod extends LayerDefault {

    Object execute(Connection connection, Object[] args) throws Exception;

}
