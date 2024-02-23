package com.wu.framework.easy.excel.endpoint;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;

import java.lang.reflect.Field;
import java.util.List;

/**
 * description  EasyExcelFiledPoint
 *
 * @author 吴佳伟
 * @date 2023/05/11 17:44
 * @see EasyExcelFiled
 */
@Data
public class EasyExcelFiledPoint {
    /**
     * 列名
     *
     * @return String
     */
    String name;

    /**
     * 列宽
     *
     * @return
     */
    int width = 30;

    /**
     * 设置自动换行
     *
     * @return
     */
    boolean wrapText = false;

    /**
     * 背景填充
     */
    FillPatternType fillPatternType = FillPatternType.SOLID_FOREGROUND;

    /**
     * title背景颜色
     *
     * @return
     */
    short titleBackgroundColor = 0x30;

    /**
     * 标题字体颜色
     *
     * @return
     */
    short titleFontColor = 16;

    /**
     * 标题字体
     *
     * @return
     */
    String titleFontName = "黑体";

    /**
     * 标题字体大小
     *
     * @return
     */
    short titleFontSize = 12;

    /**
     * 列字体颜色
     *
     * @return
     */
    short columnFontColor = 16;

    /**
     * 列字体
     *
     * @return
     */
    String columnFontName = "黑体";

    /**
     * 列字体大小
     *
     * @return
     */
    short columnFontSize = 10;

    /**
     * 列背景
     *
     * @return
     */
    short columnBackgroundColor = 0x30;
//    /**
//     * 列背景表达式
//     */
//    String columnBGExpression= "";

    /**
     * 下拉框选项
     *
     * @return
     */
    String[] dropdownOptions = {};

    /**
     * 字段合并方式
     *
     * @return
     */
    EasyExcelFiled.EasyExcelFieldMerge fieldMerge = EasyExcelFiled.EasyExcelFieldMerge.NONE;

    /**
     * 序号 数值越大越靠前
     */
    int serialNumber = 0;

    /**
     * 当前字段
     */
    Field field;
    /**
     * 当前字段名称
     */
    String fieldName;
    /**
     * 字段注解
     */
    EasyExcelFiled easyExcelFiled;


    /**
     * 当前列
     */
    Integer currentColumnIndex;
    /**
     * 字段是对象的情况下对应的表头数据
     */
    List<EasyExcelFiledPoint> excelBeanFiledPointList;

    /**
     * 字段是否为 非基本数据类型
     * true 非基本数据类型
     * false 基本数据类型
     */
    boolean isBeanFiled;
    /**
     * 字段是否为 集合
     * true 集合
     * false 非集合
     */
    boolean isCollectionFiled;

    /**
     * 字段是否为 Map
     * true Map
     * false 非Map
     */
    boolean isMapFiled;

    /**
     * 字段格式
     */
    CellStyle cellStyle;
}
