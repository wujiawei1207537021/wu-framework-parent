package com.wu.framework.repository.jpa;

import com.wu.framework.inner.lazy.database.expand.database.persistence.CrudRepository;
import com.wu.framework.repository.WuCrudRepository;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class SimpleJpaWuRepository<R extends CrudRepository<T, ID>, T, ID> implements WuCrudRepository<R, T, ID> {

    @Autowired
    public R repository;

    @Override
    public <S extends T> Result<S> saveOne(S s) {
        repository.save(s);
        return ResultFactory.successOf(s);
    }

    @Override
    public <S extends T> Result<Iterable<S>> saveList(Iterable<S> iterable) {
        repository.saveAll(iterable);
        return ResultFactory.successOf(iterable);
    }

    @Override
    public <S extends T> Result updateOne(S s) {
        repository.save(s);
        return ResultFactory.successOf(s);
    }

    @Override
    public Result<T> findById(ID id) {
        return ResultFactory.successOf(repository.findById(id));
    }

    @Override
    public Result<Boolean> existsById(ID id) {
        return ResultFactory.successOf(repository.existsById(id));
    }

    @Override
    public <S extends T> Result<Iterable<S>> findList() {
        return ResultFactory.successOf(repository.findAll());
    }

    @Override
    public Result<Iterable<T>> findByIds(Iterable<ID> ids) {
        return ResultFactory.successOf(repository.findAllById(ids));
    }

    @Override
    public Result<Long> count() {
        return ResultFactory.successOf(repository.count());
    }

    @Override
    public Result deleteByIds(List<ID> ids) {
        for (ID id : ids) {
            repository.deleteById(id);
        }
        return ResultFactory.successOf();
    }

    @Override
    public Result deleteList() {
        repository.deleteAll(null);
        return ResultFactory.successOf();
    }

    @Override
    public Result deleteList(Iterable<? extends T> sIterable) {
        repository.deleteAll(sIterable);
        return ResultFactory.successOf();
    }

    @Override
    public Result deleteOne(T t) {
        repository.delete(t);
        return ResultFactory.successOf();
    }
}