package top.tosim.actrainer.entity;

public class ContestProblem {
    private Integer id;
    private Integer contestId;
    private String remoteOj;
    private Integer remoteProblemId;
    private Integer editedProblemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public String getRemoteOj() {
        return remoteOj;
    }

    public void setRemoteOj(String remoteOj) {
        this.remoteOj = remoteOj;
    }

    public Integer getRemoteProblemId() {
        return remoteProblemId;
    }

    public void setRemoteProblemId(Integer remoteProblemId) {
        this.remoteProblemId = remoteProblemId;
    }

    public Integer getEditedProblemId() {
        return editedProblemId;
    }

    public void setEditedProblemId(Integer editedProblemId) {
        this.editedProblemId = editedProblemId;
    }
}
