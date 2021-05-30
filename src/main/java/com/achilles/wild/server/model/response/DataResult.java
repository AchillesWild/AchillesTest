package com.achilles.wild.server.model.response;

import com.achilles.wild.server.model.response.code.BaseResultCode;

public class DataResult<T> extends BaseResult {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DataResult() {
    }

    public static <T> DataResult<T> success(T data) {
        return success(new DataResult<>(), data);
    }

    public static <T> DataResult<T> success(DataResult<T> result, T data) {
        if (result == null) {
            return success(data);
        }
        result.setData(data);
        return result;
    }

    public static <T> DataResult<T> baseFail() {
        BaseResult baseResult = BaseResult.fail();
        DataResult dataResult = new DataResult<>();
        dataResult.setSuccess(false);
        dataResult.setCode(baseResult.getCode());
        dataResult.setMessage(baseResult.getMessage());
        return dataResult;
    }

    public static <T> DataResult<T> baseFail(BaseResultCode baseResultCode) {
        DataResult dataResult = new DataResult<>();
        dataResult.setSuccess(false);
        dataResult.setCode(baseResultCode.code);
        dataResult.setMessage(baseResultCode.message);
        return dataResult;
    }

    public static <T> DataResult<T> baseFail(BaseResultCode baseResultCode, T data) {
        DataResult dataResult = DataResult.baseFail(baseResultCode);
        dataResult.setData(data);
        return dataResult;
    }

    public static <T> DataResult<T> baseFail(String code,String msg) {
        DataResult dataResult = new DataResult<>();
        dataResult.setSuccess(false);
        dataResult.setCode(code);
        dataResult.setMessage(msg);
        return dataResult;
    }

}