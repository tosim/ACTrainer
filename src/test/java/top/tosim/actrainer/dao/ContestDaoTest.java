package top.tosim.actrainer.dao;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.tosim.actrainer.config.RootConfig;
import top.tosim.actrainer.dto.ContestPageSelectDto;
import top.tosim.actrainer.entity.Contest;
import top.tosim.actrainer.entity.ContestProblem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class ContestDaoTest {
    @Autowired
    ContestDao contestDao;

    @Test
    public void test(){
        ContestPageSelectDto pageSelectDto = new ContestPageSelectDto();
        pageSelectDto.setTitle("est");
        //pageSelectDto.setAccountName("Fireman");
        pageSelectDto.setAccountName("Fireman");
        pageSelectDto.setStatus("RUNING");
        pageSelectDto.validateAndCalculateStart(10);
        Integer totalCount = contestDao.selectTotalCount(pageSelectDto);
        System.out.println("totalCount = " + totalCount);
//        1511618065170
//        1511620803000
//        1511621665170
    }

    @Test
    public void test2(){
        ContestProblem contestProblem = new ContestProblem();
        contestProblem.setRemoteOj("HDU");
        contestProblem.setRemoteProblemId(1000);
        contestProblem.setContestId(1);

        contestDao.insertIntoContestProblem(contestProblem);
    }

    @Test
    public void test3(){
        System.out.println(contestDao.selectProblemFromContestProblem(3,"HDU","1000"));
    }

    @Test
    public void test4(){
        contestDao.selectByPrimaryKey(3);
//        System.out.println(JSON.toJSONString(contestDao.selectByPrimaryKey(3).getContainProblems()));
    }
    @Test
    public void test5(){
        ContestPageSelectDto pageSelectDto = new ContestPageSelectDto();
        pageSelectDto.validateAndCalculateStart(10);
//        contestDao.selectPartByPage(pageSelectDto);
        System.out.println(JSON.toJSONString(contestDao.selectPartByPage(pageSelectDto)));
    }
}
