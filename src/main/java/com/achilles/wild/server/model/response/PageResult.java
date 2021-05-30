package com.achilles.wild.server.model.response;

import com.achilles.wild.server.model.response.code.BaseResultCode;

@Deprecated
public class PageResult<T> extends BaseResult {

    private int count;

    private T data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageResult() {
    }

    public static <T> PageResult<T> success(T data) {
        return success(new PageResult<>(), data);
    }

    public static <T> PageResult<T> success(PageResult<T> result, T data) {
        if (result == null) {
            return success(data);
        }
        result.setData(data);
        return result;
    }

    public static <T> PageResult<T> success(T data, int count) {
        return success(new PageResult<>(), data, count);
    }

    public static <T> PageResult<T> success(PageResult<T> result, T data, int count) {
        if (result == null) {
            return success(data, count);
        }
        result.setData(data);
        result.setCount(count);
        return result;
    }

    public static <T> PageResult<T> baseFail() {
        BaseResult baseResult = BaseResult.fail();
        PageResult pageResult = new PageResult<>();
        pageResult.setSuccess(false);
        pageResult.setCode(baseResult.getCode());
        pageResult.setMessage(baseResult.getMessage());
        return pageResult;
    }

    public static <T> PageResult<T> baseFail(BaseResultCode baseResultCode) {
        PageResult pageResult = new PageResult<>();
        pageResult.setSuccess(false);
        pageResult.setCode(baseResultCode.code);
        pageResult.setMessage(baseResultCode.message);
        return pageResult;
    }

    public static <T> PageResult<T> baseFail(BaseResultCode baseResultCode, T data) {
        PageResult pageResult = PageResult.baseFail(baseResultCode);
        pageResult.setData(data);
        return pageResult;
    }

}