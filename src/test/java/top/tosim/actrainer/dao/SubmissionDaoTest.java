package top.tosim.actrainer.dao;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.tosim.actrainer.config.RootConfig;
import top.tosim.actrainer.dto.SubmissionPageSelectDto;
import top.tosim.actrainer.entity.Submission;

import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class SubmissionDaoTest {
    @Autowired
    SubmissionDao submissionDao;
    @Test
    public void selectAcCountByUserTest(){
        int ret = submissionDao.selectAcCountByUser(1);
        ret = submissionDao.selectFailCountByUser(1);
        System.out.println(ret);
    }

    @Test
    public void selectPartByPageTest(){
        SubmissionPageSelectDto pageSelectDto = new SubmissionPageSelectDto();
//        pageSelectDto.setAccountName("test");
//        pageSelectDto.setLanguage("0");
        pageSelectDto.validateAndCalculateStart(10);
//        System.out.println(submissionDao.selectTotalCount(pageSelectDto));
        System.out.println(JSON.toJSONString(submissionDao.selectPartByPage(pageSelectDto)));
    }
    @Test
    public void testRank(){
//        System.out.println(JSON.toJSONString(submissionDao.selectByOJAndRealRunId("HDU",123456)));
        Submission submission = new Submission();
        submission.setLanguage("G++");
        submission.setRemoteOj("HDU");
        submission.setRemoteProblemId("1000");
        submission.setContestId(3);
        submission.setExecutionTime(0);
        submission.setExecutionMemory(2366);
        submission.setRemoteAccountName("Firemann");
        submission.setSubmitTime(new Date());
        submission.setSource("#include<stdio.h>\n" +
                "int main()\n" +
                "{\n" +
                "    int a,b;\n" +
                "    while(scanf(\"%d %d\",&a,&b) != EOF)\n" +
                "        printf(\"%d\\n\",a + b);\n" +
                "    return 0;\n" +
                "}");

        submission.setIndex(0);
        submission.setStatus("AC");
        submission.setUserId(1);
        submission.setAccountName("flasco");
        submission.setRealRunId(23348848);
        submissionDao.insertSelective(submission);
    }
}