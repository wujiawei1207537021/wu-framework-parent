package com.wu.framework.authorization.platform.rpc;

import com.wu.framework.authorization.platform.infrastructure.entity.DictionaryDataDO;
import com.wu.framework.authorization.platform.model.dictionary.data.DictionaryData;
import com.wu.framework.authorization.platform.model.dictionary.data.DictionaryDataRepository;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyRpc;
import com.wu.framework.inner.layer.data.dictionary.api.ConvertApi;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LazyRpc
public class DictionConvertApiRpc implements ConvertApi {


    private final DictionaryDataRepository dictionaryDataRepository;

    public DictionConvertApiRpc(DictionaryDataRepository dictionaryDataRepository) {
        this.dictionaryDataRepository = dictionaryDataRepository;
    }


    /**
     * description 查询多个字典
     *
     * @param convertItems 字典大项
     * @param order        控制 code 和name 的顺序
     * @return Map<String, Map < String, String>> 字典大项
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    @Override
    public Map<String, Map<String, String>> getConvertDataByItems(List<String> convertItems, boolean order) {
        Result<List<DictionaryData>> listByDictionaryCode = dictionaryDataRepository.findListByDictionaryCode(convertItems);
        if(listByDictionaryCode.isSuccessAndHasData()){
            return listByDictionaryCode
                            .getData()
                            .stream()
                            .collect(
                                    Collectors.groupingBy(DictionaryData::getDictionaryCode
                                            ,Collectors.toMap(DictionaryData::getCode,DictionaryData::getName,(A,B)->A))
                            );

        }
        return new LinkedHashMap<>();
    }
}
