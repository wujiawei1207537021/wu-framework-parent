package com.wu.smart.acw.server.service;

import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.uo.ProjectUo;

public interface ProjectService {
    Result save(ProjectUo project);
}
