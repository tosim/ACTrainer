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
    @Autowired
    EditProblemDao editProblemDao;
    @Test
    public void selectContestProblemRows(){
//        List<ContestProblem> list = contestDao.selectRowsByContestId(3);
//        System.out.println(JSON.toJSONString(list));
        System.out.println(JSON.toJSONString(editProblemDao.selectByPrimaryKey(1)));

    }
    @Test
    public void testInsert(){
        Contest contest = new Contest();
        contest.setUserId(1);
        contest.setTitle("ttt");
        contest.setStartTime(new Long("1513933273816"));
        contest.setDuration(new Long("86400000"));
        contest.setContestType(1);
        contestDao.insertSelective(contest);
    }
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
//        ContestPageSelectDto pageSelectDto = new ContestPageSelectDto();
//        pageSelectDto.validateAndCalculateStart(10);
////        contestDao.selectPartByPage(pageSelectDto);
//        System.out.println(JSON.toJSONString(contestDao.selectPartByPage(pageSelectDto)));
        ContestProblem contestProblem = new ContestProblem();
        contestProblem.setId(1);
        contestProblem.setContestId(1);
        contestProblem.setRemoteOj("HDU");
        contestProblem.setIndex(1);
        contestProblem.setRemoteProblemId(1000);
        contestProblem.setEditedProblemId(1);
        contestDao.updateContestProblemByPrimaryKey(contestProblem);
    }

    @Test
    public void testtt(){
        ContestProblem contestProblem = new ContestProblem();
        contestProblem.setContestId(1);
        contestProblem.setRemoteOj("HDU");
        contestProblem.setRemoteProblemId(Integer.parseInt(2222+""));
        contestProblem.setIndex(1);
        contestProblem.setEditedProblemId(null);
        contestDao.insertIntoContestProblem(contestProblem);
    }
}
