package com.wu.freamwork.database.generator.entity;

import com.wu.freamwork.database.generator.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 模版参数抽象实体
 * @date : 2020/8/3 下午9:45
 */
@Data
public abstract class  TemplateParameterAbstractEntity {

    /**
     * 主方法路径
     */
    private String mainPath="com.wu";
    /**
     * 包名
     */
    private String packageName="com.wu";
    /**
     * 模块名
     */
    private String moduleName="module";
    /**
     * 作者
     */
    private String author="wujiawei";
    /**
     * 邮箱
     */
    private String email;
    /**
     * 表前缀
     */
    private String tablePrefix;
    /**
     * 创建时间
     */
    private String dateTime= DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);
}
