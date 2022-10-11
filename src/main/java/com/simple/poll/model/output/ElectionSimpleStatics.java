package com.simple.poll.model.output;

import java.io.Serializable;
import java.util.List;

import com.simple.poll.database.entity.ElectionState;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("選舉簡易統計資訊")
public class ElectionSimpleStatics implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("選舉ID")
    private Long electionId;
    @ApiModelProperty("選舉名稱")
    private String electionName;
    @ApiModelProperty("選舉目前狀態")
    private ElectionState electionState;
    @ApiModelProperty("所有候選人的簡易統計資訊")
    private List<CandidateSimpleStatics> statics;
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
    public List<CandidateSimpleStatics> getStatics() {
        return statics;
    }
    public void setStatics(List<CandidateSimpleStatics> statics) {
        this.statics = statics;
    }
}
