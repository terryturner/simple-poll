package com.simple.poll.model.output;

import java.util.List;

import com.simple.poll.database.entity.ElectionState;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("候選人詳細票數資訊")
public class CandidateGetNumberOfVotes {
    @ApiModelProperty("候選人ID")
    private Long id;
    @ApiModelProperty("候選人名稱")
    private String candidateName;
    @ApiModelProperty("選舉名稱")
    private String electionName;
    @ApiModelProperty("選舉目前狀態")
    private ElectionState electionState;
    @ApiModelProperty("總得票數")
    private long totalNumberOfVotes;
    @ApiModelProperty("顯示分頁的編號")
    private int pageIndex;
    @ApiModelProperty("顯示分頁的大小")
    private int pageSize;
    @ApiModelProperty("投票人的帳號名稱")
    private List<String> voters;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCandidateName() {
        return candidateName;
    }
    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
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
    public long getTotalNumberOfVotes() {
        return totalNumberOfVotes;
    }
    public void setTotalNumberOfVotes(long totalNumberOfVotes) {
        this.totalNumberOfVotes = totalNumberOfVotes;
    }
    public int getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public List<String> getVoters() {
        return voters;
    }
    public void setVoters(List<String> voters) {
        this.voters = voters;
    }
}
