package com.party.web.biz.charge;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.party.core.model.charge.PackageMember;
import com.party.core.model.charge.PackagePrivilege;
import com.party.core.model.charge.ProductPackage;
import com.party.core.model.charge.ProductPrivilege;
import com.party.core.model.system.SysRole;
import com.party.core.service.charge.IPackageMemberService;
import com.party.core.service.charge.IPackagePrivilegeService;
import com.party.core.service.charge.IPackageService;
import com.party.core.service.charge.IPrivilegeService;
import com.party.core.service.system.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 套餐业务
 *
 * @author Administrator
 * @date 2017/7/27 0027 14:50
 */
@Service
public class PackageBizService {
    @Autowired
    private IPackageMemberService packageMemberService;
    @Autowired
    private IPackageService packageService;
    @Autowired
    private IPrivilegeService privilegeService;
    @Autowired
    private IPackagePrivilegeService packagePrivilegeService;
    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 获取用户所购买的最大套餐角色
     *
     * @param memberId
     * @param sysRoles
     * @return
     */
    public PackageMember getPackageMember(String memberId, List<SysRole> sysRoles) {
        if (sysRoles == null) {
            sysRoles = Lists.newArrayList();
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("type", "1");
        params.put("memberId", memberId);
        sysRoles = sysRoleService.getRoleByMemberId(params);
        PackageMember packageMember = packageMemberService.findByMemberId(new PackageMember("", memberId));
        if (packageMember == null) {
            ProductPackage t = new ProductPackage();
            t.setType(1);
            List<ProductPackage> packages = packageService.list(t);
            List<ProductPackage> newPackges = new ArrayList<ProductPackage>();
            for (ProductPackage p : packages) {
                boolean flag = false;
                for (SysRole r : sysRoles) {
                    if (p.getSysRoleId().equals(r.getId())) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    newPackges.add(p);
                }
            }
            if (newPackges.size() > 0) {
                Collections.sort(newPackges, new Comparator<ProductPackage>() {
                    @Override
                    public int compare(ProductPackage o1, ProductPackage o2) {
                        return o1.getLevel() > o2.getLevel() ? -1 : 1;
                    }
                });

                packageMember = new PackageMember();
                packageMember.setLevelId(newPackges.get(0).getId());
                packageMember.setSysRoleId(newPackges.get(0).getSysRoleId());
            }
        }
        return packageMember;
    }

    /**
     * 获取所有权限对应的套餐值
     *
     * @param productPackages
     * @return
     */
    public List<Map<String, Object>> getPackagePrivilegeList(List<ProductPackage> productPackages) {
        List<ProductPrivilege> privileges = privilegeService.list(new ProductPrivilege());

        List<Map<String, Object>> newPrivileges = Lists.newArrayList();
        for (ProductPrivilege privilege : privileges) {
            List<Map<String, Object>> packagePrivilege = Lists.newArrayList();
            for (ProductPackage aPackage : productPackages) {
                PackagePrivilege pp = packagePrivilegeService.getUnique(new PackagePrivilege(aPackage.getId(), privilege.getId()));
                Map<String, Object> map = Maps.newHashMap();
                if (pp != null) {
                    map.put("packageId", pp.getPackageId());
                    map.put("paramValue", pp.getParamValue());
                }
                packagePrivilege.add(map);
            }
            Map<String, Object> map = Maps.newHashMap();
            map.put("privilege", privilege);
            map.put("results", packagePrivilege);
            newPrivileges.add(map);
        }
        return newPrivileges;
    }

    /**
     * 获取套餐对应的权限
     * @param packageId 套餐id
     * @param code 权限code
     * @return
     */
    public PackagePrivilege getPackagePrivilege(String packageId, String code) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("packageId", packageId);
        params.put("code", code);
        PackagePrivilege packagePrivilege = packagePrivilegeService.findByPackage(params);
        return packagePrivilege;
    }
}
