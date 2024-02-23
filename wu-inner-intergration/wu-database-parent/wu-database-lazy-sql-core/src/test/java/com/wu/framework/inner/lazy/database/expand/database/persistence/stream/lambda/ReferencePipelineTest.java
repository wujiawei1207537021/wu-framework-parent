package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda;

import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import com.wu.framework.inner.lazy.persistence.converter.SQLConverter;
import org.junit.jupiter.api.Test;

class ReferencePipelineTest {

    public static void main(String[] args) {
//        ReferencePipeline objectReferencePipeline = new ReferencePipeline(null);
//
//        final Collection collection = objectReferencePipeline
//                .select()
//                .table(DataBaseUser.class)
//                .eq("name", "1")
//                .eq("age", "1")
//                .collection(DataBaseUser.class);
//        System.out.println(collection);
        final String s = SQLConverter.creatTableSQL(DataBaseUser.class);
        System.out.println(s);
    }

    @Test
    public void run() {
//        final LambdaBasicComparison<DataBaseUser> lambdaBasicComparison = new LambdaBasicComparison<DataBaseUser>();
//        lambdaBasicComparison.eq(DataBaseUser::getAddress, "");
//        final SimpleDefaultBasicComparison simpleDefaultBasicComparison = new SimpleDefaultBasicComparison();
//        simpleDefaultBasicComparison.eq(DataBaseUser::getAddress,"");
    }
}