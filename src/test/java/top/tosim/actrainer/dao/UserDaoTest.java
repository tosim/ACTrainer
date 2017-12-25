package top.tosim.actrainer.dao;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.tosim.actrainer.config.RootConfig;
import top.tosim.actrainer.entity.Solution;
import top.tosim.actrainer.entity.Submission;
import top.tosim.actrainer.service.UserService;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class UserDaoTest {
    @Autowired
    UserDao userDao;
    @Autowired
    SolutionDao solutionDao;
    @Autowired
    UserService userService;
    @Autowired
    SubmissionDao submissionDao;
    @Test
    public void testTransaction(){
//        Submission submission = new Submission();
//        submission.setSource("");
//        submission.setIndex(0);
//        submission.setContestId(3);
//        submission.setRealRunId(1);
//        submission.setUserId(1);
//        submission.setRemoteOj("HDU");
//        submission.setRemoteProblemId(1000+"");
//        submission.setAccountName("Fireman");
//        submission.setStatus("AC");
//        submission.setSubmitTime(new Date());
//        submissionDao.insertSelective(submission);
//        submissionDao.insertSelective(submission);
//        submissionDao.insertSelective(submission);
        Integer a = 0;
        if(a !=  null){
            System.out.println("asas");
        }
    }

}
