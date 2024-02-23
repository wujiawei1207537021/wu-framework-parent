package com.wu.smart.acw.server.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.server.application.DatabaseInstanceTypeApplication;
import com.wu.smart.acw.server.application.dto.AcwInstanceTypeDTO;
import com.wu.smart.acw.server.domain.database.instance.type.AcwInstanceTypeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@LazyApplication
public class DatabaseInstanceTypeApplicationImpl implements DatabaseInstanceTypeApplication {
    private final AcwInstanceTypeRepository acwInstanceTypeRepository;

    public DatabaseInstanceTypeApplicationImpl(AcwInstanceTypeRepository acwInstanceTypeRepository) {
        this.acwInstanceTypeRepository = acwInstanceTypeRepository;
    }

    @Override
    public Result<List<AcwInstanceTypeDTO>> retrieveAll() {
        List<AcwInstanceTypeDTO> acwInstanceTypeDTOList = Arrays.stream(LazyDataSourceType.values()).map(dataSourceType -> {
            AcwInstanceTypeDTO acwInstanceTypeDTO = new AcwInstanceTypeDTO();
            acwInstanceTypeDTO.setType(dataSourceType.getType());
            acwInstanceTypeDTO.setTypeName(dataSourceType.getTypeName());
            acwInstanceTypeDTO.setDriver(dataSourceType.getDriver());
            return acwInstanceTypeDTO;
        }).collect(Collectors.toList());

        return ResultFactory.successOf(acwInstanceTypeDTOList);
    }
}
