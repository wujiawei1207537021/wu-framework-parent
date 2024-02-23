package com.wu.framework.easy.excel.stereotype;

import com.wu.framework.easy.excel.endpoint.EasyFilePoint;
import com.wu.framework.easy.excel.toolkit.DynamicEasyFileContextHolder;
import com.wu.framework.easy.excel.web.EasyFileResponseHandler;
import com.wu.framework.inner.layer.toolkit.DynamicLazyAttributeContextHolder;

import java.lang.annotation.*;

/**
 * describe: 导出文件
 * 将字符串导出为文件
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2023/1/2 18:42
 * <p>
 * @EasyFile(fileName = "导出upsert-sql", suffix = "sql")
 * @ApiOperation("下载insert-sql")
 * @GetMapping("/export/upsert/sql") public String exportUpsertSql(
 * @RequestParam String instanceId,
 * @RequestParam String schemaName,
 * @RequestParam String tableName){
 * return databaseTableService.exportUpsertSql(instanceId,schemaName,tableName);
 * }
 * 灵活自定义参考
 * @see DynamicEasyFileContextHolder#push(EasyFilePoint)
 * @see EasyFileResponseHandler
 *
 * </p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyFile {

    /**
     * 文件名
     *
     * @return String
     * <p>
     * to dynamic fileName
     * Class:EasyFile  Object:EasyExcelPoint
     * @see DynamicLazyAttributeContextHolder#push(Class, Object)
     */
    String fileName() default "";

    /**
     * 文件后缀
     *
     * @return String
     */
    String suffix() default "";

    /**
     * 文件类型
     */
    FileType fileType() default FileType.STRING_TYPE;

    /**
     * describe: 文件类型
     *
     * @author : Jia wei Wu
     * @version : 1.0
     * @date : 2023/1/2 18:46
     */
    enum FileType {
        STRING_TYPE,
        FILE_TYPE,
        BYTE_TYPE
    }

}
