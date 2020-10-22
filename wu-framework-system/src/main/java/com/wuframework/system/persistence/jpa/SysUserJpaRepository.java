package com.wuframework.system.persistence.jpa;

import com.wuframework.system.common.entity.DefaultSysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserJpaRepository extends JpaRepository<DefaultSysUser, Integer> {
    /**
     * 通过钉钉的openID查询
     *
     * @param dingtalkOpenId
     * @return
     */
    DefaultSysUser findByDingtalkOpenId(String dingtalkOpenId);

    /**
     * 通过qq的openID查询
     *
     * @param qqOpenId
     * @return
     */
    DefaultSysUser findByQqOpenId(String qqOpenId);

    /**
     * 通过微信的openID查询
     *
     * @param weChatOpenId
     * @return
     */
    DefaultSysUser findByWeChatOpenId(String weChatOpenId);

    /**
     * 通过支付宝的openID查询
     *
     * @param alipayOpenId
     * @return
     */
    DefaultSysUser findByAlipayOpenId(String alipayOpenId);

    /**
     * 根据ID查询
     *
     * @param userId
     * @return
     */
    DefaultSysUser findByUserId(Integer userId);


}
