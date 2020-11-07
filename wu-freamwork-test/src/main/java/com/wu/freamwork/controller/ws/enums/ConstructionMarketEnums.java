package com.wu.freamwork.controller.ws.enums;

import com.wu.freamwork.controller.ws.domian.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2020/7/14 下午3:14
 */
@Getter
@AllArgsConstructor
public enum ConstructionMarketEnums {
    COMP_COMMENDATION("951C88F86FF8171EEFA49A221623D708", "信用_公路建设市场企业表彰信息表", CompCommendation.class),
    COMP_REGISTER("3BAB6A50D8B6916BA2C37397024CC67E", "信用_公路建设市场企业登记信息表", CompRegister.class),
    COMP_BUSINESS_QUALIFICATION("31B0DCC6EE79D7855697233205090391", "25413信用_公路建设市场企业经营资质信息表", CompBusinessQualification.class),
    COMP_CREDIT_FEEDBACK("E4F2EDEDAE06757FB216852888E7EF38", "信用_公路建设市场企业信用奖惩反馈信息表", CompCreditFeedback.class),
    COMP_ADMINISTRATIVE_SANCTION("719AF1F6726EFAFB7FB2C4265C0BE7D4", "信用_公路建设市场企业行政处罚信息表", CompAdministrativeSanction.class),
    COMP_ACHIEVEMENT("5B5012B858CE9A7F70CEBB2976ADF8E1", "信用_公路建设市场企业业绩信息表", CompAchievement.class),
    PERSON_COMMENDATION("C811E70D17A3C4EC29BDE233E0D8028F", "信用_公路建设市场人员表彰信息表", PersonCommendation.class),
    PERSON_PERSONAL("2AE098397BCC52B9ACED54C10D1F9192", "信用_公路建设市场人员个人信息表", PersonPersonal.class),
    PERSON_REWARD("2B70286DDE922ECD2A6A2EC6FBF7AB15", "信用_公路建设市场人员奖励信息表", PersonReward.class),
    PERSON_REWPUN_FEEDBACK("03107F69CA622974A29C1E38131A8154", "信用_公路建设市场人员信用奖惩反馈信息表", PersonRewpunFeedback.class),
    PERSON_PUNISH("782193FB631DBB2F3D53658704915F14", "信用_公路建设市场人员行政处罚信息表", PersonPunish.class),
    PERSON_ACHIEVEMENT("78BD69A025A254B1E9CA9CC0A58BE46E", "信用_公路建设市场人员业绩信息表", PersonAchievement.class),
    PERSON_PROFESSIONAL_QUALIFICATION("C33F2D364E4B90897CF801E6E7C99373", "信用_公路建设市场人员职业资格信息表", PersonProfessionalQualification.class);

    private String key;
    private String msg;
    private Class clazz;

}