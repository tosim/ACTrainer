package top.tosim.actrainer.dao;

import com.alibaba.fastjson.JSON;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import top.tosim.actrainer.dto.ProblemPageSelectDto;
import top.tosim.actrainer.entity.Problem;

import java.util.List;
import java.util.Map;

public interface ProblemDao {
    //用于带参数的分页查询
    @SelectProvider(type = ProblemDaoProvider.class,method = "selectPartByPage")
    @ResultMap("PartResultMap")
    List<Map<String, Object>> selectPartByPage(ProblemPageSelectDto pageSelectDto);

    //用于带参数的分页查询的总数
    @SelectProvider(type = ProblemDaoProvider.class,method = "selectTotalCount")
    Integer selectTotalCount(ProblemPageSelectDto pageSelectDto);

    //根据Oj和Pid查询题目
    @SelectProvider(type = ProblemDaoProvider.class,method = "selectByOjAndPid")
    @ResultMap("BaseResultMap")
    Problem selectByOjAndPid(@Param("remoteOj") String remoteOj, @Param("remoteProblemId") String remoteProblemId);

    //根基Oj和pid 更新题目，用于爬取题目后的更新
    @UpdateProvider(type = ProblemDaoProvider.class,method = "updateByRemoteOjAndProblemId")
    int updateByRemoteOjAndProblemId(Problem problem);

    @SelectProvider(type = ProblemDaoProvider.class,method = "selectPartByContestId")
    @ResultMap("PartResultMap")
    List<Map<String, Object>> selectPartByContestId(Integer contestId);

    //配置于xml
    Problem selectByPrimaryKey(Integer id);
    int deleteByPrimaryKey(Integer id);
    int insert(Problem record);
    int insertSelective(Problem record);
    int updateByPrimaryKeySelective(Problem record);
    int updateByPrimaryKey(Problem record);

    class ProblemDaoProvider{
        private final String baseColumnList = " id, remote_oj, remote_problem_id, url, title, time_limit, memory_limit, description,input, output, sample_input, sample_output, hint, author ,source ";
        private final String partColumnList = " problem.id, problem.remote_oj, problem.remote_problem_id, problem.url, problem.title ";

        public String selectTotalCount(ProblemPageSelectDto pageSelectDto){
            System.out.println(JSON.toJSONString(pageSelectDto));
            return new SQL(){{
                SELECT("count(*)");
                FROM("problem");
                if(pageSelectDto.getRemoteOj() != null){
                    WHERE("remote_oj = #{remoteOj}");
                }
                if(pageSelectDto.getRemoteProblemId() != null){
                    WHERE("remote_problem_id = #{remoteProblemId}");
                }
                if(pageSelectDto.getTitle() != null){
                    WHERE("title like CONCAT('%',#{title},'%')");
                }
            }}.toString();
        }
        public String updateByRemoteOjAndProblemId(Problem problem){
            return new SQL(){{
                UPDATE("problem");
                if(problem.getUrl() != null){
                    SET("url = #{url,jdbcType=VARCHAR}");
                }
                if(problem.getTitle() != null){
                    SET("title = #{title,jdbcType=VARCHAR}");
                }
                if(problem.getTimeLimit() != null){
                    SET("time_limit = #{timeLimit,jdbcType=VARCHAR}");
                }
                if(problem.getMemoryLimit() != null){
                    SET("memory_limit = #{memoryLimit,jdbcType=VARCHAR}");
                }
                if(problem.getDescription() != null){
                    SET("description = #{description,jdbcType=VARCHAR}");
                }
                if(problem.getInput() != null){
                    SET("input = #{input,jdbcType=VARCHAR}");
                }
                if(problem.getOutput() != null){
                    SET("output = #{output,jdbcType=VARCHAR}");
                }
                if(problem.getSampleInput() != null){
                    SET("sample_input = #{sampleInput,jdbcType=VARCHAR}");
                }
                if(problem.getSampleOutput() != null){
                    SET("sample_output = #{sampleOutput,jdbcType=VARCHAR}");
                }
                if(problem.getHint() != null){
                    SET("hint = #{hint,jdbcType=VARCHAR}");
                }
                if(problem.getAuthor() != null){
                    SET("author = #{author,jdbcType=VARCHAR}");
                }
                WHERE("remote_oj = #{remoteOj,jdbcType=VARCHAR}");
                WHERE("remote_problem_id = #{remoteProblemId,jdbcType=VARCHAR}");
            }}.toString();
        }
        public String selectByOjAndPid(@Param("remoteOj") String remoteOj, @Param("remoteProblemId") String remoteProblemId){
            return new SQL(){{
                SELECT(baseColumnList);
                FROM("problem");
                WHERE("remote_oj = #{remoteOj}");
                WHERE("remote_problem_id = #{remoteProblemId}");
            }}.toString();
        }
        public String selectPartByPage(ProblemPageSelectDto pageSelectDto){
            String t = new SQL(){{
                SELECT(partColumnList);
                FROM("problem");
                if(pageSelectDto.getRemoteOj() != null){
                    WHERE("remote_oj = #{remoteOj}");
                }
                if(pageSelectDto.getRemoteProblemId() != null){
                    WHERE("remote_problem_id = #{remoteProblemId}");
                }
                if(pageSelectDto.getTitle() != null){
                    WHERE("title like CONCAT('%',#{title},'%')");
                }
                ORDER_BY("id");
            }}.toString() + " \nLIMIT #{start},#{size}";
//            System.out.println(t);
            return t;
        }
        public String selectPartByContestId(Integer contestId){
            System.out.println("cid = " + contestId);
            return new SQL(){{
                SELECT(partColumnList);
                FROM("contest_problem,problem");
                WHERE("contest_problem.contest_id = #{contestId}");
                WHERE("problem.remote_problem_id = contest_problem.remote_problem_id");
            }}.toString();
        }
    }
}