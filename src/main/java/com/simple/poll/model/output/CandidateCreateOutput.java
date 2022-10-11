package com.simple.poll.model.output;

import com.simple.poll.database.entity.ElectionState;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("加入候選人成功")
public class CandidateCreateOutput {
    @ApiModelProperty("候選人ID")
    private Long id;
    @ApiModelProperty("參加選舉名稱")
    private String electionName;
    @ApiModelProperty("參加選舉目前狀態")
    private ElectionState electionState;
    @ApiModelProperty("候選人名稱")
    private String candidateName;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getCandidateName() {
        return candidateName;
    }
    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
}
