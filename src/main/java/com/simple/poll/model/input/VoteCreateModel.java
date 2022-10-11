package com.simple.poll.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("投票")
public class VoteCreateModel {
    @ApiModelProperty("選舉ID")
    @NotNull
    private long electionId;
    
    @ApiModelProperty("候選人帳號ID")
    @NotNull
    private long candidateId;

    public long getElectionId() {
        return electionId;
    }

    public void setElectionId(long electionId) {
        this.electionId = electionId;
    }

    public long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(long candidateId) {
        this.candidateId = candidateId;
    }
}
