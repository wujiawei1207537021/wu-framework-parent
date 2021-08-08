package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda;

import com.wu.framework.inner.lazy.database.test.pojo.DataBaseUser;

import java.util.Collection;

class ReferencePipelineTest {

    public static void main(String[] args) {
        ReferencePipeline<Object> objectReferencePipeline = new ReferencePipeline<Object>(null);

        final Collection collection = objectReferencePipeline
                .select()
                .table(DataBaseUser.class)
                .eq("name", "1")
                .eq("age", "1")
                .collection(DataBaseUser.class);
        System.out.println(collection);
    }
}