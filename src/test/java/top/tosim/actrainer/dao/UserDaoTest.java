package top.tosim.actrainer.dao;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.tosim.actrainer.config.RootConfig;
import top.tosim.actrainer.dto.UserPageSelectDto;
import top.tosim.actrainer.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class UserDaoTest {
    @Autowired
    UserDao userDao;
    @Test
    public void selectByAccountNameAndPassTest(){
        User usr = userDao.selectByAccountNameAndPass("Fireman","056210");
        System.out.println(JSON.toJSONString(usr));
    }

    @Test
    public void selecPartByPageTest(){
        UserPageSelectDto pageSelectDto = new UserPageSelectDto();
//        pageSelectDto.setAccountName("F");
        pageSelectDto.setPage(1);
        pageSelectDto.setSize(2);
        pageSelectDto.validateAndCalculateStart(10);
        List<Map<String,Object>> ret = userDao.selectPartByPage(pageSelectDto);

        System.out.println(JSON.toJSONString(ret));
    }

    @Test
    public void updateAcOrFailCountTest(){
        userDao.updateAcOrFailCount(1,1);
    }

    @Test
    public void testUTF8(){
        String t = new Date().getTime() + "";
        System.out.println(t);
//        User user = new User();
//        user.setAccountName("姚一城");
//        user.setPassword("你好");
//        userDao.insertSelective(user);
//        1511618250000
//        1511618065170
    }

    @Test
    public void teet(){
        User user = userDao.selectByAccountNameAndPass("Fireman","056210");
        System.out.println(JSON.toJSONString(user));
        UserPageSelectDto pageSelectDto = new UserPageSelectDto();
        pageSelectDto.validateAndCalculateStart(10);
        Integer cnt = userDao.selectTotalCount(pageSelectDto);
        System.out.println(JSON.toJSONString(userDao.selectPartByPage(pageSelectDto)));
//        userDao.updateAcOrFailCount(1,-1);
//        userDao.updateIconByPrimaryKey(1,"aass");
//        userDao.selectByAccountName("Fireman");
    }
}
