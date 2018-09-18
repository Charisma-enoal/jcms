package com.jcms.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "sys_role", schema = "jcms", catalog = "")
public class SysRoleEntity extends BaseEntity {
    private long roleId;
    @Length(min = 2,max = 32,message = "角色名称必须在2-32个字符之间")
    private String roleName;
    private Integer roleEnable;
    @Length(max = 256,message = "备注字符数量超出限制，不能超过256个字符")
    private String roleRemark;

    @Id
    @Column(name = "roleId")
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "roleName")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "roleEnable")
    public Integer getRoleEnable() {
        return roleEnable;
    }

    public void setRoleEnable(Integer roleEnable) {
        this.roleEnable = roleEnable;
    }

    @Basic
    @Column(name = "roleRemark")
    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysRoleEntity that = (SysRoleEntity) o;

        if (roleId != that.roleId) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;
        if (roleEnable != null ? !roleEnable.equals(that.roleEnable) : that.roleEnable != null) return false;
        if (roleRemark != null ? !roleRemark.equals(that.roleRemark) : that.roleRemark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (roleEnable != null ? roleEnable.hashCode() : 0);
        result = 31 * result + (roleRemark != null ? roleRemark.hashCode() : 0);
        return result;
    }
}
