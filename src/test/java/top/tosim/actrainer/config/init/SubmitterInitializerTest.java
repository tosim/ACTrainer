package top.tosim.actrainer.config.init;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.tosim.actrainer.config.RootConfig;
import top.tosim.actrainer.dao.SubmissionDao;
import top.tosim.actrainer.entity.Submission;
import top.tosim.actrainer.remote.RemoteOJ;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(classes = {RootConfig.class})
public class SubmitterInitializerTest {
    @Autowired
    OJWorkerInitializer submitterInitializer;
    @Autowired
    SubmissionDao submissionDao;
    @Test
    public void testInit(){

    }
}
