package com.jcms.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sys_authorities", schema = "jcms", catalog = "")
public class SysAuthoritiesEntity {
    private long authId;
    @NotBlank(message = "权限编码不能为空")
    private String authCode;
    @Length(min = 2,max = 32,message = "权限名称必须在2-32位之间")
    private String authName;
    private Long authPid;
    @Length(min = 2,max = 512,message = "备注不能超过512个字符")
    private String authRemark;
    private Integer authPriority;
    private Integer authIsOption;
    private String authIcon;
    @Length(max = 512,message = "权限URL不允许超过512个字符")
    private String authUrl;

    @Id
    @Column(name = "authId")
    public long getAuthId() {
        return authId;
    }

    public void setAuthId(long authId) {
        this.authId = authId;
    }

    @Basic
    @Column(name = "authCode")
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Basic
    @Column(name = "authName")
    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    @Basic
    @Column(name = "authPid")
    public Long getAuthPid() {
        return authPid;
    }

    public void setAuthPid(Long authPid) {
        this.authPid = authPid;
    }

    @Basic
    @Column(name = "authRemark")
    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark;
    }

    @Basic
    @Column(name = "authPriority")
    public Integer getAuthPriority() {
        return authPriority;
    }

    public void setAuthPriority(Integer authPriority) {
        this.authPriority = authPriority;
    }

    @Basic
    @Column(name = "authIsOption")
    public Integer getAuthIsOption() {
        return authIsOption;
    }

    public void setAuthIsOption(Integer authIsOption) {
        this.authIsOption = authIsOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysAuthoritiesEntity that = (SysAuthoritiesEntity) o;

        if (authId != that.authId) return false;
        if (authName != null ? !authName.equals(that.authName) : that.authName != null) return false;
        if (authPid != null ? !authPid.equals(that.authPid) : that.authPid != null) return false;
        if (authRemark != null ? !authRemark.equals(that.authRemark) : that.authRemark != null) return false;
        if (authPriority != null ? !authPriority.equals(that.authPriority) : that.authPriority != null) return false;
        if (authIsOption != null ? !authIsOption.equals(that.authIsOption) : that.authIsOption != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (authId ^ (authId >>> 32));
        result = 31 * result + (authName != null ? authName.hashCode() : 0);
        result = 31 * result + (authPid != null ? authPid.hashCode() : 0);
        result = 31 * result + (authRemark != null ? authRemark.hashCode() : 0);
        result = 31 * result + (authPriority != null ? authPriority.hashCode() : 0);
        result = 31 * result + (authIsOption != null ? authIsOption.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "authIcon")
    public String getAuthIcon() {
        return authIcon;
    }

    public void setAuthIcon(String authIcon) {
        this.authIcon = authIcon;
    }

    @Basic
    @Column(name = "authUrl")
    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }
}
