package com.wu.bionic.language.vocalize.uo;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/8 7:52 下午
 */
@Accessors(chain = true)
@Data
@EasySmart(perfectTable = true)
public class MusicUo {

    private MultipartFile multipartFile;
    private String musicName;
    private String singer;
    private String album;
}
