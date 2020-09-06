package com.wu.framework.inner.db.utils;

import com.baomidou.mybatisplus.plugins.Page;
import com.sun.istack.NotNull;
import com.wu.framework.inner.db.annotation.CustomMultiParamFunction;
import com.wu.framework.inner.db.config.pro.DBPageConfigPro;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ValGet {

    public static PageRequest getJpaPageRequest4Request(HttpServletRequest request, DBPageConfigPro dbPageConfigPro) {
        return getPageRequest(request, dbPageConfigPro);
    }

    public static Page getPageRequest4Request(HttpServletRequest request, DBPageConfigPro dbPageConfigPro) {
        PageRequest pageRequest = getJpaPageRequest4Request(request, dbPageConfigPro);
        getPageAndSize(x->{
            return null;
        },request,dbPageConfigPro);
        return new Page(pageRequest.getPageNumber() + 1, pageRequest.getPageSize());
    }

    @NotNull
    private static PageRequest getPageRequest(HttpServletRequest request, DBPageConfigPro dbPageConfigPro) {
        Integer current =
                Optional.ofNullable(request.getParameter(dbPageConfigPro.getPageAlias()))
                        .map(Integer::valueOf)
                        .orElse(dbPageConfigPro.getPageNum());
        Integer size =
                Optional.ofNullable(request.getParameter(dbPageConfigPro.getSizeAlias()))
                        .map(Integer::valueOf)
                        .orElse(dbPageConfigPro.getPageSize());
        return PageRequest.of(current - 1, size);
    }

    private static Object getPageAndSize(CustomMultiParamFunction customMultiParamFunction, Object... objects){
    return customMultiParamFunction.multiParam(objects);
    }
}
