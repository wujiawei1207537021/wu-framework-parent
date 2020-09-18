package com.wu.framework.easy.stereotype.upsert.ienum;

import java.util.Map;

public interface UserDictionaryService {


//    List<IEnum> userDictionary(Class<?> aClass);

    //             item   code
    Map<String,Map<String,String>> userDictionary(Class<?> aClass);


}
