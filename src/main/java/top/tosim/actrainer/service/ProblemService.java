package top.tosim.actrainer.service;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tosim.actrainer.dao.ProblemDao;
import top.tosim.actrainer.dto.ProblemPageSelectDto;
import top.tosim.actrainer.entity.Problem;
import top.tosim.actrainer.remote.RemoteOJ;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProblemService {
    Logger log = LoggerFactory.getLogger(ProblemService.class);

    @Autowired
    private ProblemDao problemDao;

    public Problem getProblem(String remoteOj,String id){
        log.info("pid = " + id);
//        Problem problem = HDUCrawler.crawl(id);
        Problem problem = problemDao.selectByOjAndPid(RemoteOJ.HDU.name(),id);
        log.info(JSON.toJSONString(problem));
        return problem;
    }

    public List<Map<String,Object>> getProblemList(ProblemPageSelectDto pageSelectDto){
        pageSelectDto.validateAndCalculateStart(10);
//        log.info(JSON.toJSONString(pageSelectDto));
        List<Map<String,Object>> ret = problemDao.selectPartByPage(pageSelectDto);
        log.info(JSON.toJSONString(ret));
        return ret;
    }

    public Map<String,Integer> getProblemTotalCount(ProblemPageSelectDto pageSelectDto){
        pageSelectDto.validateAndCalculateStart(10);
        Map<String,Integer> totalCount = new HashMap<String, Integer>();
        totalCount.put("totalCount",problemDao.selectTotalCount(pageSelectDto));
        return totalCount;
    }
}
