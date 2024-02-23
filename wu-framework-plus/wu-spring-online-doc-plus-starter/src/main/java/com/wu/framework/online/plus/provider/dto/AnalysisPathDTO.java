package com.wu.framework.online.plus.provider.dto;

import lombok.Data;

import java.util.List;

/**
 * description 解析后的URL对象
 *
 * @author 吴佳伟
 * @date 2023/10/13 14:38
 */
@Data
public class AnalysisPathDTO {
    // 参数
    private List<ParamDTO> paramList;
    // 结果
    private List<ParamDTO> resultList;
}
