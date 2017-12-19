package top.tosim.actrainer.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import top.tosim.actrainer.dto.ProblemPageSelectDto;
import top.tosim.actrainer.dto.SubmissionPageSelectDto;
import top.tosim.actrainer.entity.Submission;

import java.util.List;
import java.util.Map;

public interface SubmissionDao {
    @Select("select * from submission where contest_id=#{contestId}")
    @ResultMap("BaseResultMap")
    List<Submission> selectByContestId(Integer contestId);

    //用于带参数的分页查询
    List<Map<String, Object>> selectPartByPage(SubmissionPageSelectDto pageSelectDto);

    //用于带参数的分页查询的总数
    Integer selectTotalCount(SubmissionPageSelectDto pageSelectDto);

    //插入
    int insert(Submission record);

    //插入，排除null字段
    int insertSelective(Submission record);

    //更新，排除null字段
    int updateByPrimaryKeySelective(Submission record);

    //根据oj和runid查询提交
    Submission selectByOJAndRealRunId(String Oj, Integer realRunId);

    //根据用户id查询ac提交数
    int selectAcCountByUser(Integer userId);

    //根据用户id查询非ac提交数
    int selectFailCountByUser(Integer userId);

    //not used
    int deleteByPrimaryKey(Integer id);
    int updateByPrimaryKey(Submission record);
    Submission selectByPrimaryKey(Integer id);
}