package top.tosim.actrainer.entity;

import java.util.Date;
import java.util.List;

public class Contest {
    private Integer id;

    private String title;

    private Long startTime;

    private Long duration;

    private Integer userId;

    private String status;

    private Integer contestType;

    private String password;

    private String accountName;

    private List<List<Object>> problemList;

    private List<Problem> containProblems;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public List<Problem> getContainProblems() {
        return containProblems;
    }

    public void setContainProblems(List<Problem> containProblems) {
        this.containProblems = containProblems;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<List<Object>> getProblemList() {
        return problemList;
    }

    public String getStatus() {
        if(new Date().getTime() > this.startTime + this.duration) return "Ended";
        if(new Date().getTime() < this.startTime) return "Pending";
        else return "Runing";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getContestType() {
        return contestType;
    }

    public void setContestType(Integer contestType) {
        this.contestType = contestType;
    }

    public void setProblemList(List<List<Object>> problemList) {
        this.problemList = problemList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}