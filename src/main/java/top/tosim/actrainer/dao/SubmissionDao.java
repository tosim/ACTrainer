package top.tosim.actrainer.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import top.tosim.actrainer.dto.ProblemPageSelectDto;
import top.tosim.actrainer.dto.SubmissionPageSelectDto;
import top.tosim.actrainer.entity.Submission;

import java.util.List;
import java.util.Map;

public interface SubmissionDao {
    @Select("select * from submission where contest_id=#{contestId}")
    @ResultMap("BaseResultMap")
    List<Submission> selectByContestId(Integer contestId);

    @SelectProvider(type = SubmissionDaoProvider.class,method = "selectTotalCount")
    Integer selectTotalCount(SubmissionPageSelectDto pageSelectDto);

    @SelectProvider(type = SubmissionDaoProvider.class,method = "selectPartByPage")
    @ResultMap("PartResultMap")
    List<Map<String, Object>> selectPartByPage(SubmissionPageSelectDto pageSelectDto);

    @SelectProvider(type = SubmissionDaoProvider.class,method = "selectByOJAndRealRunId")
    @ResultMap("BaseResultMap")
    Submission selectByOJAndRealRunId(@Param("Oj") String Oj, @Param("realRunId") Integer realRunId);

    @Select("select count(*) from submission where user_id = #{userId} and status = 'AC'")
    int selectAcCountByUser(Integer userId);

    @Select("select count(*) from submission where user_id = #{userId} and status != 'AC'")
    int selectFailCountByUser(Integer userId);

    //--
    int deleteByPrimaryKey(Integer id);
    int updateByPrimaryKey(Submission record);
    Submission selectByPrimaryKey(Integer id);
    int insert(Submission record);
    int insertSelective(Submission record);
    int updateByPrimaryKeySelective(Submission record);

    class SubmissionDaoProvider{
        private final String baseColumnList = " id, submit_time, remote_problem_id, language, source, remote_account_name, remote_oj, \n" +
                "    status, real_run_id, compilation_error_info, execution_time, execution_memory, contest_id,`index`\n" +
                "    user_id, account_name ";
        private final String partColumnList = "     id, submit_time,status,remote_oj,remote_problem_id,execution_time, execution_memory,source,language,account_name,`index`\n ";

        public String selectTotalCount(SubmissionPageSelectDto pageSelectDto){
            return new SQL(){{
                SELECT("count(*)");
                FROM("submission");
                if(pageSelectDto.getFromId() != null){
                    WHERE("id <= #{fromId}");
                }
                if(pageSelectDto.getContestId() != null){
                    WHERE("contest_id = #{contestId}");
                }
                else{
                    WHERE("contest_id is null");
                }
                if(pageSelectDto.getRemoteOj() != null){
                    WHERE("remote_oj = #{remoteOj}");
                }
                if(pageSelectDto.getRemoteProblemId() != null){
                    WHERE("remote_problem_id = #{remoteProblemId}");
                }
                if(pageSelectDto.getAccountName() != null){
                    WHERE("account_name = #{accountName}");
                }
                if(pageSelectDto.getLanguage() != null){
                    WHERE("language = #{language}");
                }
                if(pageSelectDto.getStatus() != null){
                    WHERE("status = #{status}");
                }
                if(pageSelectDto.getIndex() != null){
                    WHERE("`index` = #{index}");
                }
            }}.toString();
        }
        public String selectPartByPage(SubmissionPageSelectDto pageSelectDto){
            return new SQL(){{
                SELECT(partColumnList);
                FROM("submission");
                if(pageSelectDto.getFromId() != null){
                    WHERE("id <= #{fromId}");
                }
                if(pageSelectDto.getContestId() != null){
                    WHERE("contest_id = #{contestId}");
                }
                else{
                    WHERE("contest_id is null");
                }
                if(pageSelectDto.getRemoteOj() != null){
                    WHERE("remote_oj = #{remoteOj}");
                }
                if(pageSelectDto.getRemoteProblemId() != null){
                    WHERE("remote_problem_id = #{remoteProblemId}");
                }
                if(pageSelectDto.getAccountName() != null){
                    WHERE("account_name = #{accountName}");
                }
                if(pageSelectDto.getLanguage() != null){
                    WHERE("language = #{language}");
                }
                if(pageSelectDto.getStatus() != null){
                    WHERE("status = #{status}");
                }
                if(pageSelectDto.getIndex() != null){
                    WHERE("`index` = #{index}");
                }
                ORDER_BY("id desc");
            }}.toString()+" \nLIMIT #{start},#{size}";
        }
        public String selectByOJAndRealRunId(@Param("Oj") String Oj, @Param("realRunId") Integer realRunId){
            String sql = new SQL(){{
                SELECT(baseColumnList);
                FROM("submission");
                WHERE("remote_oj = #{Oj}");
                WHERE("real_run_id = #{realRunId}");
            }}.toString();
            System.out.println(sql);
            return sql;
        }

    }
}