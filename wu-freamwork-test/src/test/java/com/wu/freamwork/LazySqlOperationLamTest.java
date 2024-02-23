package com.wu.freamwork;

import com.wu.framework.easy.upsert.core.dynamic.IUpsert;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.LambdaJoinBasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;
import com.wu.freamwork.domain.DataBaseAddress;
import com.wu.freamwork.domain.DataBaseUser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
class LazySqlOperationLamTest {

    @Autowired
    PerfectLazyOperation perfectLazyOperation;

    @Autowired
    IUpsert upsert;

    @Autowired
    LazySqlOperation lazySqlOperation;

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    public static void main(String[] args) {
        DataTransformUntil.simulationData(DataBaseAddress.class);
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

        DataBaseUser user = new DataBaseUser();
        user.setUsername("紧");
        user.setAddress("123456");
        user.setSex(null);
        user.setId(12);
        lazyLambdaStream.upsert(user);
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

        DataBaseUser user = new DataBaseUser();
        user.setUsername("紧");
        user.setAge(12);
        user.setAddress(null);
        user.setId(1);
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

        DataBaseUser user = new DataBaseUser();
        user.setUsername("紧");
        user.setAge(12);
        user.setAddress(null);
        user.setId(1);
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

        final Collection<DataBaseUser> collection
                = lazyLambdaStream.of(DataBaseUser.class).
                select(
                        LazyWrappers.<DataBaseUser>lambdaWrapper().eqIgnoreEmpty(DataBaseUser::getId, null).eq(DataBaseUser::getUsername, userName)

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

        DataBaseAddress address = new DataBaseAddress();
        address.setId(2977805307432827172L);
        address.setName("name");
        address.setLatitude(0.4486429466850429);
        address.setLongitude(0.9386839201502839);
        lazyLambdaStream.upsert(address);

        final DataBaseUser dataBaseUser = new DataBaseUser();
        dataBaseUser.setUsername("root").setAddress("安徽").setAge(15).setSex("男");
        lazyLambdaStream.upsert(dataBaseUser);

        final LambdaJoinBasicComparison lambdaJoinBasicComparison = new LambdaJoinBasicComparison<DataBaseUser, DataBaseAddress>() {
        };
        final Class<DataBaseAddress> classT2 = lambdaJoinBasicComparison.getClassT2();

        LazyWrappers.<DataBaseUser, DataBaseAddress>lambdaWrapperJoin();

        String userName = "紧";
        final Collection<DataBaseUser> collection
                = lazyLambdaStream.of(DataBaseUser.class).
                select(
                        LazyWrappers.<DataBaseUser>lambdaWrapper()
                                .eqIgnoreEmpty(DataBaseUser::getId, null)
                                .eq(DataBaseUser::getUsername, userName)
                                .eq(DataBaseUser::getAddress, "123456")
                                .leftJoin(LazyWrappers.<DataBaseUser, DataBaseAddress>lambdaWrapperJoin().eq(DataBaseUser::getUsername, DataBaseAddress::getId))
                                .rightJoin(LazyWrappers.<DataBaseUser, DataBaseUser>lambdaWrapperJoin().eq(DataBaseUser::getUsername, DataBaseUser::getId))
                ).collection();
        System.out.println(collection);
    }


    @Test()
    public void selectWithBasicComparison() {

        String userName = "紧";
        final Collection<DataBaseUser> collection
                = lazyLambdaStream.
                select(
                        LazyWrappers.<DataBaseUser>lambdaWrapper()
                                .eqIgnoreEmpty(DataBaseUser::getId, null)
                                .eq(DataBaseUser::getUsername, userName)
                                .eq(DataBaseUser::getAddress, "123456")
                                .leftJoin(LazyWrappers.<DataBaseUser, DataBaseAddress>lambdaWrapperJoin().eq(DataBaseUser::getUsername, DataBaseAddress::getId))
                                .rightJoin(LazyWrappers.<DataBaseUser, DataBaseUser>lambdaWrapperJoin().eq(DataBaseUser::getUsername, DataBaseUser::getId))
                ).collection();
        System.out.println(collection);
    }


    @Test()
    public void selectWithAsBasicComparison() {

        String userName = "紧";
        final Collection<DataBaseUser> collection
                = lazyLambdaStream.
                select(
                        LazyWrappers.<DataBaseUser>lambdaWrapper()
                                .eqIgnoreEmpty(DataBaseUser::getId, null)
                                .eq(DataBaseUser::getUsername, userName)
                                .eq(DataBaseUser::getAddress, "123456")
                                .leftJoin(LazyWrappers.<DataBaseUser, DataBaseAddress>lambdaWrapperJoin().eq(DataBaseUser::getUsername, DataBaseAddress::getId))
                                .rightJoin(LazyWrappers.<DataBaseUser, DataBaseUser>lambdaWrapperJoin().eq(DataBaseUser::getUsername, DataBaseUser::getId))
                                .as(DataBaseUser::getAddress, "hh")
                                .as(DataBaseUser::getAddress, DataBaseUser::getUsername)
                ).collection();
        System.out.println(collection);
    }

}