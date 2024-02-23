package com.wu.framework.repository.jpa;

import com.wu.framework.inner.lazy.database.expand.database.persistence.CrudRepository;
import com.wu.framework.repository.WuCrudRepository;


public class JpaWuControllerRepository<R extends CrudRepository<T, ID>, T, ID> extends SimpleJpaWuRepository<R, T, ID> implements WuCrudRepository<R, T, ID> {


}
