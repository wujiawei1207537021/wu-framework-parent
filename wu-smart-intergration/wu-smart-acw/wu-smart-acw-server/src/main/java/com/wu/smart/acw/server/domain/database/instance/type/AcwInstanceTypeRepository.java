package com.wu.smart.acw.server.domain.database.instance.type;

import java.util.List;


public interface AcwInstanceTypeRepository {


    List<DatabaseInstanceType> findAll();
}
