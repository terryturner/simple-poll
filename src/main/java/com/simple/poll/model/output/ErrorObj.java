package com.simple.poll.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Error共用輸出格式")
public class ErrorObj {
    @ApiModelProperty("錯誤代碼")
    private final Integer errorCode;
    @ApiModelProperty("錯誤訊息")
    private final String errorMsg;

    public ErrorObj(Integer errorCode, String errorMsg) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
