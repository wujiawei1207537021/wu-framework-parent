package com.wu.smart.acw.server.application.impl;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.dictionary.api.TranslateApi;
import com.wu.framework.inner.layer.stereotype.LayerClass;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasySmartFillFieldConverter;
import com.wu.framework.inner.lazy.persistence.map.EasySmartFillFieldConverterAbstract;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.qo.TableConfigurationQo;
import com.wu.smart.acw.core.domain.uo.AcwClassUo;
import com.wu.smart.acw.server.application.DemandCodeGenerationApplication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;

@Service
public class DemandCodeGenerationServiceImpl implements DemandCodeGenerationApplication {

    private final TranslateApi translate;
    private final LazyLambdaStream lazyLambdaStream;

    public DemandCodeGenerationServiceImpl(TranslateApi translate, LazyLambdaStream lazyLambdaStream) {
        this.translate = translate;

        this.lazyLambdaStream = lazyLambdaStream;
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


        // 中文翻译
        // test table
        String tableEn = this.translate.translate(table.getTableZhName(), TranslateApi.LanguageType.zh);
        ClassLazyTableEndpoint lazyTableEndpoint = new ClassLazyTableEndpoint();
        // test_table
        final String toLowerCaseTable = tableEn.replace(NormalUsedString.SPACE, NormalUsedString.UNDERSCORE).toLowerCase(Locale.ROOT);
        lazyTableEndpoint.setTableName(toLowerCaseTable);
        lazyTableEndpoint.setComment(table.getTableZhName());
        lazyTableEndpoint.setFieldEndpoints(new ArrayList<>());
        for (TableConfigurationQo.FieldConfig field : table.getFields()) {
            // test id
            String fieldEn = this.translate.translate(field.getName(), TranslateApi.LanguageType.zh);
            // test_id
            final String lowerCaseFieldEn = fieldEn.replace(NormalUsedString.SPACE, NormalUsedString.UNDERSCORE).toLowerCase(Locale.ROOT);
            FieldLazyTableFieldEndpoint fieldEndpoint = new FieldLazyTableFieldEndpoint();
            fieldEndpoint.setComment(field.getName());
            fieldEndpoint.setName(lowerCaseFieldEn);
            fieldEndpoint.setExist(true);
            fieldEndpoint.setClazz(field.getType().getClazz());
            lazyTableEndpoint.getFieldEndpoints().add(fieldEndpoint);

        }
        // 生成数据库表
        String creatTableSQL = lazyTableEndpoint.creatTableSQL();
        System.out.println(creatTableSQL);
        // 生成实体
        EasySmartFillFieldConverterAbstract.CreateInfo createInfo = new EasySmartFillFieldConverterAbstract.CreateInfo();
        // TestTable
        String humpTable = CamelAndUnderLineConverter.lineToHumpClass(toLowerCaseTable);
        createInfo.setClassName(humpTable);
        createInfo.setFileSuffix(NormalUsedString.DOT_JAVA);

        for (LazyTableFieldEndpoint fieldEndpoint : lazyTableEndpoint.getFieldEndpoints()) {
            Class clazz = fieldEndpoint.getClazz();
            EasySmartFillFieldConverterAbstract.CreateField createField = new EasySmartFillFieldConverterAbstract.CreateField();

            createField.setFieldName(CamelAndUnderLineConverter.lineToHumpField(fieldEndpoint.getName()));
            createField.setFieldTypeName(clazz.getTypeName());
            createInfo.getCreateFieldList().add(createField);

        }
        EasySmartFillFieldConverter easySmartFillFieldConverter = new EasySmartFillFieldConverter();
        easySmartFillFieldConverter.targetClassWriteAttributeFieldList(createInfo);

        // 存储 class
        final AcwClassUo acwClassUo = new AcwClassUo();
        acwClassUo.setName(humpTable).
//                setAnnotationList().
        setProjectId(table.getProjectId()).
                setType(LayerClass.LayerType.CONTROLLER);

        lazyLambdaStream.upsert(acwClassUo);
        // 存储 方法
        // 存储代码

        // 生成接口 (CRUD)


        // acw

        return null;
    }


}
