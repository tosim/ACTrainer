package top.tosim.actrainer.normalTest;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import top.tosim.actrainer.entity.Solution;

import java.util.Date;

public class NormalTest {
    @Test
    public void normalTestT(){
        Solution solution = new Solution();
        solution.setRemoteOj("HDU");
        solution.setRemoteProblemId(1000);
        solution.setTitle("Firemann title");
        solution.setUserId(1);
        solution.setContent("no content");
        solution.setCreateTime(new Date());
        System.out.println(JSON.toJSONString(solution));
    }
}
