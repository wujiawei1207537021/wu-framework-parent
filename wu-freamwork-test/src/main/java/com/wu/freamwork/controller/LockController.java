package com.wu.freamwork.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.freamwork.domain.DataBaseUser;
import com.wu.freamwork.service.LockeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2023/01/04 9:45 上午
 */
@EasyController("/lock")
public class LockController {


    private final LockeService lockeService;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 20, 20, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(20));

    public LockController(LockeService lockeService) {
        this.lockeService = lockeService;
    }


    @GetMapping("/sameId")
    @ApiOperation(value = "当前方法并发id 30s内会被锁住")
    public void sameId(String id) {
        threadPoolExecutor.execute(() -> {
            try {
                lockeService.lockId(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }


    @GetMapping("/sameName")
    @ApiOperation(value = "当前方法并发 username 30s内会被锁住")
    public void sameName(@ModelAttribute DataBaseUser dataBaseUser) {
        threadPoolExecutor.execute(() -> {
            try {
                lockeService.lockName(dataBaseUser);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

}
