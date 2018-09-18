package com.jcms.domain;

import javax.persistence.*;

@Entity
@Table(name = "sys_role_authorities", schema = "jcms", catalog = "")
public class SysRoleAuthoritiesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long raId;
    private Long roleId;
    private Long authId;


    @Column(name = "raId")
    public long getRaId() {
        return raId;
    }


    public void setRaId(long raId) {
        this.raId = raId;
    }

    @Basic
    @Column(name = "roleId")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "authId")
    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysRoleAuthoritiesEntity that = (SysRoleAuthoritiesEntity) o;

        if (raId != that.raId) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (authId != null ? !authId.equals(that.authId) : that.authId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (raId ^ (raId >>> 32));
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (authId != null ? authId.hashCode() : 0);
        return result;
    }
}
