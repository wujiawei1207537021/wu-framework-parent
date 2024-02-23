package com.wu.freamwork;

import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperationAutoStuffed;
import com.wu.framework.inner.lazy.database.smart.database.factory.AutoStuffedFactory;

public class AutoStuffedMain {

    public static void main(String[] args) {
        SmartLazyOperationAutoStuffed autoStuffedLazyOperation = AutoStuffedFactory.createAutoStuffed("127.0.0.1", 3306, "acw", "root", "wujiawei");
        autoStuffedLazyOperation.stuffed("acw", "annotation_code", 1L);
    }
}
