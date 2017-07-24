package com.party.admin.web.dto.output.system;

import com.party.core.model.system.SysPrivilege;
import org.springframework.beans.BeanUtils;

/**
 * Created by wei.li
 *
 * @date 2017/6/27 0027
 * @time 15:08
 */
public class PrivilegeOutput extends SysPrivilege{

    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public static PrivilegeOutput transform(SysPrivilege sysPrivilege){
        PrivilegeOutput privilegeOutput = new PrivilegeOutput();
        BeanUtils.copyProperties(sysPrivilege, privilegeOutput);
        return privilegeOutput;
    }
}
