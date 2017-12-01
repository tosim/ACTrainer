package top.tosim.actrainer.dao;

import top.tosim.actrainer.dto.ProblemPageSelectDto;
import top.tosim.actrainer.entity.Problem;

import java.util.List;
import java.util.Map;

public interface ProblemDao {
    //用于带参数的分页查询
    List<Map<String, Object>> selectPartByPage(ProblemPageSelectDto pageSelectDto);

    //用于带参数的分页查询的总数
    Integer selectTotalCount(ProblemPageSelectDto pageSelectDto);

    //根据Oj和Pid查询题目
    Problem selectByOjAndPid(String oj,String pid);

    //插入一道题目
    int insert(Problem record);

    //插入一道题目,排除null字段
    int insertSelective(Problem record);

    //更新problem，排除null字段
    int updateByPrimaryKeySelective(Problem record);

    //根基Oj和pid 更新题目，用于爬取题目后的更新
    int updateByRemoteOjAndProblemId(Problem problem);

    List<Map<String, Object>> selectPartByContestId(Integer contestId);

    //not used
    Problem selectByPrimaryKey(Integer id);
    int deleteByPrimaryKey(Integer id);
    int updateByPrimaryKey(Problem record);
}