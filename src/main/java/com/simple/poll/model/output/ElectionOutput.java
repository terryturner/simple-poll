package com.simple.poll.model.output;

import com.simple.poll.database.entity.ElectionState;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("選舉資訊")
public class ElectionOutput {
    @ApiModelProperty("選舉ID")
    private Long electionId;
    @ApiModelProperty("選舉名稱")
    private String electionName;
    @ApiModelProperty("選舉目前狀態")
    private ElectionState electionState;
    public Long getElectionId() {
        return electionId;
    }
    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }
    public String getElectionName() {
        return electionName;
    }
    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }
    public ElectionState getElectionState() {
        return electionState;
    }
    public void setElectionState(ElectionState electionState) {
        this.electionState = electionState;
    }
}
