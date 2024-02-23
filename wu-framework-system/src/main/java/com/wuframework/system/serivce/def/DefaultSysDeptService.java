package com.wuframework.system.serivce.def;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.wuframework.system.common.consts.CacheConsts;
import com.wuframework.system.common.consts.ConfigConsts;
import com.wuframework.system.common.dto.DeptDTO;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.SysDept;
import com.wuframework.system.common.entity.SysDict;
import com.wuframework.system.common.vo.*;
import com.wuframework.system.persistence.mapper.SysDeptMapper;
import com.wuframework.system.redis.RedisRepository;
import com.wuframework.system.serivce.SysDeptService;
import com.wuframework.system.serivce.SysDictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

/**
 * @author : Xinogxz
 * @Date: 2018/7/11 09:51
 * @Description:
 */
@Slf4j
@Service("defaultSysDeptService")
public class DefaultSysDeptService extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final static String NUM = "99";

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表转换树
     *
     * @param list     部门树
     * @param startId  初始编号
     * @param optional 判断
     * @return
     */
    private List<TreeNode> nodeConvent(List<SysDept> list, String startId, Boolean optional) {
//        List<TreeNode> treeNodeList = new ArrayList<>();
//        List<SysDept> deptList = list.stream()
//                .filter(d -> startId.equals(d.getParentId().toString()))
//                .collect(Collectors.toList());
//        for (SysDept dept : deptList) {
//            TreeNode treeNode = new TreeNode();
//            treeNode.setIcon("");
//            treeNode.setKey(dept.getDeptId().toString());
//            treeNode.setTitle(dept.getDeptName());
//            treeNode.setValue(dept.getDeptId().toString());
//            List<TreeNode> treeNodes = nodeConvent(list, dept.getDeptId().toString(), optional);
//            treeNode.setChildren(treeNodes);
//            treeNode.setLeaf(treeNodes.size() == 0);
//            if (null == optional || !optional) {
//                treeNode.setDisabled(false);
//            } else {
//                treeNode.setDisabled(treeNodes.size() == 0);
//            }
//            treeNodeList.add(treeNode);
//        }
//        return treeNodeList;
        return null;
    }

    /**
     * 获取部门列表
     *
     * @return
     */
    @Override
    public List<TreeNode> listDept() {
        EntityWrapper<SysDept> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("enabled", true);
        List<SysDept> deptList = deptMapper.selectList(entityWrapper);
        if (ObjectUtils.isEmpty(deptList)) {
            return Collections.emptyList();
        }
        return nodeConvent(deptList, "0", false);
    }

    /**
     * 根据条件查询组织列表
     *
     * @param deptDTO 部门&市场DTO
     * @param page    分页
     * @return
     */
    @Override
    public Page getListDeptByDeptDTO(DeptDTO deptDTO, Page page) {
//        Page<DeptVO> page = new Page<>(pageParams.getCurrent(), pageParams.getSize());
        if (Objects.isNull(deptDTO.getNeedPage()) || deptDTO.getNeedPage()) {
            page.setRecords(this.deptMapper.selectListDeptByDeptDTO(deptDTO, page));
        } else {
            page.setRecords(this.deptMapper.selectListDeptByDeptDTOAll(deptDTO));
        }
        return page;
    }

    /**
     * 查询当前用户部门所在的所有机构
     *
     * @param deptType 部门级别（1，2····）
     * @param user     当前用户
     * @return
     */
    @Override
    public List<TreeNode> getDeptListAllTree(List<String> deptType, DefaultSysUser user) {
        List<TreeNode> treeNodeList = Lists.newArrayList();
        //查询当前部门及其父节点
        List<SysDept> list = this.deptMapper.selectJurisdiction(user.getDeptId().longValue());
        list = list.stream().filter(d -> d.getParentId() == 0).collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(list)) {
            List<SysDept> deptList = Lists.newArrayList();
            Long deptId = list.get(0).getDeptId();
            List<DeptNodeVO> deptNodeVOList = this.deptMapper.selectListDeptChildrenByType(deptId, null);
            //deptNodeVOList.add(JSON.parseObject(JSON.toJSONString(list.get(0)), DeptNodeVO.class));
            //list = JSON.parseArray(JSON.toJSONString(deptNodeVOList), SysDept.class);
            list.addAll(JSON.parseArray(JSON.toJSONString(deptNodeVOList), SysDept.class));
            if (deptType.size() > 0) {
                for (String type : deptType) {
                    List<SysDept> listTemp = list.stream().filter(d -> d.getDeptType() == Integer.parseInt(type)).collect(Collectors.toList());
                    deptList.addAll(listTemp);
                }
            } else {
                deptList = list;
            }
            return nodeConvent(deptList, "0", false);
        }
        return treeNodeList;
    }

    /**
     * 获取组织架构-树（根据部门级别）
     *
     * @param deptType 部门级别（1，2····）
     * @param user     当前用户
     * @return
     */
    @Override
    public List<TreeNode> selectListDeptSelfTree(String deptType, DefaultSysUser user, Boolean optional) {
        List<SysDept> list = new ArrayList<>();
        String startId = "";
        //默认查询子节点
        SysDept dept = this.deptMapper.selectById(user.getDeptId());
        //如果是检测点
        if (Objects.nonNull(dept) && dept.getDeptType() == 99) {
            list = this.deptMapper.selectDeptListByDetection(dept.getDeptId().toString());
            list.add(dept);
            list.forEach(d -> {
                if (d.getDeptType() == 99) {
                    d.setParentId(0L);
                } else {
                    d.setParentId(dept.getDeptId());
                }
            });
            startId = dept.getParentId().toString();
        } else {
            List<SysDept> listLower = getDeptChildrenByParentId(user.getDeptId(), null);
            if (StringUtils.isNotBlank(deptType)) {
                String[] strType = StringUtils.split(deptType, ",");
                for (String type : strType) {
                    for (SysDept lower : listLower) {
                        if (lower.getDeptType() == Integer.parseInt(type)) {
                            list.add(lower);
                        }
                    }
                }
            } else {
                list = listLower;
            }
            List<SysDept> lists = list.stream().filter(d -> (d.getDeptId() == user.getDeptId().intValue())).collect(Collectors.toList());
            startId = lists.get(0).getParentId().toString();
        }
        return nodeConvent(list, startId, optional);
    }

    /**
     * 根据编号获取部门信息
     *
     * @param deptId 部门ID
     * @return
     */
    @Override
    public SysDept getDeptById(Long deptId) {
        SysDept dept = new SysDept();
        dept.setDeptId(deptId);
        dept.setEnabled(1);
        return this.deptMapper.selectOne(dept);
    }

    /**
     * 刷新Redis部门数据
     *
     * @param user 当前用户
     */
    @Override
    public void refreshRedisDept(DefaultSysUser user) {
        Map<String, Object> deptMap = new HashMap<>(5);
        // 设置字典 未考虑检测点
        for (Integer i = 0; i <= 4; i++) {
            //根据类型查找当前用户下的部门所属基层所、或者市场....
            deptMap.put(i.toString(), this.getDeptChildrenByParentId(user.getDeptId(), i));
        }
        deptMap.put("99", this.getDeptChildrenByParentId(user.getDeptId(), 99));
        redisRepository.setExpire("dept_" + user.getUsername(), JSON.toJSONString(deptMap), ConfigConsts.EXPIRE_TIME);
    }

    /**
     * 存储部门
     *
     * @param dept 部门
     * @return
     */
    @Override
    public void saveDept(SysDept dept, DefaultSysUser sysUser) {
        this.insert(dept);
        this.redisRepository.del(CacheConsts.REDIS_DEPT + sysUser.getDeptId());
    }

    /**
     * 更新部门
     *
     * @param dept 部门
     * @return
     */
    @Override
    public void updateDeptById(SysDept dept) {
        this.deptMapper.updateById(dept);
    }

    /**
     * 根据部门ID禁用部门
     *
     * @param deptId 部门ID
     * @return
     */
    @Override
    public Integer batchDeleteDeptById(Long[] deptId) {
        SysDept dept = new SysDept();
        dept.setEnabled(0);
        int row = 0;
        for (Long id : deptId) {
            dept.setDeptId(id);
            row += this.deptMapper.updateById(dept);
        }
        return row;
    }

    /**
     * 根据编号删除部门
     *
     * @param deptId 部门ID
     * @return
     */
    @Override
    public Integer deleteDeptById(Long deptId) {
        return this.deptMapper.deleteById(deptId);
    }

    /**
     * 根据部门ID，获取当前部门所属辖区
     *
     * @param deptId 部门ID
     * @return
     */
    @Override
    public String getJurisdiction(Long deptId) {
        SysDept dept = this.deptMapper.selectById(deptId);
        List<SysDept> deptList = this.deptMapper.selectJurisdiction(deptId);
        String jurisdiction = "无";
        if (Objects.nonNull(dept)) {
            Integer deptType = dept.getDeptType();
            List<SysDept> list;
            if (deptType == 1) {
                jurisdiction = dept.getDeptName();
            } else if (deptType == 2) {
                list = deptList.stream().filter(d -> d.getDeptType() == 1).collect(Collectors.toList());
                jurisdiction = list.size() == 0 ? "无" : list.get(0).getDeptName();
            } else if (deptType == 3 || deptType == 4 || deptType == 5) {
                list = deptList.stream().filter(d -> d.getDeptType() == 2).collect(Collectors.toList());
                jurisdiction = list.size() == 0 ? "无" : list.get(0).getDeptName();
            }
        }
        return jurisdiction;
    }

    /**
     * 根据部门ID查询所有父级节点
     *
     * @param deptId   部门ID
     * @param deptType 部门类型
     * @return
     */
    @Override
    public List<DeptVO> selectDeptParentList(Long deptId, Integer deptType) {
        return this.deptMapper.selectDeptParentList(deptId, deptType);
    }

    /**
     * 根据部门编号查询子节点部门序列
     *
     * @param deptId   部门编号
     * @param deptType 部门下子部门类别
     * @return
     */
    @Override
    public List<SysDept> getDeptChildrenByParentId(Integer deptId, Integer deptType) {
        String deptIdPath = deptMapper.selectDeptChildrenByRootId(deptId.toString());
        EntityWrapper<SysDept> entityWrapper = new EntityWrapper<>();
        entityWrapper.setEntity(new SysDept());
        entityWrapper.in("dept_id", deptIdPath);
        entityWrapper.eq("enabled", true);
        if (null != deptType) {
            entityWrapper.eq("dept_type", deptType.toString());
        }
        return deptMapper.selectList(entityWrapper);
    }

    /**
     * 根据检测点获取被检测机构
     *
     * @param detection 检测点编号
     * @return
     */
    @Override
    public List<SysDept> getDeptListByDetection(Long detection) {
        return deptMapper.selectDeptListByDetection(detection.toString());
    }

    /**
     * 根据所属部门获取子市场
     *
     * @param deptId 科室编号编号
     * @return
     */
    @Override
    public List<SysDept> getMarketListByDept(String deptId) {

        // 获取自己的部门
        SysDept dept = new SysDept();
        dept.setDeptId(Long.parseLong(deptId));
        dept = deptMapper.selectOne(dept);

        List<SysDept> deptList;

        // 构造获取子部门的检索条件
        EntityWrapper<SysDept> deptEntityWrapper = new EntityWrapper<>();
        deptEntityWrapper.setEntity(new SysDept());
        // 是检测点
        if (NUM.equals(dept.getDeptType().toString())) {
            // 是检测点就根据检测点编号查询
            deptList = this.getDeptListByDetection(dept.getDeptId());
        } else {
            // 不是检测点，那么就要查询他的子节点
            deptList = this.getDeptChildrenByParentId(dept.getDeptId().intValue(), 4);
        }
        return deptList;
    }

    /**
     * 根据所属部门获取检测点清单
     *
     * @param deptId
     * @return
     */
    @Override
    public List<SysDept> getDetectionListByDept(String deptId) {
        // 获取自己的部门
        SysDept dept = new SysDept();
        dept.setDeptId(Long.parseLong(deptId));
        dept = deptMapper.selectOne(dept);

        List<SysDept> deptList = new ArrayList<>();

        // 构造获取子部门的检索条件
        EntityWrapper<SysDept> deptEntityWrapper = new EntityWrapper<>();
        deptEntityWrapper.setEntity(new SysDept());
        // 是检测点
        if (NUM.equals(dept.getDeptType().toString())) {
            // 是检测点就根据检测点编号查询
            deptList.add(dept);
        } else {
            // 不是检测点，那么就要查询他的子节点
            deptList = this.getDeptChildrenByParentId(dept.getDeptId().intValue(), 99);
        }
        return deptList;
    }

    /**
     * 获取所有市场的地理坐标
     *
     * @param deptIds 部门ids
     * @return
     */
    @Override
    public List<DeptLocationVO> selectListMarketLocation(String deptIds) {
        return deptMapper.selectListMarketLocation(deptIds);
    }

    /**
     * 根据部门名称获取部门信息
     *
     * @param deptName 部门名称
     * @return
     */
    @Override
    public SysDept selectDeptByDeptName(String deptName) {
        SysDept dept = new SysDept();
        dept.setDeptName(deptName);
        return this.deptMapper.selectOne(dept);
    }

    /**
     * 获取时间范围内市场的检验量
     *
     * @param deptId    市场编号
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    @Override
    public List<DeptSampleCountVO> selectListMarketSampleCount(String deptId, String startDate, String endDate) {
        return deptMapper.selectListMarketSampleCount(deptId, startDate, endDate);
    }

    /**
     * 查询所有部门的评分
     *
     * @param deptId   部门ID
     * @param deptType 部门类型
     * @return
     */
    @Override
    public List<DeptRankVO> selectListMarketRank(String deptId, Integer deptType) {
        String idString = this.deptMapper.selectDeptChildrenByRootId(deptId);
        String[] childDeptIds = idString.split(",");
        return deptMapper.selectListMarketRank(childDeptIds, deptType);
    }

    /**
     * 根据 父节点id 和 查询目标部门类型 查出父节点下所有的指定类型的子节点,并查出子节点到父节点的级数和路径的每个节点
     *
     * @param deptId   部门ID
     * @param deptType 部门类型
     * @return
     */
    @Override
    public List<DeptNodeVO> selectListDeptChildrenByType(Long deptId, Integer deptType) {
        return deptMapper.selectListDeptChildrenByType(deptId, deptType);
    }

    /**
     * 获取所有市场id
     *
     * @return
     */
    @Override
    public List<Long> getMarketId() {
        return deptMapper.selectMarketId();
    }

    /**
     * 根据检测科室存储市场清单
     *
     * @param detectionId 检测单位
     * @param deptList    部门集合
     * @return
     */
    @Override
    public int saveByDetectionId(Long detectionId, List<String> deptList) {
        return 0;
    }

    /**
     * 根据类型查询部门列表
     *
     * @param deptType 部门类型
     * @return
     */
    @Override
    public List<SysDept> selectDeptByDeptType(Integer deptType) {
        SysDept deptTemp = new SysDept();
        Optional.ofNullable(deptType).ifPresent(type -> deptTemp.setDeptType(deptType));
        EntityWrapper<SysDept> entityWrapper = new EntityWrapper<>();
        entityWrapper.setEntity(deptTemp);
        return this.deptMapper.selectList(entityWrapper);
    }

    @Override
    public SysDept getTopDept() {
        SysDept sysDept = new SysDept();
        sysDept.setParentId(0L);
        return deptMapper.selectOne(sysDept);
    }

    /**
     * 添加部门到redis
     */
    @Override
    public void saveRedisByDept() {
        List<SysDept> deptList = this.deptMapper.selectList(new EntityWrapper<SysDept>().eq("enabled", true));
        if (ObjectUtils.isEmpty(deptList)) {
            deptList.forEach(d -> {
                final Map<String, Object> deptMap = new HashMap<>();
                List<SysDept> childList = this.getDeptChildrenByParentId(d.getDeptId().intValue(), null);
                List<SysDict> sysDictList = sysDictService.listSysDictByTypeOrAll("dept_type");
                for (SysDict sysDict : sysDictList) {
                    //根据类型查找当前用户下的部门所属基层所、或者市场....
                    deptMap.put(sysDict.getValue(), childList.stream().filter(t -> t.getDeptType().toString().equals(sysDict.getValue())).collect(Collectors.toList()));
                }
                redisRepository.setExpire(CacheConsts.REDIS_DEPT + d.getDeptId(), JSON.toJSONString(deptMap), ConfigConsts.EXPIRE_TIME);
            });
        }
    }

    /**
     * 获取部门下一级的所有部门
     *
     * @param deptId
     * @return
     */
    @Override
    public List<SysDept> listNextLevelDept(Integer deptId) {
        EntityWrapper<SysDept> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("parent_id", deptId);
        return deptMapper.selectList(entityWrapper);
    }


}
