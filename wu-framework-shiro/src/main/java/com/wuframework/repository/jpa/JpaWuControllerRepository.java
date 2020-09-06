package com.wuframework.repository.jpa;

import com.wuframework.repository.WuCrudRepository;
import org.springframework.data.repository.CrudRepository;

public class JpaWuControllerRepository<R extends CrudRepository<T,ID>,T,ID>  extends SimpleJpaWuRepository<R,T,ID> implements WuCrudRepository<R,T,ID> {


}
