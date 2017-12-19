package top.tosim.actrainer.entity;

import lombok.Data;

@Data
public class ContestProblem {
    private Integer id;
    private Integer contestId;
    private String remoteOj;
    private Integer remoteProblemId;
    private Integer editedProblemId;
    private Integer index;
}
