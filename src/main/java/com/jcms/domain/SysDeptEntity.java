package com.jcms.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sys_dept", schema = "jcms", catalog = "")
public class SysDeptEntity {
    private long deptId;
    @NotBlank(message = "部门编码不能为空")
    private String deptCode;
    @NotBlank(message = "部门名称不能为空")
    private String deptName;
    private String deptRemark;
    @NotNull(message = "上级部门不能为空")
    private Long deptPid;
    @NotNull(message = "请选择部门状态")
    private Integer deptEnable;
    @CreatedBy
    private String deptCreatePerson;
    @CreatedDate
    private Timestamp deptCreateDate;
    @LastModifiedDate
    private Timestamp deptLastEditDate;
    @LastModifiedBy
    private String deptLastEditPerson;


    @Id
    @Column(name = "deptId")
    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    @Basic
    @Column(name = "deptCode")
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Basic
    @Column(name = "deptName")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Basic
    @Column(name = "deptRemark")
    public String getDeptRemark() {
        return deptRemark;
    }

    public void setDeptRemark(String deptRemark) {
        this.deptRemark = deptRemark;
    }

    @Basic
    @Column(name = "deptPid")
    public Long getDeptPid() {
        return deptPid;
    }

    public void setDeptPid(Long deptPid) {
        this.deptPid = deptPid;
    }

    @Basic
    @Column(name = "deptEnable")
    public Integer getDeptEnable() {
        return deptEnable;
    }

    public void setDeptEnable(Integer deptEnable) {
        this.deptEnable = deptEnable;
    }

    @Basic
    @Column(name = "deptCreatePerson")
    public String getDeptCreatePerson() {
        return deptCreatePerson;
    }

    public void setDeptCreatePerson(String deptCreatePerson) {
        this.deptCreatePerson = deptCreatePerson;
    }

    @Basic
    @Column(name = "deptCreateDate")
    public Timestamp getDeptCreateDate() {
        return deptCreateDate;
    }

    public void setDeptCreateDate(Timestamp deptCreateDate) {
        this.deptCreateDate = deptCreateDate;
    }

    @Basic
    @Column(name = "deptLastEditDate")
    public Timestamp getDeptLastEditDate() {
        return deptLastEditDate;
    }

    public void setDeptLastEditDate(Timestamp deptLastEditDate) {
        this.deptLastEditDate = deptLastEditDate;
    }

    @Basic
    @Column(name = "deptLastEditPerson")
    public String getDeptLastEditPerson() {
        return deptLastEditPerson;
    }

    public void setDeptLastEditPerson(String deptLastEditPerson) {
        this.deptLastEditPerson = deptLastEditPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysDeptEntity that = (SysDeptEntity) o;

        if (deptId != that.deptId) return false;
        if (deptCode != null ? !deptCode.equals(that.deptCode) : that.deptCode != null) return false;
        if (deptName != null ? !deptName.equals(that.deptName) : that.deptName != null) return false;
        if (deptRemark != null ? !deptRemark.equals(that.deptRemark) : that.deptRemark != null) return false;
        if (deptPid != null ? !deptPid.equals(that.deptPid) : that.deptPid != null) return false;
        if (deptEnable != null ? !deptEnable.equals(that.deptEnable) : that.deptEnable != null) return false;
        if (deptCreatePerson != null ? !deptCreatePerson.equals(that.deptCreatePerson) : that.deptCreatePerson != null)
            return false;
        if (deptCreateDate != null ? !deptCreateDate.equals(that.deptCreateDate) : that.deptCreateDate != null)
            return false;
        if (deptLastEditDate != null ? !deptLastEditDate.equals(that.deptLastEditDate) : that.deptLastEditDate != null)
            return false;
        if (deptLastEditPerson != null ? !deptLastEditPerson.equals(that.deptLastEditPerson) : that.deptLastEditPerson != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (deptId ^ (deptId >>> 32));
        result = 31 * result + (deptCode != null ? deptCode.hashCode() : 0);
        result = 31 * result + (deptName != null ? deptName.hashCode() : 0);
        result = 31 * result + (deptRemark != null ? deptRemark.hashCode() : 0);
        result = 31 * result + (deptPid != null ? deptPid.hashCode() : 0);
        result = 31 * result + (deptEnable != null ? deptEnable.hashCode() : 0);
        result = 31 * result + (deptCreatePerson != null ? deptCreatePerson.hashCode() : 0);
        result = 31 * result + (deptCreateDate != null ? deptCreateDate.hashCode() : 0);
        result = 31 * result + (deptLastEditDate != null ? deptLastEditDate.hashCode() : 0);
        result = 31 * result + (deptLastEditPerson != null ? deptLastEditPerson.hashCode() : 0);
        return result;
    }
}
