package com.mkl.mkltest.entity;

import java.util.List;

public class Permission {
    private String resource;
    private List<String> operations;
    
    public Permission() {
        super();
    }
    
    public Permission(String resource, List<String> operations) {
        super();
        this.resource = resource;
        this.operations = operations;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public List<String> getOperations() {
        return operations;
    }

    public void setOperations(List<String> operations) {
        this.operations = operations;
    }
}
