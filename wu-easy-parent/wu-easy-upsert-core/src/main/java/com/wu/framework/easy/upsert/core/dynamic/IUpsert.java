package com.wu.framework.easy.upsert.core.dynamic;


import org.springframework.lang.NonNull;


public interface IUpsert {


    <T> Object upsert(@NonNull Object... objects);

}
