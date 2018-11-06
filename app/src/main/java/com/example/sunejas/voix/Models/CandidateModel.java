package com.example.sunejas.voix.Models;

/**
 * Created by Suneja's on 26-06-2018.
 */

public class CandidateModel {
    public String candidateName;
    public Integer votes;

    public CandidateModel(){}

    public CandidateModel(String candidateName, Integer votes) {
        this.candidateName = candidateName;
        this.votes = votes;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}

