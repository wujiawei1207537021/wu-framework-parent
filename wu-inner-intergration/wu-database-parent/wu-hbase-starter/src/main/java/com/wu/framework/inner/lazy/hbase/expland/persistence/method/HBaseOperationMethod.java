package com.wu.framework.inner.lazy.hbase.expland.persistence.method;


import org.apache.hadoop.hbase.client.Connection;

public interface HBaseOperationMethod {

    Object execute(Connection connection, Object[] args) throws Exception;

}
