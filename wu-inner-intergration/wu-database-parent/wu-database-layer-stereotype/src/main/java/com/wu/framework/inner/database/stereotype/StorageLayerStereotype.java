package com.wu.framework.inner.database.stereotype;

import com.wu.framework.inner.layer.stereotype.Layer;
import com.wu.framework.inner.layer.stereotype.LayerDefault;

/**
 * description  存储 stereotype
 *
 * @author Jia wei Wu
 * @date 2021/4/1 上午11:16
 */
public interface StorageLayerStereotype extends LayerDefault, Layer {

    String value();
}
