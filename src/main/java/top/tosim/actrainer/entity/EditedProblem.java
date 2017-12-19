package top.tosim.actrainer.entity;

import lombok.Data;

@Data
public class EditedProblem {
    private Integer id;             //本地数据库编号
    private String remoteOj;        //原始OJ
    private String remoteProblemId; //原始OJ题号
    private String url;             //题面原始url

    private String title;           //标题
    private String timeLimit;       //时间限制(ms)
    private String memoryLimit;     //内存限制(KB)
    private String description;     //题面描述
    private String input;           //输入介绍
    private String output;          //输出介绍
    private String sampleInput;     //样例输入
    private String sampleOutput;    //样例输出
    private String hint;            //提示
    private String source;          //出处
    private String author;          //作者
    private Integer isEdited;       //在比赛中的呈现是否修改过

}