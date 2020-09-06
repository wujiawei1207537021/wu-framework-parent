package com.wuframework.system.serivce;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wuframework.pojo.qo.UniversalSearchQO;
import com.wuframework.response.Result;
import com.wuframework.system.common.entity.DefaultSysUserDetails;
import com.wuframework.system.common.entity.SysFeedback;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Xiong xz
 * @since 2018-10-23
 */
public interface FeedbackService extends IService<SysFeedback> {
    /**
     * 保存用户反馈
     *
     * @param sysFeedback 用户反馈
     * @param defaultSysUserDetails     当前用户
     * @return
     */
    Result saveFeedBack(SysFeedback sysFeedback, DefaultSysUserDetails defaultSysUserDetails);

    /**
     * 查询用户反馈
     * @param universalSearchQO
     * @param page
     * @param defaultSysUserDetails
     * @return
     */

    Result selectFeedBackList(UniversalSearchQO universalSearchQO, Page page, DefaultSysUserDetails defaultSysUserDetails);

    /**
     * 更新
     * @param sysFeedback
     * @param defaultSysUserDetails
     * @return
     */
    Result updateFeedback(SysFeedback sysFeedback, DefaultSysUserDetails defaultSysUserDetails);
}
