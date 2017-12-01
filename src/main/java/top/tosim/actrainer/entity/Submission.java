package top.tosim.actrainer.entity;

import top.tosim.actrainer.config.init.LanguageMapManager;
import top.tosim.actrainer.remote.RemoteOJ;

import java.util.Date;

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


//    private Integer failedSubmitCount;  //累计提交失败次数
//    private Integer failedQueryCount;   //累计查询结果失败次数
    private Integer open;                 //代码是否公开
    private String language;            //用于前端显示的语言
    private Integer contestId;          //组织的比赛Id

    private Integer userId;             //提交的用户的Id
    private String accountName;         //提交的用户名

    public void init() {
        this.status = "Pending";
        this.submitTime = new Date();
        this.languageCode = LanguageMapManager.getLanguageMap(RemoteOJ.valueOf(remoteOj)).get(this.remoteOj);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompilationErrorInfo() {
        return compilationErrorInfo;
    }

    public void setCompilationErrorInfo(String compilationErrorInfo) {
        this.compilationErrorInfo = compilationErrorInfo;
    }

    public Integer getRealRunId() {
        return realRunId;
    }

    public void setRealRunId(Integer realRunId) {
        this.realRunId = realRunId;
    }

    public Integer getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
    }

    public Integer getExecutionMemory() {
        return executionMemory;
    }

    public void setExecutionMemory(Integer executionMemory) {
        this.executionMemory = executionMemory;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getRemoteProblemId() {
        return remoteProblemId;
    }

    public void setRemoteProblemId(String remoteProblemId) {
        this.remoteProblemId = remoteProblemId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRemoteAccountName() {
        return remoteAccountName;
    }

    public void setRemoteAccountName(String remoteAccountName) {
        this.remoteAccountName = remoteAccountName;
    }

    public String getRemoteOj() {
        return remoteOj;
    }

    public void setRemoteOj(String remoteOj) {
        this.remoteOj = remoteOj;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }
}
