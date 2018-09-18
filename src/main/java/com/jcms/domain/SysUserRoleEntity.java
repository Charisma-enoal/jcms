package com.jcms.domain;

import javax.persistence.*;

@Entity
@Table(name = "sys_user_role", schema = "jcms", catalog = "")
public class SysUserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long urId;
    private Long userId;
    private Long roleId;

    @Column(name = "urId")
    public long getUrId() {
        return urId;
    }

    public void setUrId(long urId) {
        this.urId = urId;
    }

    @Basic
    @Column(name = "userId")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "roleId")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysUserRoleEntity that = (SysUserRoleEntity) o;

        if (urId != that.urId) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (urId ^ (urId >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }
}
