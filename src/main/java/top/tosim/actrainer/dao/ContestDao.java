package top.tosim.actrainer.dao;

import org.apache.ibatis.annotations.Param;
import top.tosim.actrainer.dto.ContestPageSelectDto;
import top.tosim.actrainer.entity.Contest;
import top.tosim.actrainer.entity.ContestProblem;
import top.tosim.actrainer.entity.Problem;

import java.util.List;

public interface ContestDao {
    //用于带参数的分页查询
    List<Contest> selectPartByPage(ContestPageSelectDto pageSelectDto);

    //用于带参数的分页查询的总数
    Integer selectTotalCount(ContestPageSelectDto pageSelectDto);

    //向比赛和题目关联表中插入一条题目
    Integer insertIntoContestProblem(ContestProblem contestProblem);

    //向contest中插入一场比赛
    int insert(Contest record);

    //向contest中插入一场比赛，排除null字段
    int insertSelective(Contest record);

    //根据id查询contest
    Contest selectByPrimaryKey(Integer id);

    //根据id更新contest,排除null字段
    int updateByPrimaryKeySelective(Contest record);

    //查询某次比赛中的某个题目是否被自定义编辑过，关联表中的edited_problem_id字段是否为null
    Integer selectEditIdFromContestProblem(@Param("contestId") Integer contestId,@Param("remoteOj") String remoteOj, @Param("remoteProblemId") String remoteProblemId);

    //向edited_problem表中插入一条自定义编辑过的问题
    int insertEditedProblemSelective(Problem problem);

    //更新contest_problem表中的edited_problem_id ,表明这个比赛的这个题目被自定义编辑过，且题目描述在edited_problem表中的id为edited_problem_id
    int updateContestProblemForEdit(@Param("contestId") Integer contestId,@Param("remoteOj") String remoteOj, @Param("remoteProblemId") String remoteProblemId,@Param("editedProblemId") Integer editedProblemId);

    //更新edited_problem，用于二次编辑自定义编辑的题目，题目在edited_problem的id由contest_problem表中的edited_problem_id字段查询得出
    int updateEditedProblemByPrimaryKey(Problem problem);

    //查询某次比赛中是否有某道题目
    Integer selectProblemFromContestProblem(@Param("contestId") Integer contestId,@Param("remoteOj") String remoteOj, @Param("remoteProblemId") String remoteProblemId);


    //not used
    int updateByPrimaryKey(Contest record);
    int deleteByPrimaryKey(Integer id);
}