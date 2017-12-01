package top.tosim.actrainer.remote;

import top.tosim.actrainer.entity.Submission;

public interface Submitter {
    public Integer submitCode(Submission submission);
}
