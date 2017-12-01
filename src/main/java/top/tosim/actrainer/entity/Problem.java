package top.tosim.actrainer.entity;

public class Problem {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(String memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getSampleInput() {
        return sampleInput;
    }

    public void setSampleInput(String sampleInput) {
        this.sampleInput = sampleInput;
    }

    public String getSampleOutput() {
        return sampleOutput;
    }

    public void setSampleOutput(String sampleOutput) {
        this.sampleOutput = sampleOutput;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
