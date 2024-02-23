package com.wu.inner.sys.adapter;

import org.springframework.beans.factory.InitializingBean;


/**
 * description 字典适配器
 *
 * @author Jia wei Wu
 * @date 2020/8/17 下午7:48
 */
public interface ConvertAdapter extends InitializingBean {

    void convertObjects(Object... objects);

}
