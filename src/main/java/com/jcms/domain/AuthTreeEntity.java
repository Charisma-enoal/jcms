package com.jcms.domain;

public class AuthTreeEntity {
    private long id;
    private long pId;
    private String name;
    private String code;
    private boolean open;
    private boolean checked;
    private String authRemark;
    private Integer authPriority;
    private Integer authIsOption;
    private String authIcon;
    private String authUrl;
    private long raId;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark;
    }

    public Integer getAuthPriority() {
        return authPriority;
    }

    public void setAuthPriority(Integer authPriority) {
        this.authPriority = authPriority;
    }

    public Integer getAuthIsOption() {
        return authIsOption;
    }

    public void setAuthIsOption(Integer authIsOption) {
        this.authIsOption = authIsOption;
    }

    public String getAuthIcon() {
        return authIcon;
    }

    public void setAuthIcon(String authIcon) {
        this.authIcon = authIcon;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public long getRaId() {
        return raId;
    }

    public void setRaId(long raId) {
        this.raId = raId;
    }
}
