package com.wu.smart.acw.client.nocode.provider.application.impl;


import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.config.enums.RowValueType;
import com.wu.framework.inner.lazy.config.enums.WebArchitecture;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.Condition;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.util.SqlColumnUtils;
import com.wu.framework.inner.lazy.database.util.SqlUtils;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.field.AbstractLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.client.nocode.provider.application.InterfaceInfoApplication;
import com.wu.smart.acw.client.nocode.provider.application.assembler.InterfaceInfoDTOAssembler;
import com.wu.smart.acw.client.nocode.provider.application.command.DerivativeCodeCommand;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoCommand;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoSaveCommand;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoSaveSQLCommand;
import com.wu.smart.acw.client.nocode.provider.model.interface_.in.param.InterfaceInParam;
import com.wu.smart.acw.client.nocode.provider.model.interface_.info.InterfaceInfo;
import com.wu.smart.acw.client.nocode.provider.model.interface_.info.InterfaceInfoRepository;
import com.wu.smart.acw.client.nocode.provider.model.interface_.out.param.InterfaceOutParam;
import com.wu.smart.acw.client.nocode.provider.model.interface_.table.InterfaceTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * describe Dataway 中的API
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@LazyApplication
public class InterfaceInfoApplicationImpl implements InterfaceInfoApplication {

    @Autowired
    InterfaceInfoRepository interfaceInfoRepository;
    //    @Autowired
//    AcwServerProperties acwServerProperties;
    @Autowired
    LazyOperationConfig lazyOperationConfig;

    /**
     * describe 新增Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceInfo> story(InterfaceInfoSaveCommand interfaceInfoSaveCommand) {
        InterfaceInfo interfaceInfo = InterfaceInfoDTOAssembler.toInterfaceInfo(interfaceInfoSaveCommand);
        return interfaceInfoRepository.story(interfaceInfo);
    }

    /**
     * 根据sql 创建 api
     *
     * @param interfaceInfoSaveSQLCommand 保存的sql 对象
     * @return Result<InterfaceInfo>
     */
    @Override
    public Result<InterfaceInfo> storyWithSql(InterfaceInfoSaveSQLCommand interfaceInfoSaveSQLCommand) {
        InterfaceInfo interfaceInfo = InterfaceInfoDTOAssembler.toInterfaceInfo(interfaceInfoSaveSQLCommand);
        String sql = interfaceInfoSaveSQLCommand.getSql();
        // 解析出表
        List<String> tablesInSqlList = SqlUtils.tablesInSql(sql);
        interfaceInfo.setInterfaceTableList(tablesInSqlList.stream().map(tableName -> {
            InterfaceTable interfaceTable = new InterfaceTable();
            interfaceTable.setTableName(tableName);
            interfaceTable.setIsDeleted(false);
            return interfaceTable;
        }).toList());
        // 解析出字段
        List<Condition> conditionList = SqlColumnUtils.columnConditionInSql(sql);
        LambdaTableType lambdaTableType = SqlColumnUtils.executeTypeInSql(sql);
        interfaceInfo.setExecuteType(lambdaTableType.getValue());

        interfaceInfo.setInterfaceInParamList(conditionList.stream().map(condition -> {
            InterfaceInParam interfaceInParam = new InterfaceInParam();
            interfaceInParam.setName(condition.getRowName().toString());
            interfaceInParam.setTerm(condition.getType());
            interfaceInParam.setDefaultValueType(RowValueType.STRING.equals(condition.getRowValueType()) ? 0 : 1);
            interfaceInParam.setDefaultValue(condition.getRowValue().toString());
            return interfaceInParam;
        }).toList());
        // 只有查询的时候需要返回数据
        List<String> columnInSqlList = SqlColumnUtils.columnInSql(sql);
        interfaceInfo.setInterfaceOutParamList(columnInSqlList.stream().map(columnName -> {
            InterfaceOutParam interfaceOutParam = new InterfaceOutParam();
            interfaceOutParam.setName(columnName);
            if (NormalUsedString.ASTERISK.equals(columnName)) {
                interfaceOutParam.setMappingName(NormalUsedString.EMPTY);
            } else {
                interfaceOutParam.setMappingName(columnName);
            }
            return interfaceOutParam;
        }).toList());
        return interfaceInfoRepository.story(interfaceInfo);
    }

    /**
     * describe 更新Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceInfo> updateOne(InterfaceInfoCommand interfaceInfoCommand) {
        InterfaceInfo interfaceInfo = InterfaceInfoDTOAssembler.toInterfaceInfo(interfaceInfoCommand);
        return interfaceInfoRepository.story(interfaceInfo);
    }

    /**
     * describe 查询单个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceInfo> findOne(InterfaceInfoCommand interfaceInfoCommand) {
        InterfaceInfo interfaceInfo = InterfaceInfoDTOAssembler.toInterfaceInfo(interfaceInfoCommand);
        return interfaceInfoRepository.findOne(interfaceInfo);
    }

    /**
     * describe 查询多个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<List<InterfaceInfo>> findList(InterfaceInfoCommand interfaceInfoCommand) {
        InterfaceInfo interfaceInfo = InterfaceInfoDTOAssembler.toInterfaceInfo(interfaceInfoCommand);
        return interfaceInfoRepository.findList(interfaceInfo);
    }

    /**
     * describe 分页查询多个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<LazyPage<InterfaceInfo>> findPage(int size, int current, InterfaceInfoCommand interfaceInfoCommand) {
        InterfaceInfo interfaceInfo = InterfaceInfoDTOAssembler.toInterfaceInfo(interfaceInfoCommand);
        return interfaceInfoRepository.findPage(size, current, interfaceInfo);
    }

    /**
     * describe 删除Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceInfo> remove(InterfaceInfoCommand interfaceInfoCommand) {
        InterfaceInfo interfaceInfo = InterfaceInfoDTOAssembler.toInterfaceInfo(interfaceInfoCommand);
        return interfaceInfoRepository.remove(interfaceInfo);
    }

    /**
     * 代码衍生
     *
     * @param derivativeCodeCommand 代码衍生代码参数
     * @return
     */
    @Override
    public Result derivativeCode(DerivativeCodeCommand derivativeCodeCommand) {
        // 查询 api信息
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setApiId(derivativeCodeCommand.getApiId());
        Result<InterfaceInfo> infoResult = interfaceInfoRepository.findOne(interfaceInfo);
        InterfaceInfo interfaceInfoRepositoryOne = infoResult.getData();
        List<InterfaceInParam> interfaceInParamList = interfaceInfoRepositoryOne.getInterfaceInParamList();
        List<InterfaceOutParam> interfaceOutParamList = interfaceInfoRepositoryOne.getInterfaceOutParamList();
        List<InterfaceTable> interfaceTableList = interfaceInfoRepositoryOne.getInterfaceTableList();

        String schema = interfaceInfoRepositoryOne.getSchema();
        String tableName = interfaceInfoRepositoryOne.getInterfaceTableList().stream().map(InterfaceTable::getTableName).collect(Collectors.joining(NormalUsedString.UNDERSCORE));
        String apiComment = interfaceInfoRepositoryOne.getApiComment();


        // 输入参数
        List<LazyTableFieldEndpoint> inLazyTableFieldEndpoints = interfaceInParamList.stream().map(interfaceInParam -> {
                    LazyTableFieldEndpoint fieldEndpoint = AbstractLazyTableFieldEndpoint.getInstance();
                    String columnName = interfaceInParam.getName();
                    if (columnName.contains(NormalUsedString.DOT)) {
                        columnName = columnName.split("\\.")[1];
                    }
                    fieldEndpoint.setColumnName(columnName);
                    fieldEndpoint.setName(CamelAndUnderLineConverter.lineToHumpField(columnName));
                    fieldEndpoint.setComment(columnName);
//                    fieldEndpoint.setColumnType(interfaceInParam.getColumnType());
//                    fieldEndpoint.setDataType(interfaceInParam.getDataType());
//                    fieldEndpoint.setNotNull(NormalUsedString.NO.equalsIgnoreCase(interfaceInParam.getIsNullable()));
//                    fieldEndpoint.setDefaultValue(interfaceInParam.getColumnDefault());
//                    String extra = interfaceInParam.getExtra();
//                    fieldEndpoint.setExtra(extra);
                    return fieldEndpoint;
                })
                .collect(Collectors.collectingAndThen(Collectors
                        .toCollection(() -> new TreeSet<>(Comparator
                                .comparing(LazyTableFieldEndpoint::getColumnName))), ArrayList::new));

        // 输出参数
        List<LazyTableFieldEndpoint> outLazyTableFieldEndpoints = interfaceOutParamList.stream().map(outParam -> {
                    LazyTableFieldEndpoint fieldEndpoint = AbstractLazyTableFieldEndpoint.getInstance();
                    String columnName = outParam.getName();
                    if (columnName.contains(NormalUsedString.DOT)) {
                        columnName = columnName.split("\\.")[1];
                    }
                    fieldEndpoint.setColumnName(columnName);
                    fieldEndpoint.setName(CamelAndUnderLineConverter.lineToHumpField(columnName));
                    fieldEndpoint.setComment(columnName);
//                    fieldEndpoint.setColumnType(outParam.getColumnType());
//                    fieldEndpoint.setDataType(outParam.getDataType());
//                    fieldEndpoint.setNotNull(NormalUsedString.NO.equalsIgnoreCase(outParam.getIsNullable()));
//                    fieldEndpoint.setDefaultValue(outParam.getColumnDefault());
//                    String extra = outParam.getExtra();
//                    fieldEndpoint.setExtra(extra);
                    return fieldEndpoint;
                })
                .collect(Collectors
                        .collectingAndThen(Collectors
                                .toCollection(() -> new TreeSet<>(Comparator
                                        .comparing(LazyTableFieldEndpoint::getColumnName))), ArrayList::new));

        LazyOperationConfig.ReverseEngineering reverseEngineering = new LazyOperationConfig.ReverseEngineering();
        reverseEngineering.setOrmArchitecture(OrmArchitecture.LAZY);
        reverseEngineering.setWebArchitecture(WebArchitecture.DDD_ARCHITECTURE);

        ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint = new ReverseClassLazyTableEndpoint();
        reverseClassLazyTableEndpoint.setSchema(schema);
        reverseClassLazyTableEndpoint.setTableName(tableName);
        reverseClassLazyTableEndpoint.setClassName(CamelAndUnderLineConverter.lineToHumpClass(tableName));
        reverseClassLazyTableEndpoint.setInLazyTableFieldEndpoints(inLazyTableFieldEndpoints);
        reverseClassLazyTableEndpoint.setOutLazyTableFieldEndpoints(outLazyTableFieldEndpoints);
        reverseClassLazyTableEndpoint.setComment(apiComment);
        reverseClassLazyTableEndpoint.setPackageName(lazyOperationConfig.getReverseEngineering().getPackageName() + NormalUsedString.DOT + "domain");

        String entitySuffix = lazyOperationConfig.getReverseEngineering().getEntitySuffix();
        if (!ObjectUtils.isEmpty(entitySuffix)) {
            String className = reverseClassLazyTableEndpoint.getClassName();
            reverseClassLazyTableEndpoint.setClassName(className + entitySuffix);
        }

        LazyTableUtil.createJava(reverseClassLazyTableEndpoint, reverseEngineering);
        // 生成代码
        // in 参数
        // out 参数
        // 创建controller
        // 创建 command
        // 创建application
        // 创建repository
        // 创建 dao
        // 控制是否生成实体、领域对象、DO
        return ResultFactory.successOf();
    }
}