package com.simple.poll.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("建立一個選舉資訊")
public class ElectionCreateModel {
    @ApiModelProperty("選舉名稱，唯一值")
    @NotNull
    @Size(min = 1, max = 50)
    private String electionName;

    public String getElectionName() {
        return electionName;
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

}
