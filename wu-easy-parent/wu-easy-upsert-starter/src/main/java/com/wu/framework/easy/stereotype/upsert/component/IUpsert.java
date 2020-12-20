package com.wu.framework.easy.stereotype.upsert.component;


import org.springframework.lang.NonNull;


public interface IUpsert {


    <T> Boolean upsert(@NonNull Object... objects);

}
