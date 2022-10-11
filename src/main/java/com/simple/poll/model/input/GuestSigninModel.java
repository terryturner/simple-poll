package com.simple.poll.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("帳號登錄資訊")
public class GuestSigninModel {
    @ApiModelProperty("帳號名稱")
    @NotNull
    @Size(min = 1, max = 50)
    private String userName;
    
    @ApiModelProperty("密碼")
    @NotNull
    @Size(min = 1, max = 20)
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
