package top.tosim.actrainer.dao;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.tosim.actrainer.config.RootConfig;
import top.tosim.actrainer.dto.ProblemPageSelectDto;
import top.tosim.actrainer.entity.Problem;
import top.tosim.actrainer.remote.provider.hdu.HDUCrawler;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class ProblemDaoTest {
    @Autowired
    ProblemDao problemDao;
    @Test
    public void updateByRemoteOjAndProblemIdTest(){
        Problem problem = HDUCrawler.crawl(1000+"");
        System.out.println(problemDao.updateByRemoteOjAndProblemId(problem));
    }

    @Test
    public void testSelectByContestID(){
//        ProblemPageSelectDto pageSelectDto = new ProblemPageSelectDto();
//        pageSelectDto.validateAndCalculateStart(10);
//        pageSelectDto.setRemoteProblemId(1083+"");
//        List<Map<String, Object>> list = problemDao.selectPartByContestId(3);
//        System.out.println(JSON.toJSONString(list));
        System.out.println(JSON.toJSONString(problemDao.selectByOjAndPid("HDU",1083+"")));
    }
}
