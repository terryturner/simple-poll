package com.simple.poll.database.entity;

public interface INumberOfVotes {
    Long getElectionId();
    Long getCandidateId();
    String getCandidateName();
    Long getNumberOfVotes();
}
