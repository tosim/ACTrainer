package top.tosim.actrainer.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Solution {
    private Integer id;
    private String title;
    private String remoteOj;
    private Integer remoteProblemId;
    private Integer userId;
    private String content;
    private Date createTime;

}