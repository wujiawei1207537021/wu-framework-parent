package com.wu.smart.acw.server.application.assembler;

import com.wu.smart.acw.server.application.dto.AcwInstanceTypeDTO;
import com.wu.smart.acw.server.domain.database.instance.type.DatabaseInstanceType;

public class AcwInstanceTypeDTOAssembler {
    public static AcwInstanceTypeDTO fromDatabaseInstanceType(DatabaseInstanceType databaseInstanceType) {
        if (databaseInstanceType == null) {
            return null;
        }
        AcwInstanceTypeDTO acwInstanceTypeDTO = new AcwInstanceTypeDTO();
        return acwInstanceTypeDTO;
    }

}
