package com.simple.poll.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("登錄帳號資訊")
public class GuestSigninOutput {
    @ApiModelProperty("後續請帶入Bearer Token")
    private String tokenId;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
