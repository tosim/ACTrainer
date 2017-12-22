package top.tosim.actrainer.dao;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.tosim.actrainer.config.RootConfig;
import top.tosim.actrainer.entity.Solution;
import top.tosim.actrainer.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class UserDaoTest {
    @Autowired
    UserDao userDao;
    @Autowired
    SolutionDao solutionDao;
    @Autowired
    UserService userService;

    @Test
    public void testTransaction(){
        userService.test();
    }
}
