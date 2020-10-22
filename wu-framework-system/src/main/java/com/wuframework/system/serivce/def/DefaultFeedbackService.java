package com.wuframework.system.serivce.def;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wuframework.pojo.qo.UniversalSearchQO;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.system.common.entity.DefaultSysUserDetails;
import com.wuframework.system.common.entity.SysFeedback;
import com.wuframework.system.persistence.mapper.DefaultFeedbackMapper;
import com.wuframework.system.serivce.FeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service("defaultFeedbackService")
public class DefaultFeedbackService extends ServiceImpl<DefaultFeedbackMapper, SysFeedback> implements FeedbackService {

    @Override
    public Result saveFeedBack(SysFeedback sysFeedback, DefaultSysUserDetails defaultSysUserDetails) {
        if (ObjectUtils.isEmpty(sysFeedback.getFeedbackUserPhone())) {
            sysFeedback.setFeedbackUserPhone(defaultSysUserDetails.getMobile());
            sysFeedback.setFeedbackUserId(defaultSysUserDetails.getUserId());
        }
        sysFeedback.insert();
        return ResultFactory.successOf();
    }

    @Override
    public Result selectFeedBackList(UniversalSearchQO universalSearchQO, Page page, DefaultSysUserDetails defaultSysUserDetails) {
        Wrapper<SysFeedback> feedbackEntityWrapper = new EntityWrapper<>();
        Optional.ofNullable(universalSearchQO.getType()).ifPresent(type -> feedbackEntityWrapper.eq("feedback_type", type));
        Optional.ofNullable(universalSearchQO.getStatus()).ifPresent(status -> feedbackEntityWrapper.eq("feedback_status", status));
        Optional.ofNullable(universalSearchQO.getKeyWord()).ifPresent(keyWord -> feedbackEntityWrapper.like("feedback_content", keyWord));
        if (universalSearchQO.getPagination()) {
            baseMapper.selectPage(page, feedbackEntityWrapper);
        }
        return ResultFactory.successOf(baseMapper.selectList(feedbackEntityWrapper));
    }

    @Override
    public Result updateFeedback(SysFeedback sysFeedback, DefaultSysUserDetails defaultSysUserDetails) {
        sysFeedback.updateById();
        return ResultFactory.successOf();
    }


}
