package top.tosim.actrainer.remote.provider.hdu;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.tosim.actrainer.config.init.SubmissionManager;
import top.tosim.actrainer.dao.SubmissionDao;
import top.tosim.actrainer.dao.UserDao;
import top.tosim.actrainer.entity.Submission;
import top.tosim.actrainer.dto.SubmissionStatus;
import top.tosim.actrainer.httpclient.DedicatedHttpClient;
import top.tosim.actrainer.remote.Querier;
import top.tosim.actrainer.remote.RemoteOJ;
import top.tosim.actrainer.remote.RemoteStatusNormalizer;
import top.tosim.actrainer.remote.RemoteStatusType;
import top.tosim.actrainer.tool.Tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HDUQuerier implements Querier,Runnable{
    Logger log = LoggerFactory.getLogger(HDUQuerier.class);

    private DedicatedHttpClient client = HDUHelper.getClient(); //HDU网络请求对象
    private SubmissionDao submissionDao;                        //数据库操作对象，当作参数传入
    private UserDao userDao;

    public HDUQuerier(SubmissionDao submissionDao, UserDao userDao){
        this.submissionDao = submissionDao;
        this.userDao = userDao;
    }
    public SubmissionStatus queryResult(Submission submission) {
        String html = client.get(HDUHelper.getStatusPageUrl()+"?first="+submission.getRealRunId());
        Pattern pattern = Pattern.compile(">" + submission.getRealRunId() + "</td><td>[\\s\\S]*?</td><td>([\\s\\S]*?)</td><td>[\\s\\S]*?</td><td>(\\d*?)MS</td><td>(\\d*?)K</td>");
        Matcher matcher = pattern.matcher(html);

        if(matcher.find() == false) return null;
        SubmissionStatus status = new SubmissionStatus();

        status.setRawStatus(matcher.group(1).replaceAll("<[\\s\\S]*?>", "").trim());
        status.setStatusType(RemoteStatusNormalizer.DEFAULT.getStatusType(status.getRawStatus()));

        if (status.getStatusType() == RemoteStatusType.AC) {
            status.setExecutionTime(Integer.parseInt(matcher.group(2)));
            status.setExecutionMemory(Integer.parseInt(matcher.group(3)));
        } else if (status.getStatusType() == RemoteStatusType.CE) {
            html = client.get("/viewerror.php?rid=" + submission.getRealRunId());
            status.setCompilationErrorInfo(Tools.regFind(html, "(<pre>[\\s\\S]*?</pre>)"));
        }
        return status;
    }

    public void run() {
        while(true){
            try{
                Submission submission = SubmissionManager.takeQuerySubmission(RemoteOJ.HDU);
                SubmissionStatus status = this.queryResult(submission);
                log.info("query status , "+status.getStatusType().name());
                //未查询出最终状态,最多查询60s，60s内还未查询出结果判定为提交失败
                int submissionFailedQueryCount = 0;
                while(status == null || status.getStatusType().isFinal() == false){
                    Thread.sleep(1500);
                    if(submissionFailedQueryCount < 40){//如果尝试了15次以下，就重新尝试查询
                        submissionFailedQueryCount++;
                        status = this.queryResult(submission);
                        log.info("query status , "+status.getStatusType().name());
                    }else{//查询失败，保存到数据库，标记提交失败
                        submission.setStatus(RemoteStatusType.SUBMIT_FAILED.name());
                        //submissionDao.updateByPrimaryKeySelective(submission);//
                        updateToDatabase(submission);
                        break;
                    }
                }
                if(status == null || status.getStatusType().isFinal() == false) continue;

                submission.setStatus(status.getStatusType().name());
                submission.setCompilationErrorInfo(status.getCompilationErrorInfo());
                submission.setExecutionTime(status.getExecutionTime());
                submission.setExecutionMemory(status.getExecutionMemory());

                log.info("save to table,querier");
                log.info("status = " + submission.getStatus());
                //submissionDao.updateByPrimaryKeySelective(submission);
                updateToDatabase(submission);
                Thread.sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void updateToDatabase(Submission submission){
        submissionDao.updateByPrimaryKeySelective(submission);//
        Integer flag = submission.getStatus().equals(RemoteStatusType.AC.name()) ? 1 : -1;
        log.info(JSON.toJSONString(submission));
        log.info("flag = " + flag);
        userDao.updateAcOrFailCount(submission.getUserId(),flag);
    }
}
