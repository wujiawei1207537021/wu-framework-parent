package com.wu.framework.play.platform.infrastructure.persistence;

import com.wu.framework.play.platform.infrastructure.entity.FileDO;
import com.wu.framework.play.platform.infrastructure.converter.FileConverter;
import com.wu.framework.play.platform.infrastructure.mapper.FileMapper;
import com.wu.framework.play.platform.domain.model.file.FileRepository;
import org.springframework.stereotype.Repository;
import java.util.stream.Collectors;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import com.wu.framework.play.platform.domain.model.file.File;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe file 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class FileRepositoryImpl  implements  FileRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增
     *
     * @param file 新增     
     * @return {@link Result<File>} 新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<File> story(File file) {
        FileDO fileDO = FileConverter.INSTANCE.fromFile(file);
        lazyLambdaStream.upsert(fileDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增
     *
     * @param fileList 批量新增     
     * @return {@link Result<List<File>>} 新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<File>> batchStory(List<File> fileList) {
        List<FileDO> fileDOList = fileList.stream().map(FileConverter.INSTANCE::fromFile).collect(Collectors.toList());
        lazyLambdaStream.upsert(fileDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个
     *
     * @param file 查询单个     
     * @return {@link Result<File>} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<File> findOne(File file) {
        FileDO fileDO = FileConverter.INSTANCE.fromFile(file);
        File fileOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(fileDO), File.class);
        return ResultFactory.successOf(fileOne);
    }

    /**
     * describe 查询多个
     *
     * @param file 查询多个     
     * @return {@link Result<List<File>>} 领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<List<File>> findList(File file) {
        FileDO fileDO = FileConverter.INSTANCE.fromFile(file);
        List<File> fileList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(fileDO), File.class);
        return ResultFactory.successOf(fileList);
    }

    /**
     * describe 分页查询多个
     *
     * @param size 当前页数
     * @param current 当前页
     * @param file 分页查询多个     
     * @return {@link Result<LazyPage<File>>} 分页领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<LazyPage<File>> findPage(int size,int current,File file) {
        FileDO fileDO = FileConverter.INSTANCE.fromFile(file);
        LazyPage<File> lazyPage = new LazyPage<>(current,size);
        LazyPage<File> fileLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(fileDO),lazyPage, File.class);
        return ResultFactory.successOf(fileLazyPage);
    }

    /**
     * describe 删除
     *
     * @param file 删除     
     * @return {@link Result<File>}      
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<File> remove(File file) {
        FileDO fileDO = FileConverter.INSTANCE.fromFile(file);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(fileDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在
     *
     * @param file 领域对象     
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    @Override
    public Result<Boolean> exists(File file) {
        FileDO fileDO = FileConverter.INSTANCE.fromFile(file);
        Boolean exists=lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(fileDO));
        return ResultFactory.successOf(exists);
    }

}