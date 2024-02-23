package com.wu.freamwork;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.LambdaBuild;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.factory.LambdaBuildFactory;


public class Demo {


    public static void main(String[] args) {
//        final LambdaBuild lambdaBuild = new LambdaBuild(null);
//        LambdaBuild<User> userLambdaBuild = new LambdaBuild<User>(null) {
//        };
//        final Class classT = userLambdaBuild.getClassT();
//        final Class<User> classT = lambdaBuild.getClassT();
        // Type arguments are redundant for the non-generic method call

//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.readValue("jsonStr", new TypeReference<List<Job>>() {});
//        final Execute select = lambdaBuild.<User>select();
        final LambdaBuild<String> userBuild = LambdaBuildFactory.of(String.class);
        final Class<String> classT = userBuild.getClassT();

    }
}
