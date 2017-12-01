package top.tosim.actrainer.dao;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.tosim.actrainer.config.RootConfig;
import top.tosim.actrainer.dto.SubmissionPageSelectDto;


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
}