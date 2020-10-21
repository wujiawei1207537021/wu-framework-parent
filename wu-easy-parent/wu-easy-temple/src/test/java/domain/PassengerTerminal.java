package domain;

import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
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
@EasyTable(name = "rt_rfm_pub_oth_depot_infr_station",comment = "客运/货运场站")
public class PassengerTerminal {

    @EasyTableFile(comment = "业户名称")
    private String compName;

    @EasyTableFile(comment = "所属机构")
    private String affiliation;

    @EasyTableFile(comment = "经营范围")
    private Integer businessScope;

    @EasyTableFile(comment = "经营范围中文")
    private String businessScopeName;

    @EasyTableFile(comment = "经营状态")
    private Integer operatingStatus;

    @EasyTableFile(comment = "经营状态中文")
    private Integer operatingStatusName;

    @EasyTableFile(comment = "分支机构编码")
    private String branchCode;

    @EasyTableFile(comment = "道路运输证号")
    private String roadTransportLicenseNum;

    @EasyTableFile(comment = "地址")
    private String address;

    @EasyTableFile(comment = "许可证号")
    private String permitNum;

    @EasyTableFile(comment = "截止日期")
    private Date deadline;

    @EasyTableFile(comment = "经济类型")
    private Integer economicType;

    @EasyTableFile(comment = "经济类型中文")
    private Integer economicTypeName;

    @EasyTableFile(comment = "法定代表人")
    private String legalRepresentative;

    @EasyTableFile(comment = "联系电话")
    private String contactNum;

    @EasyTableFile(comment = "是否分支")
    private Integer isBranch;

    @EasyTableFile(comment = "是否分支中文")
    private Integer isBranchName;

    @EasyTableFile(comment = "经度")
    private double lng;

    @EasyTableFile(comment = "纬度")
    private double lat;

}
