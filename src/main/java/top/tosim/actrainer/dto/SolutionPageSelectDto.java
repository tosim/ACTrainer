package top.tosim.actrainer.dto;

public class SolutionPageSelectDto extends PageSelectDto{
    private String remoteOj;
    private String remoteProblemId;
    private Integer userId;

    @Override
    public void validateAndCalculateStart(Integer defaultSize) {
        super.validateAndCalculateStart(defaultSize);
        if(this.remoteOj == null || this.remoteOj.equals("")) this.remoteOj = null;
        if(this.remoteProblemId == null || this.remoteProblemId.equals("")) this.remoteProblemId = null;
        if(this.userId == null || this.userId.equals(-1)) this.userId = null;
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
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
