package com.wu.framework.inner.lazy.persistence.reverse;

import com.wu.framework.inner.layer.data.NormalUsedString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description 逆向工程 方法存储接口
 *
 * @author 吴佳伟
 * @date 2023/02/14 13:21
 */
public interface JavaReverseEngineeringMethod extends JavaReverseEngineeringField {
    /**
     * description:  添加 class 方法片段
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    void initClassMethodPart();

    /**
     * description:  添加 class 方法片段
     *
     * @param classMethodPart class 方法片段
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    void addClassMethodPart(String classMethodPart);

    /**
     * description:  获取添加 class 的字段片段
     *
     * @author 吴佳伟
     * @date: 13.2.23 17:13
     */
    List<String> getClassMethodPart();

    /**
     * description 生成类注释
     *
     * @param comment 表描述信息
     * @return String 返回类描述信息
     * @date 2022/2/15 1:41 下午
     */
    default String generateMethodDescribe(String comment) {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final int localDateTimeHour = localDateTime.getHour();
        String describe = String.format("    /**\n" +
                        "     * describe %s\n" +
                        "     *\n" +
                        "     * @param\n" +
                        "     * @return\n" +
                        "     * @exception/throws\n" +
                        "     * @author Jia wei Wu\n" +
                        "     * @date %s %s\n" +
                        "     **/\n", comment,
                localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")),
                localDateTimeHour < 8 ? "夜间" : localDateTimeHour < 12 ? "上午" : localDateTimeHour < 18 ? "下午" : "晚上");
        return describe;
    }

    /**
     * description 生成类注释
     *
     * @param comment           表描述信息
     * @param paramDescribeList 参数描述
     * @return String 返回类描述信息
     * @date 2022/2/15 1:41 下午
     */
    default String generateMethodDescribe(String comment, ParamDescribeList paramDescribeList) {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final int localDateTimeHour = localDateTime.getHour();
        String describe = String.format("    /**\n" +
                        "     * describe %s\n" +
                        "     *\n" +
                        paramDescribeList.getParamDescribeList().stream().map(paramDescribe -> {
                            //"     * @param\n" +
                            return "     * @param " + paramDescribe.getParam() + NormalUsedString.SPACE + paramDescribe.getDescribe();
                        }).collect(Collectors.joining(NormalUsedString.NEWLINE)) +
                        "     \n" +
                        paramDescribeList.getReturnParamDescribeList().stream().map(paramDescribe -> {
                            //"     * @return\n" +
                            // {@link Result}<{@link BsAxleDeadWeightSettingVO}>
                            return "     * @return {@link " + paramDescribe.getParam() + "}" + NormalUsedString.SPACE + paramDescribe.getDescribe();
                        }).collect(Collectors.joining(NormalUsedString.NEWLINE)) +
                        "     \n" +
                        paramDescribeList.getExceptionDescribeList().stream().map(paramDescribe -> {
                            //"     * @exception/throws\n" +
                            return "     * @exception/throws " + paramDescribe.getParam() + NormalUsedString.SPACE + paramDescribe.getDescribe();
                        }).collect(Collectors.joining(NormalUsedString.NEWLINE)) +
                        "     \n" +
                        "     * @author Jia wei Wu\n" +
                        "     * @date %s %s\n" +
                        "     **/\n", comment,
                localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")),
                localDateTimeHour < 8 ? "夜间" : localDateTimeHour < 12 ? "上午" : localDateTimeHour < 18 ? "下午" : "晚上");
        return describe;
    }

}

