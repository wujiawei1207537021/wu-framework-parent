package com.wuframework.system.common.dto;

import lombok.Data;

/**
 * @author ：wq
 * @date ：Created in 2019/6/24 10:04
 * @description：用户个性签名DTO
 * @modified By：
 * @version:
 */
@Data
public class SignatureDTO {

    /**
     * 图片64位字符串
     */
    private String base64;
    private Integer width;
    private Integer height;
}
