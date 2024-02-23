package com.wu.freamwork;

import com.wu.framework.authorization.model.User;
import com.wu.framework.easy.upsert.core.dynamic.IUpsert;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.LambdaJoinBasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;
import com.wu.framework.inner.lazy.database.test.pojo.Address;
import com.wu.framework.inner.lazy.database.test.pojo.DataBaseUser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
class LazyOperationLamTest {

    @Autowired
    PerfectLazyOperation perfectLazyOperation;

    @Autowired
    IUpsert upsert;

    @Autowired
    LazyOperation lazyOperation;

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    public static void main(String[] args) {
        DataTransformUntil.simulationData(Address.class);
    }

    /**
     * describe smartUpsert
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/20 8:53 下午
     **/
    @Test()
    public void smartUpsert() {

        User user = new User();
        user.setUsername("紧");
        user.setPassword("123456");
        user.setPermissionList(null);
        user.setId("12");
        lazyLambdaStream.smartUpsert(user);
    }

    /**
     * describe upsert
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/20 8:53 下午
     **/
    @Test()
    public void upsert() {

        User user = new User();
        user.setUsername("紧");
        user.setPassword("123456");
        user.setPermissionList(null);
        user.setId("12");
        lazyLambdaStream.upsert(user);
    }

    /**
     * describe insert
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/20 8:53 下午
     **/
    @Test()
    public void insert() {

        User user = new User();
        user.setUsername("紧");
        user.setPassword("123456");
        user.setPermissionList(null);
        user.setId("12");
        lazyLambdaStream.insert(user);
    }

    /**
     * describe select
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/20 8:53 下午
     **/
    @Test()
    public void select() {

        String userName = "紧";

        final Collection<User> collection
                = lazyLambdaStream.of(User.class).
                select(
                        LazyWrappers.<User>lambdaWrapper().eqIgnoreEmpty(User::getId, null).eq(User::getUsername, userName)

                ).collection();
        System.out.println(collection);
    }

    /**
     * describe selectLeftJoin
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/20 8:53 下午
     **/
    @Test()
    public void selectLeftJoin() {

        Address address = new Address();
        address.setId(2977805307432827172L);
        address.setName("name");
        address.setLatitude(0.4486429466850429);
        address.setLongitude(0.9386839201502839);
        lazyLambdaStream.smartUpsert(address);

        final DataBaseUser dataBaseUser = new DataBaseUser();
        dataBaseUser.setUsername("root").setAddress("安徽").setAge(15).setSex("男");
        lazyLambdaStream.smartUpsert(dataBaseUser);

        final LambdaJoinBasicComparison lambdaJoinBasicComparison = new LambdaJoinBasicComparison<User, Address>() {
        };
        final Class<Address> classT2 = lambdaJoinBasicComparison.getClassT2();

        LazyWrappers.<User, Address>lambdaWrapperJoin();

        String userName = "紧";
        final Collection<User> collection
                = lazyLambdaStream.of(User.class).
                select(
                        LazyWrappers.<User>lambdaWrapper()
                                .eqIgnoreEmpty(User::getId, null)
                                .eq(User::getUsername, userName)
                                .eq(User::getPassword, "123456")
                                .leftJoin(LazyWrappers.<User, Address>lambdaWrapperJoin().eq(User::getUsername, Address::getId))
                                .rightJoin(LazyWrappers.<User, DataBaseUser>lambdaWrapperJoin().eq(User::getUsername, DataBaseUser::getId))
                ).collection();
        System.out.println(collection);
    }

}