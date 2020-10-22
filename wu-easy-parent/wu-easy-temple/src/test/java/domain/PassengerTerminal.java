package domain;

import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import lombok.Data;

import java.util.Date;

/**
 * description 客运场站
 *
 * @author 吴佳伟
 * @date 2020/10/21 上午9:24
 */
@Data
// 道路运输-道路管理-
@EasyTable(name = "rt_rfm_pub_oth_depot_infr_station", comment = "客运/货运场站")
public class PassengerTerminal {

    @EasyTableField(comment = "业户名称")
    private String compName;

    @EasyTableField(comment = "所属机构")
    private String affiliation;

    @EasyTableField(comment = "经营范围")
    private Integer businessScope;

    @EasyTableField(comment = "经营范围中文")
    private String businessScopeName;

    @EasyTableField(comment = "经营状态")
    private Integer operatingStatus;

    @EasyTableField(comment = "经营状态中文")
    private Integer operatingStatusName;

    @EasyTableField(comment = "分支机构编码")
    private String branchCode;

    @EasyTableField(comment = "道路运输证号")
    private String roadTransportLicenseNum;

    @EasyTableField(comment = "地址")
    private String address;

    @EasyTableField(comment = "许可证号")
    private String permitNum;

    @EasyTableField(comment = "截止日期")
    private Date deadline;

    @EasyTableField(comment = "经济类型")
    private Integer economicType;

    @EasyTableField(comment = "经济类型中文")
    private Integer economicTypeName;

    @EasyTableField(comment = "法定代表人")
    private String legalRepresentative;

    @EasyTableField(comment = "联系电话")
    private String contactNum;

    @EasyTableField(comment = "是否分支")
    private Integer isBranch;

    @EasyTableField(comment = "是否分支中文")
    private Integer isBranchName;

    @EasyTableField(comment = "经度")
    private double lng;

    @EasyTableField(comment = "纬度")
    private double lat;

}
