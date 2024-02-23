package com.wu.framework.inner.lazy.persistence.reverse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * description 逆向工程 class name接口
 *
 * @author 吴佳伟
 * @date 2023/02/14 13:20
 */
public interface JavaReverseEngineeringClassName extends JavaReverseEngineeringAnnotation {

    /**
     * description:  添加 class
     *
     * <p>
     * public class FileProvider extends AbstractLazyCrudProvider<FileUo, Long> {
     * </p>
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    void initClassNamePart();

    /**
     * description:  添加 class
     *
     * @param classNamePart
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    void addClassNamePart(String classNamePart);


    List<String> getClassNamePart();

    /**
     * 获取 文件 name
     *
     * @return
     */
    String getFileName();

    /**
     * description 生成类注释
     *
     * @param comment 表描述信息
     * @return String 返回类描述信息
     * @date 2022/2/15 1:41 下午
     */
    default String generateClassDescribe(String comment) {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final int localDateTimeHour = localDateTime.getHour();
        String describe = String.format("/**\n" +
                        " * describe %s \n" +
                        " *\n" +
                        " * @author Jia wei Wu\n" +
                        " * @date %s %s\n" +
                        " * @see %s \n" +
                        " **/\n", comment,
                localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")),
                localDateTimeHour < 8 ? "夜间" : localDateTimeHour < 12 ? "上午" : localDateTimeHour < 18 ? "下午" : "晚上",
                this.getClass().getName()
        );
        return describe;
    }

}
