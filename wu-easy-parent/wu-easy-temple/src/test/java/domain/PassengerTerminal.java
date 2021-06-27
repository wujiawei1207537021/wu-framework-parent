package domain;

import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.EasySmartField;
import lombok.Data;

import java.util.Date;

/**
 * description 客运场站
 *
 * @author Jia wei Wu
 * @date 2020/10/21 上午9:24
 */
@Data
// 道路运输-道路管理-
@EasySmart(tableName = "rt_rfm_pub_oth_depot_infr_station", comment = "客运/货运场站")
public class PassengerTerminal {

    @EasySmartField(comment = "业户名称")
    private String compName;

    @EasySmartField(comment = "所属机构")
    private String affiliation;

    @EasySmartField(comment = "经营范围")
    private Integer businessScope;

    @EasySmartField(comment = "经营范围中文")
    private String businessScopeName;

    @EasySmartField(comment = "经营状态")
    private Integer operatingStatus;

    @EasySmartField(comment = "经营状态中文")
    private Integer operatingStatusName;

    @EasySmartField(comment = "分支机构编码")
    private String branchCode;

    @EasySmartField(comment = "道路运输证号")
    private String roadTransportLicenseNum;

    @EasySmartField(comment = "地址")
    private String address;

    @EasySmartField(comment = "许可证号")
    private String permitNum;

    @EasySmartField(comment = "截止日期")
    private Date deadline;

    @EasySmartField(comment = "经济类型")
    private Integer economicType;

    @EasySmartField(comment = "经济类型中文")
    private Integer economicTypeName;

    @EasySmartField(comment = "法定代表人")
    private String legalRepresentative;

    @EasySmartField(comment = "联系电话")
    private String contactNum;

    @EasySmartField(comment = "是否分支")
    private Integer isBranch;

    @EasySmartField(comment = "是否分支中文")
    private Integer isBranchName;

    @EasySmartField(comment = "经度")
    private double lng;

    @EasySmartField(comment = "纬度")
    private double lat;

}
