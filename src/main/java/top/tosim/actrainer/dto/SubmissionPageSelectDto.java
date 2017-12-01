package top.tosim.actrainer.dto;

public class SubmissionPageSelectDto extends PageSelectDto{
    private Integer fromId;
    private String remoteOj;
    private String remoteProblemId;
    private String accountName;
    private String language;
    private String status;
    private Integer contestId;

    @Override
    public void validateAndCalculateStart(Integer defaultSize) {
        super.validateAndCalculateStart(defaultSize);
        if(this.fromId == null || this.fromId.equals(-1)) this.fromId = null;
        if(this.remoteOj == null || this.remoteOj.equals("")) this.remoteOj = null;
        if(this.remoteProblemId == null || this.remoteProblemId.equals("")) this.remoteProblemId = null;
        if(this.accountName == null || this.accountName.equals("")) this.accountName = null;
        if(this.language == null || this.language.equals("")) this.language = null;
        if(this.status == null || this.status.equals("")) this.status = null;
        if(this.status == null || this.contestId.equals(-1)) this.contestId = null;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public String getRemoteOj() {
        return remoteOj;
    }

    public void setRemoteOj(String remoteOj) {
        this.remoteOj = remoteOj;
    }

    public String getRemoteProblemId() {
        return remoteProblemId;
    }

    public void setRemoteProblemId(String remoteProblemId) {
        this.remoteProblemId = remoteProblemId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
