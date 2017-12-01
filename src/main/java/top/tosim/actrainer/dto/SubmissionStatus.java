package top.tosim.actrainer.dto;

import top.tosim.actrainer.remote.RemoteStatusType;

import java.util.Date;

public class SubmissionStatus {
    private RemoteStatusType statusType;

    private String rawStatus;

    private int executionTime;

    private int executionMemory;

    private String compilationErrorInfo;

    private int failCase = -1;

    private Date queryTime = new Date();

    public RemoteStatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(RemoteStatusType statusType) {
        this.statusType = statusType;
    }

    public String getRawStatus() {
        return rawStatus;
    }

    public void setRawStatus(String rawStatus) {
        this.rawStatus = rawStatus;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public int getExecutionMemory() {
        return executionMemory;
    }

    public void setExecutionMemory(int executionMemory) {
        this.executionMemory = executionMemory;
    }

    public String getCompilationErrorInfo() {
        return compilationErrorInfo;
    }

    public void setCompilationErrorInfo(String compilationErrorInfo) {
        this.compilationErrorInfo = compilationErrorInfo;
    }

    public int getFailCase() {
        return failCase;
    }

    public void setFailCase(int failCase) {
        this.failCase = failCase;
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }
}
