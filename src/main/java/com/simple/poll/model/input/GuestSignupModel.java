package com.simple.poll.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("帳號註冊資訊")
public class GuestSignupModel {
    @ApiModelProperty("帳號名稱")
    @NotNull
    @Size(min = 1, max = 50)
    private String userName;
    
    @ApiModelProperty("密碼")
    @NotNull
    @Size(min = 1, max = 20)
    private String password;
    
    @ApiModelProperty("電子郵件信箱")
    @NotNull
    @Size(min = 1, max = 50)
    @Pattern(regexp="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;
    
    @ApiModelProperty("香港身份證號碼")
    @NotNull
    @Size(min = 7, max = 7)
    @Pattern(regexp="^[A-Za-z]\\d{6}$")
    private String hkIdentify;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHkIdentify() {
        return hkIdentify;
    }

    public void setHkIdentify(String hkIdentify) {
        this.hkIdentify = hkIdentify;
    }
    
}
