package com.simple.poll.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("加入候選人")
public class CandidateCreateModel {
    @ApiModelProperty("選舉ID")
    @NotNull
    private long electionId;
    
    @ApiModelProperty("候選人帳號ID")
    @NotNull
    private long userId;

    public long getElectionId() {
        return electionId;
    }

    public void setElectionId(long electionId) {
        this.electionId = electionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    
}
