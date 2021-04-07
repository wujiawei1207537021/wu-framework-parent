package com.wu.framework.inner.lazy.hbase.expland.persistence.method;


import com.wu.framework.inner.layer.stereotype.Layer;
import org.apache.hadoop.hbase.client.Connection;

public interface HBaseOperationMethod extends Layer {

    Object execute(Connection connection, Object[] args) throws Exception;

}
