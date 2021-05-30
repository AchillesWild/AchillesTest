package com.achilles.wild.server.model.response;

import com.achilles.wild.server.model.response.code.BaseResultCode;

import java.io.Serializable;

public class BaseResult implements  Serializable {
	
	private static final long serialVersionUID = 2663890170416765007L;
	
	private String code;
	private boolean success;
	private String message;
	

	public BaseResult() {
		this.success = true;
		this.code = BaseResultCode.SUCCESS.code;
		this.message = BaseResultCode.SUCCESS.message;
	}

	public static BaseResult fail() {
		BaseResult result = new BaseResult();
		result.setSuccess(false);
		result.setCode(BaseResultCode.FAIL.code);
		result.setMessage(BaseResultCode.FAIL.message);
		return result;
	}

	public static BaseResult fail(BaseResultCode baseResultCode) {
		BaseResult result = new BaseResult();
		result.setSuccess(false);
		result.setCode(baseResultCode.code);
		result.setMessage(baseResultCode.message);
		return result;
	}

	public static BaseResult fail(String code,String message) {
		BaseResult result = new BaseResult();
		result.setSuccess(false);
		result.setCode(code);
		result.setMessage(message);
		return result;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
