package com.wu.framework.repository;

import com.wu.framework.response.Result;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@NoRepositoryBean
public interface WuCrudRepository<R,T, ID>  extends WuRepository<R> {

    @PostMapping()
    <S extends T> Result<S> saveOne(@RequestBody S s);

    @PostMapping("/list")
    <S extends T> Result<Iterable<S>> saveList(Iterable<S> sIterable);

    @PutMapping()
    <S extends T> Result updateOne(@RequestBody S s);

    @GetMapping("/{id}")
    Result<T> findById(@PathVariable ID id);

    @GetMapping("/exists/{id}")
    Result<Boolean> existsById(@PathVariable ID id);

    @GetMapping("/list")
    <S extends T> Result<Iterable<S>> findList();

    @GetMapping("/ids/{ids}")
    Result<Iterable<T>> findByIds(@PathVariable Iterable<ID> ids);

    @GetMapping("/count")
    Result<Long> count();

    @DeleteMapping("/{ids}")
    Result deleteByIds(@PathVariable List<ID> ids);

    @DeleteMapping("/one")
    Result deleteOne(@RequestBody T t);

    @DeleteMapping("/list")
    Result deleteList(@RequestBody Iterable<? extends T> var1);

    @DeleteMapping()
    Result deleteList();

}
