package com.wu.freamwork.service.impl;

import com.wu.freamwork.domain.SysApiUserTestPO;
import com.wu.freamwork.service.SysApiTestService;
import com.wu.inner.sys.adapter.stereotype.NormalConvertMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/11/7 下午4:36
 */
@Service
public class SysApiTestServiceImpl implements SysApiTestService {


    @NormalConvertMapper
    @Override
    public List<SysApiUserTestPO> transformUser() {
        List<SysApiUserTestPO> sysApiUserTestPOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SysApiUserTestPO sysApiUserTestPO = new SysApiUserTestPO();
            sysApiUserTestPO.setSex(i % 3);
            sysApiUserTestPO.setAge(i);
            sysApiUserTestPO.setName("第:" + i);
            sysApiUserTestPOList.add(sysApiUserTestPO);
        }
        return sysApiUserTestPOList;
    }
}
