package top.tosim.actrainer.entity;

import lombok.Data;
import top.tosim.actrainer.config.init.LanguageMapManager;
import top.tosim.actrainer.remote.RemoteOJ;
import top.tosim.actrainer.remote.RemoteStatusType;

import java.util.Date;

@Data
public class Submission {
    //用户刚刚提交需要
    private Integer id;                 //本地数据库编号
    private Date submitTime;            //提交时间

    //提交到远程所需信息
    private String remoteProblemId;     //远程真实题目Id
    private String languageCode;        //语言在远程的代码编号
    private String source;              //提交的源代码
    private String remoteAccountName;   //交题账号名
    private String remoteOj;            //交题OJ

    //提交之后所要查询的信息
    private String status;              //判题状态
    private Integer realRunId;          //远程oj真实运行id
    private String compilationErrorInfo;//编译错误信息
    private Integer executionTime;      //运行时间(未AC提交为空    单位:ms)
    private Integer executionMemory;    //运行内存(未AC提交为空    单位:KB)

    private Integer open;                 //代码是否公开
    private String language;            //用于前端显示的语言
    private Integer contestId;          //组织的比赛Id
    private Integer index;              //比赛中题目的index
    private Integer userId;             //提交的用户的Id
    private String accountName;         //提交的用户名

    public void init() {
        this.status = RemoteStatusType.PENDING.name();
        this.submitTime = new Date();
        this.languageCode = LanguageMapManager.getLanguageMap(RemoteOJ.valueOf(remoteOj)).get(this.remoteOj);
    }


}
