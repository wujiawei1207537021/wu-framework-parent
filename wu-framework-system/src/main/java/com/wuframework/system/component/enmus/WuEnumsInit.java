package com.wuframework.system.component.enmus;

import com.wuframework.response.enmus.WuEnums;
import com.wuframework.shiro.util.CamelAndUnderLineConverter;
import com.wuframework.shiro.util.ShiroContextUtil;
import com.wuframework.system.common.entity.SysDict;
import com.wuframework.system.common.entity.SysDictType;
import com.wuframework.system.common.pro.ApplicationProperties;
import com.wuframework.system.persistence.jpa.SysDictJpaRepository;
import com.wuframework.system.serivce.SysDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WuEnumsInit implements CommandLineRunner {

    @Resource
    private SysDictJpaRepository sysDictJpaRepository;

    @Resource
    private SysDictTypeService sysDictTypeService;

    @Resource
    private ApplicationProperties applicationProperties;

    @Override
    public void run(String... args) {
        if (applicationProperties.isInitEnum()) {
            initEnums();
        }
    }

    public void initEnums() {

        log.info("<---------枚举自动装载数据库开始---------->");
        String[] beanNamesForAnnotation = ShiroContextUtil.getApplicationContext().getBeanNamesForAnnotation(EnableEnumReflection.class);
        ArrayList<String> basePackages = new ArrayList();
        for (String name : beanNamesForAnnotation) {
            Object bean = ShiroContextUtil.getBean(name);
            // 当前包路径
            String packageName = bean.getClass().getPackage().getName();
            basePackages.add(packageName);
            //注解上参数
            EnableEnumReflection enableEnumReflection = bean.getClass().getAnnotation(EnableEnumReflection.class);
            // 指定扫描路径
            String[] scanBasePackages = enableEnumReflection.scanBasePackages();
            if (!ObjectUtils.isEmpty(scanBasePackages)) {
                basePackages.addAll(Arrays.asList(scanBasePackages));
            }
            //指定扫描类 包含当前类所在包
            Class<?>[] scanBasePackageClasses = enableEnumReflection.scanBasePackageClasses();
            if (!ObjectUtils.isEmpty(scanBasePackageClasses)) {
                for (Class<?> scanBasePackageClass : scanBasePackageClasses) {
                    basePackages.add(scanBasePackageClass.getPackage().getName());
                }
            }

        }
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().forPackages(basePackages.toArray(new String[basePackages.size()])).
                        addScanners(new SubTypesScanner()).addScanners(new FieldAnnotationsScanner()));
        Set<Class<? extends WuEnums>> reflectionsSubTypesOf = reflections.getSubTypesOf(WuEnums.class);
        // 具体业务处理 TODO 上游迁移
        List<SysDict> sysDictList = new ArrayList<>();
        List<SysDictType> sysDictTypeList = new ArrayList<>();
        List typeValues = new ArrayList();

        for (Class<? extends WuEnums> aClass : reflectionsSubTypesOf) {
            WuEnums[] wuEnums = null;
            if (aClass.isEnum()) {
                wuEnums = aClass.getEnumConstants();
            } else {
                continue;
            }
            // SysDictType 数据获取
            SysDictType sysDictType = new SysDictType();

            //新版本枚举注解yuntsoftEnumsProperty 优先于 yuntsoftEnumsService
            WuEnumsProperty wuEnumsProperty = aClass.getAnnotation(WuEnumsProperty.class);
            //                    AnnotationUtils.findAnnotation(aClass, WuEnumsService.class);

            if (ObjectUtils.isEmpty(wuEnumsProperty)) {
//                String className = aClass.getSimpleName();
//                className = enmuNameFormat(className);
//                sysDictType.setTypeId(className);
//                sysDictType.setTypeName(className);

                //          兼容YuntsoftEnumsService 取值
                WuEnumsService wuEnumsService = aClass.getAnnotation(WuEnumsService.class);
                if (ObjectUtils.isEmpty(wuEnumsService)) {
                    String className = aClass.getSimpleName();
                    className = enmuNameFormat(className);
                    sysDictType.setTypeId(className);
                    sysDictType.setTypeName(className);
                } else {
                    boolean init = wuEnumsService.init();
                    if (init) {
                        String typeId = wuEnumsService.value();
                        typeId = enmuNameFormat(typeId);
                        String typeName = wuEnumsService.name();
                        int enable = wuEnumsService.enable();
                        sysDictType.setTypeId(typeId);
                        sysDictType.setTypeName(typeName);
                        sysDictType.setEnabled(enable);
                    } else {
                        continue;
                    }
                }
            } else {
                boolean init = wuEnumsProperty.init();
                if (init) {
                    String typeId = wuEnumsProperty.value();
                    typeId = enmuNameFormat(typeId);
                    String typeName = wuEnumsProperty.name();
                    int enable = wuEnumsProperty.enable();
                    sysDictType.setTypeId(typeId);
                    sysDictType.setTypeName(typeName);
                    sysDictType.setEnabled(enable);
                } else {
                    continue;
                }

            }

            sysDictTypeList.add(sysDictType);

            //SysDict 数据获取
            for (WuEnums yuntsoftEnum : wuEnums) {
                SysDict sysDict = new SysDict();
                sysDict.setName(yuntsoftEnum.getName());
                sysDict.setValue(yuntsoftEnum.getValue().toString());
                sysDict.setType(sysDictType.getTypeId());
                sysDict.setDescription(sysDictType.getTypeName());
                sysDict.setEnabled(yuntsoftEnum.getEnable());
                typeValues.add(sysDictType.getTypeId() + sysDict.getValue());
                sysDictList.add(sysDict);
            }
        }
        // 扫描后的数据
        if (ObjectUtils.isEmpty(sysDictTypeList)) {
            return;
        }
        sysDictTypeService.insertOrUpdateAllColumnBatch(sysDictTypeList, sysDictTypeList.size());
        //
        //数据库查找
        List<String> oldDictByTypeAndValue = sysDictJpaRepository.findOldByTypeAndValue(typeValues);
        //需要添加的
        List<SysDict> saveSysDictList = sysDictList.stream().filter(sysDict -> !oldDictByTypeAndValue.contains(sysDict.getType() + sysDict.getValue())).collect(Collectors.toList());
        sysDictJpaRepository.saveAll(saveSysDictList);
        System.out.println(oldDictByTypeAndValue);
        log.info("<---------枚举自动装载数据库结束---------->");

    }

    /**
     * 驼峰转换下划线并代替特殊字段
     *
     * @param name
     * @return
     */
    private String enmuNameFormat(String name) {
        //                TODO  Enums
        name = name.replace("Enums", "");
        name = CamelAndUnderLineConverter.humpToLine2(name);
        return name;
    }

//    public static void main(String[] args) {
//        WuEnumsService yuntsoftEnumsService = AnnotationUtils.findAnnotation(PermissionTypeEnums.class, WuEnumsService.class);
//        Class c = PermissionTypeEnums.class;
//        Annotation[] a = PermissionTypeEnums.class.getAnnotations();
//        System.out.println(a);
//    }


}
