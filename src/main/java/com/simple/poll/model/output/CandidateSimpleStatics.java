package com.simple.poll.model.output;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("候選人簡易統計資訊")
public class CandidateSimpleStatics implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("候選人ID")
    private Long candidateId;
    @ApiModelProperty("候選人名稱")
    private String candidateName;
    @ApiModelProperty("候選人得票數")
    private long numberOfVotes;
    public Long getCandidateId() {
        return candidateId;
    }
    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
    public String getCandidateName() {
        return candidateName;
    }
    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
    public long getNumberOfVotes() {
        return numberOfVotes;
    }
    public void setNumberOfVotes(long numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}
