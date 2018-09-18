package com.jcms.domain;


import java.io.Serializable;

public class BaseEntity implements Serializable {
    private Integer page;
    private Integer pageSize;
    private Integer keyType;
    private String key;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getKeyType() {
        return keyType;
    }

    public void setKeyType(Integer keyType) {
        this.keyType = keyType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
