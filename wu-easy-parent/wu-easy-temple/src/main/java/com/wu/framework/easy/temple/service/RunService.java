package com.wu.framework.easy.temple.service;

import com.wu.framework.easy.temple.domain.UserLog;

import java.util.List;

public interface RunService {

    List<UserLog> run(Integer size);

    List<UserLog> run1();

    void run2(Integer size);

    List binary(Integer size);
}
