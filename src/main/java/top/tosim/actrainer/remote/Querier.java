package top.tosim.actrainer.remote;

import top.tosim.actrainer.entity.Submission;
import top.tosim.actrainer.dto.SubmissionStatus;

public interface Querier {
    public SubmissionStatus queryResult(Submission submission);
}
