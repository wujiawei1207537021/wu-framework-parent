package com.wu.smart.acw.core.service.impl;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.api.TranslateApi;
import com.wu.framework.inner.lazy.database.expand.database.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.database.expand.database.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.database.expand.database.persistence.map.EasySmartFillFieldConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.map.EasySmartFillFieldConverterAbstract;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.qo.TableConfigurationQo;
import com.wu.smart.acw.core.service.DemandCodeGenerationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;

@Service
public class DemandCodeGenerationServiceImpl implements DemandCodeGenerationService {

    private final TranslateApi translate;

    public DemandCodeGenerationServiceImpl(TranslateApi translate) {
        this.translate = translate;
    }

    /**
     * 生成表
     *
     * @param table
     * @return
     */
    @Override
    public Result generationTable(TableConfigurationQo table) {


        return null;
    }

    @Override
    public Result generation(TableConfigurationQo table) {

        // 存储项目
        // 存储项目依赖
        // 存储 class
        //存储 方法
        //存储代码

        // 中文翻译
        // test table
        String tableEn = this.translate.translate(table.getName(), TranslateApi.LanguageType.zh);
        ClassLazyTableEndpoint classLazyTableEndpoint = new ClassLazyTableEndpoint();
        // test_table
        final String toLowerCaseTable = tableEn.replace(NormalUsedString.SPACE, NormalUsedString.UNDERSCORE).toLowerCase(Locale.ROOT);
        classLazyTableEndpoint.setTableName(toLowerCaseTable);
        classLazyTableEndpoint.setComment(table.getName());
        classLazyTableEndpoint.setFieldEndpoints(new ArrayList<>());
        for (TableConfigurationQo.FieldConfig field : table.getFields()) {
            // test id
            String fieldEn = this.translate.translate(field.getName(), TranslateApi.LanguageType.zh);
            // test_id
            final String lowerCaseFieldEn = fieldEn.replace(NormalUsedString.SPACE, NormalUsedString.UNDERSCORE).toLowerCase(Locale.ROOT);
            ClassLazyTableEndpoint.ClassLazyTableFieldEndpoint fieldEndpoint = new ClassLazyTableEndpoint.ClassLazyTableFieldEndpoint();
            fieldEndpoint.setComment(field.getName());
            fieldEndpoint.setName(lowerCaseFieldEn);
            fieldEndpoint.setExist(true);
            fieldEndpoint.setClazz(field.getType().getClazz());
            classLazyTableEndpoint.getFieldEndpoints().add(fieldEndpoint);

        }
        // 生成数据库表
        String creatTableSQL = classLazyTableEndpoint.creatTableSQL();
        System.out.println(creatTableSQL);
        // 生成实体
        EasySmartFillFieldConverterAbstract.CreateInfo createInfo = new EasySmartFillFieldConverterAbstract.CreateInfo();
        // TestTable
        String humpTable = CamelAndUnderLineConverter.lineToHumpClass(toLowerCaseTable);
        createInfo.setClassName(humpTable);
        createInfo.setFileSuffix(NormalUsedString.DOT_JAVA);

        for (LazyTableFieldEndpoint fieldEndpoint : classLazyTableEndpoint.getFieldEndpoints()) {
            Class clazz = fieldEndpoint.getClazz();
            EasySmartFillFieldConverterAbstract.CreateField createField = new EasySmartFillFieldConverterAbstract.CreateField();

            createField.setFieldName(CamelAndUnderLineConverter.lineToHumpField(fieldEndpoint.getName()));
            createField.setFieldTypeName(clazz.getTypeName());
            createInfo.getCreateFieldList().add(createField);

        }
        EasySmartFillFieldConverter easySmartFillFieldConverter = new EasySmartFillFieldConverter();
        easySmartFillFieldConverter.targetClassWriteAttributeFieldList(createInfo);

        // 生成接口 (CRUD)


        // acw

        return null;
    }


}
