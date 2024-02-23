//package com.wu.framework.repository.mybatis;
//
//import com.baomidou.mybatisplus.activerecord.Model;
//import com.baomidou.mybatisplus.mapper.BaseMapper;
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.wu.framework.repository.WuCrudRepository;
//import com.wu.framework.response.Result;
//import com.wu.framework.response.ResultFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.CollectionUtils;
//
//import java.io.Serializable;
//import java.util.List;
//
//public class SimpleMybatisWuRepository<M extends BaseMapper<T>, T extends Model<T>, ID extends Serializable> implements WuCrudRepository<M, T, ID> {
//
//    @Autowired
//    protected M baseMapper;
//
//    @Override
//    public <S extends T> Result<S> saveOne(S s) {
//        s.insert();
//        return ResultFactory.successOf(s);
//    }
//
//    @Override
//    public <S extends T> Result<Iterable<S>> saveList(Iterable<S> iterable) {
//        for (S s : iterable) {
//            s.insert();
//        }
//        return ResultFactory.successOf(iterable);
//    }
//
//    @Override
//    public <S extends T> Result updateOne(S s) {
//        s.updateById();
//        return ResultFactory.successOf(s);
//    }
//
//    @Override
//    public Result<T> findById(ID id) {
//        return ResultFactory.successOf(baseMapper.selectById(id));
//    }
//
//    @Override
//    public Result<Boolean> existsById(ID id) {
//        return ResultFactory.successOf(baseMapper.selectById(id) != null);
//    }
//
//    @Override
//    public <S extends T> Result<Iterable<S>> findList() {
//        return ResultFactory.successOf(baseMapper.selectList(null));
//    }
//
//    @Override
//    public Result<Iterable<T>> findByIds(Iterable<ID> ids) {
//        return ResultFactory.successOf(baseMapper.selectBatchIds(CollectionUtils.arrayToList(ids)));
//    }
//
//    @Override
//    public Result<Long> count() {
//        return ResultFactory.successOf(baseMapper.selectCount(null));
//    }
//
//    @Override
//    public Result deleteList() {
//        baseMapper.delete(null);
//        return ResultFactory.successOf();
//    }
//
//    @Override
//    public Result deleteList(Iterable<? extends T> sIterable) {
//        baseMapper.deleteBatchIds(CollectionUtils.arrayToList(sIterable));
//        return ResultFactory.successOf();
//    }
//
//    @Override
//    public Result deleteByIds(List<ID> ids) {
//        baseMapper.deleteBatchIds(ids);
//        return ResultFactory.successOf();
//    }
//
//    @Override
//    public Result deleteOne(T t) {
//        EntityWrapper entityWrapper = new EntityWrapper(t);
//        baseMapper.delete(entityWrapper);
//        return ResultFactory.successOf();
//    }
//}