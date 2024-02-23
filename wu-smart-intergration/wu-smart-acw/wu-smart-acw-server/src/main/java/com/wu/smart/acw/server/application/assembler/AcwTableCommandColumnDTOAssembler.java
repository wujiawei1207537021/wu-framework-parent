package com.wu.smart.acw.server.application.assembler;

import com.wu.framework.inner.lazy.database.domain.LazyColumnIndex;
import com.wu.smart.acw.server.application.dto.AcwTableCommandColumnDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;


@Mapper
public interface AcwTableCommandColumnDTOAssembler {

    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwTableCommandColumnDTOAssembler INSTANCE = Mappers.getMapper(AcwTableCommandColumnDTOAssembler.class);

    AcwTableCommandColumnDTO.AcwTableColumnIndexDTO formLazyColumnIndex(LazyColumnIndex lazyColumnIndex);
}