package com.jcms.domain;

public class DeptTreeEntity {
    private long id;
    private long pId;
    private String name;
    private boolean open;
    private String deptRemark;
    private Integer deptEnable;
    private String code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getDeptRemark() {
        return deptRemark;
    }

    public void setDeptRemark(String deptRemark) {
        this.deptRemark = deptRemark;
    }

    public Integer getDeptEnable() {
        return deptEnable;
    }

    public void setDeptEnable(Integer deptEnable) {
        this.deptEnable = deptEnable;
    }
}
