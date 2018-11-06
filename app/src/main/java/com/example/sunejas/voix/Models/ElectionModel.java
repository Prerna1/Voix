package com.example.sunejas.voix.Models;

import java.util.List;

/**
 * Created by Suneja's on 26-06-2018.
 */

public class ElectionModel {
    public String hostName;
    public String elctionName;
    public String description;
    public String startDate;
    public String startTime;
    public String endDate;
    public String endTime;
    public String numberOfCandidates;
    public String numberOfWinners;
    public String electionId;
    //public List<CandidateModel> candidates;
    public ElectionModel(){}

    public ElectionModel(String hostName, String elctionName, String description, String startDate, String startTime, String endDate, String endTime, String numberOfCandidates, String numberOfWinners) {
        this.hostName = hostName;
        this.elctionName = elctionName;
        this.description = description;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.numberOfCandidates = numberOfCandidates;
        this.numberOfWinners = numberOfWinners;
    }
}
