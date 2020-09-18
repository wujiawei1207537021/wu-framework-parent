package com.wu.framework.easy.stereotype.upsert.component;


import org.springframework.lang.NonNull;
import java.util.List;


public interface IUpsert {


    <T> Boolean upsert(@NonNull List<T> list);

}
