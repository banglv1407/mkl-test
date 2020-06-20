package com.mkl.mkltest.entity;

import java.util.List;

public class RoleInfo {
    private String id;
    private String name;
    private List<String> listPermission;
    
    public RoleInfo() {
        super();
    }

    public RoleInfo(Role role) {
        super();
        this.id = role.getId();
        this.name = role.getName();
        this.listPermission = role.getPermissions();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getListPermission() {
        return listPermission;
    }

    public void setListPermission(List<String> listPermission) {
        this.listPermission = listPermission;
    }
    
}
