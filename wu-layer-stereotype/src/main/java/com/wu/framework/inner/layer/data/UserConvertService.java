package com.wu.framework.inner.layer.data;

import java.util.Map;

public interface UserConvertService {


//    List<IEnum> userConvert(Class<?> aClass);

    //             item   code
    Map<String, Map<String, String>> userConvert(Class<?> aClass);


}
