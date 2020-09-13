package com.wu.framework.inner.database.util;

import com.wu.framework.inner.database.domain.CustomRepository;
import lombok.NonNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.*;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 扫描xml
 * @date : 2020/6/25 下午11:59
 */
public class ScanXmlPathUtil {

    private final static Log log = LogFactory.getLog(ScanXmlPathUtil.class);

    static ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    public static Map<String, CustomRepository> getCustomRepository(@NonNull List<String> scanXmlPath) {
        long start=System.currentTimeMillis();
        List<Resource> resources = new ArrayList<Resource>();
        if (scanXmlPath != null) {
            for (String mapperLocation : scanXmlPath) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        resources.toArray(new Resource[resources.size()]);
        System.out.println(resources);
        Map<String, CustomRepository> customRepositoryMap = new HashMap<>();
        for (Resource resource : resources) {
            try {
                customRepositoryMap.putAll(CustomXMLConfigBuilder.loadMapperConfiguration(resource.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end=System.currentTimeMillis();
        log.info("耗时:"+(end-start));
        return customRepositoryMap;
    }


    public static List<Class> getCustomScanBeanClass(@NonNull List<String> scanXmlPath) {
        long start=System.currentTimeMillis();
        List<Resource> resources = new ArrayList<>();
        for (String mapperLocation : scanXmlPath) {
            try {
                Resource[] mappers = resourceResolver.getResources(mapperLocation);
                resources.addAll(Arrays.asList(mappers));
            } catch (IOException e) {
                // ignore
            }
        }
        resources.toArray(new Resource[resources.size()]);
        List<Class> classList = new ArrayList<>();
        for (Resource resource : resources) {
            try {
                classList.add(CustomXMLConfigBuilder.loadMapperNamespace(resource.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end=System.currentTimeMillis();
        log.info("耗时:"+(end-start));
        return classList;
    }


}
