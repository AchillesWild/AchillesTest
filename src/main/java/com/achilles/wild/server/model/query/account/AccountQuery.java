package com.achilles.wild.server.model.query.account;

import java.util.List;

import com.achilles.wild.server.model.query.BaseQuery;

public class AccountQuery extends BaseQuery {

    private Integer accountType;

    private List<Integer> accountTypes;

    private String userId;

    private Integer status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getAccountTypes() {
        return accountTypes;
    }

    public void setAccountTypes(List<Integer> accountTypes) {
        this.accountTypes = accountTypes;
    }
}
