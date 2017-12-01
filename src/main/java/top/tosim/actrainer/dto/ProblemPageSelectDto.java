package top.tosim.actrainer.dto;

public class ProblemPageSelectDto extends PageSelectDto{
    private String remoteOj;
    private String remoteProblemId;
    private String title;

    @Override
    public void validateAndCalculateStart(Integer defaultSize) {
        super.validateAndCalculateStart(defaultSize);
        if(this.remoteOj.equals("")) this.remoteOj = null;
        if(this.remoteProblemId.equals("")) this.remoteProblemId = null;
        if(this.title.equals("")) this.title = null;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
