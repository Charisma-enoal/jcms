package com.jcms.domain;

import com.jcms.annotation.IsNeeded;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "sys_user", schema = "jcms", catalog = "")
@EntityListeners(AuditingEntityListener.class)
public class SysUserEntity extends BaseEntity {
    private long userId;
    @Length(min = 3, max = 12, message = "用户名必须在3-12位之间")
    @IsNeeded
    private String userName;
    @NotBlank(message = "真实姓名不能为空")
    @Length(min = 2,max = 12,message = "真实姓名必须在3-12位之间")
    @IsNeeded
    private String userRealName;
    private String userPassowrd;

    @NotNull(message = "所属部门不能为空")
    private Long userDept;
    private Integer userEnable = 1;
    @CreatedDate
    @IsNeeded
    private Date userCreateDate;
    @CreatedBy
    @IsNeeded
    private String userCreatePerson;
    @LastModifiedDate
    @IsNeeded
    private Date userLastEditDate;
    @LastModifiedBy
    @IsNeeded
    private String userLastEditPerson;

    //扩展字段，用于存储所属部门
    @IsNeeded
    private SysDeptEntity sysDeptEntity;

    @Transient
    private String userDateRangeBegin;
    @Transient
    private String userDateRangeEnd;
    private String userDeptName;

    @Id
    @Column(name = "userId")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @OneToOne
    @JoinColumn(name = "userDept", insertable = false, updatable = false, nullable = true)
    public SysDeptEntity getSysDeptEntity() {
        return sysDeptEntity;
    }

    public void setSysDeptEntity(SysDeptEntity sysDeptEntity) {
        this.sysDeptEntity = sysDeptEntity;
    }

    @Basic
    @Column(name = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "userRealName")
    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    @Basic
    @Column(name = "userPassowrd")
    public String getUserPassowrd() {
        return userPassowrd;
    }

    public void setUserPassowrd(String userPassowrd) {
        this.userPassowrd = userPassowrd;
    }

    @Basic
    @Column(name = "userDept")
    public Long getUserDept() {
        return userDept;
    }

    public void setUserDept(Long userDept) {
        this.userDept = userDept;
    }

    @Basic
    @Column(name = "userEnable")
    public Integer getUserEnable() {
        return userEnable;
    }

    public void setUserEnable(Integer userEnable) {
        this.userEnable = userEnable;
    }

    @Basic
    @Column(name = "userCreateDate")
    public Date getUserCreateDate() {
        return userCreateDate;
    }

    public void setUserCreateDate(Date userCreateDate) {
        this.userCreateDate = userCreateDate;
    }

    @Basic
    @Column(name = "userCreatePerson")
    public String getUserCreatePerson() {
        return userCreatePerson;
    }

    public void setUserCreatePerson(String userCreatePerson) {
        this.userCreatePerson = userCreatePerson;
    }

    @Basic
    @Column(name = "userLastEditDate")
    public Date getUserLastEditDate() {
        return userLastEditDate;
    }

    public void setUserLastEditDate(Date userLastEditDate) {
        this.userLastEditDate = userLastEditDate;
    }

    @Basic
    @Column(name = "userLastEditPerson")
    public String getUserLastEditPerson() {
        return userLastEditPerson;
    }

    public void setUserLastEditPerson(String userLastEditPerson) {
        this.userLastEditPerson = userLastEditPerson;
    }

    @Transient
    public String getUserDateRangeBegin() {
        return userDateRangeBegin;
    }

    public void setUserDateRangeBegin(String userDateRangeBegin) {
        this.userDateRangeBegin = userDateRangeBegin;
    }

    @Transient
    public String getUserDateRangeEnd() {
        return userDateRangeEnd;
    }

    public void setUserDateRangeEnd(String userDateRangeEnd) {
        this.userDateRangeEnd = userDateRangeEnd;
    }

    @Transient
    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysUserEntity that = (SysUserEntity) o;

        if (userId != that.userId) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userRealName != null ? !userRealName.equals(that.userRealName) : that.userRealName != null) return false;
        if (userPassowrd != null ? !userPassowrd.equals(that.userPassowrd) : that.userPassowrd != null) return false;
        if (userDept != that.userDept) return false;
        if (userEnable != null ? !userEnable.equals(that.userEnable) : that.userEnable != null) return false;
        if (userCreateDate != null ? !userCreateDate.equals(that.userCreateDate) : that.userCreateDate != null)
            return false;
        if (userCreatePerson != null ? !userCreatePerson.equals(that.userCreatePerson) : that.userCreatePerson != null)
            return false;
        if (userLastEditDate != null ? !userLastEditDate.equals(that.userLastEditDate) : that.userLastEditDate != null)
            return false;
        if (userLastEditPerson != null ? !userLastEditPerson.equals(that.userLastEditPerson) : that.userLastEditPerson != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userRealName != null ? userRealName.hashCode() : 0);
        result = 31 * result + (userPassowrd != null ? userPassowrd.hashCode() : 0);
        result = (int) (userDept ^ (userDept >>> 32));
        result = 31 * result + (userEnable != null ? userEnable.hashCode() : 0);
        result = 31 * result + (userCreateDate != null ? userCreateDate.hashCode() : 0);
        result = 31 * result + (userCreatePerson != null ? userCreatePerson.hashCode() : 0);
        result = 31 * result + (userLastEditDate != null ? userLastEditDate.hashCode() : 0);
        result = 31 * result + (userLastEditPerson != null ? userLastEditPerson.hashCode() : 0);
        return result;
    }
}
