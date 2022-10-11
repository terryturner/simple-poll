package com.simple.poll.model.output;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("API共用輸出格式")
public final class ApiResponseBody<T> {
    @ApiModelProperty("Request URL")
	private String url;
    @ApiModelProperty("成功回傳1")
	private int status = 1;
    @ApiModelProperty("單一錯誤資訊")
	private ErrorObj error;
    @ApiModelProperty("成功回傳資訊")
	private T resultBody = null;
    @ApiModelProperty("多重錯誤資訊")
	private List<ErrorObj> errorList;

	public ApiResponseBody() { }

	public ApiResponseBody(T resultBody) {
		this.resultBody = resultBody; }

	public int getStatus() {
		return status;
	}

	public ApiResponseBody<T> setStatus(int status) {
		this.status = status;
		return this;
	}

	public T getResultBody() {
		return resultBody;
	}

	public ApiResponseBody<T> setResultBody(T resultBody) {
		this.resultBody = resultBody;
		return this;
	}

	public ErrorObj getError() {
		return error;
	}

	public void setError(ErrorObj error) {
		this.error = error;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ErrorObj> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ErrorObj> errors) {
		this.errorList = errors;
	}
   
	
}
