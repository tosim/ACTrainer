package top.tosim.actrainer.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import top.tosim.actrainer.dto.ContestPageSelectDto;
import top.tosim.actrainer.entity.Contest;
import top.tosim.actrainer.entity.ContestProblem;
import top.tosim.actrainer.entity.Problem;

import java.util.List;

public interface ContestDao {
    @SelectProvider(type = ContestDaoProvider.class,method = "selectTotalCount")
    Integer selectTotalCount(ContestPageSelectDto pageSelectDto);

    @SelectProvider(type = ContestDaoProvider.class,method = "selectPartByPage")
    @ResultMap("PartResultMap")
    List<Contest> selectPartByPage(ContestPageSelectDto pageSelectDto);

    //contest_problem
    @Select("select edited_problem_id from contest_problem where contest_id = #{contestId} and remote_oj = #{remoteOj} and remote_problem_id = #{remoteProblemId}")
    Integer selectEditIdFromContestProblem(@Param("contestId") Integer contestId,@Param("remoteOj") String remoteOj, @Param("remoteProblemId") String remoteProblemId);

    @InsertProvider(type = ContestDaoProvider.class,method = "insertIntoContestProblem")
    Integer insertIntoContestProblem(ContestProblem contestProblem);

    @Update("update contest_problem set edited_problem_id = #{editedProblemId} " +
            "where contest_id = #{contestId} and remote_oj = #{remoteOj} and remote_problem_id = #{remoteProblemId}")
    int updateContestProblemForEdit(@Param("contestId") Integer contestId,@Param("remoteOj") String remoteOj, @Param("remoteProblemId") String remoteProblemId,@Param("editedProblemId") Integer editedProblemId);

    @Select("select count(*) from contest_problem where contest_id = #{contestId} and remote_oj = #{remoteOj} and remote_problem_id = #{remoteProblemId}")
    Integer selectProblemFromContestProblem(@Param("contestId") Integer contestId,@Param("remoteOj") String remoteOj, @Param("remoteProblemId") String remoteProblemId);

    @Select("select * from contest_problem where contest_id = #{contestId} order by `index`")
    @ResultMap("ContestProblem")
    List<ContestProblem> selectRowsByContestId(Integer contestId);

    @Select("select * from contest_problem where contest_id = #{contestId} and remote_oj = #{remoteOj} and remote_problem_id = #{remoteProblemId}")
    @ResultMap("ContestProblem")
    ContestProblem selectRow(@Param("contestId") Integer contestId,@Param("remoteOj") String remoteOj,@Param("remoteProblemId") Integer remoteProblemId);

    @Delete("delete from contest_problem where contest_id = #{cid}")
    int deleteContestProblemByCid(Integer cid);

    @UpdateProvider(type = ContestDaoProvider.class,method = "updateContestProblemByPrimaryKey")
    int updateContestProblemByPrimaryKey(ContestProblem contestProblem);

    //--
    Contest selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Contest record);
    int updateByPrimaryKey(Contest record);
    int deleteByPrimaryKey(Integer id);
    int insert(Contest record);
    int insertSelective(Contest record);

    class ContestDaoProvider{
        private final String baseColumnList = " contest.id, title, start_time, duration, user_id,contest_type,password\n ";
        private final String partColumnList = " contest.id, title, start_time, duration, user_id,contest_type,user.account_name\n ";
        public String insertIntoContestProblem(ContestProblem contestProblem){
            return new SQL(){{
                INSERT_INTO("contest_problem");
                if(contestProblem.getContestId() != null){
                    VALUES("contest_id",contestProblem.getContestId()+"");
                }
                if(contestProblem.getRemoteOj() != null){
                    VALUES("remote_oj","'" + contestProblem.getRemoteOj() + "'");
                }
                if(contestProblem.getRemoteProblemId() != null){
                    VALUES("remote_problem_id",contestProblem.getRemoteProblemId()+"");
                }
                if(contestProblem.getEditedProblemId() != null){
                    VALUES("edited_problem_id",contestProblem.getEditedProblemId()+"");
                }
                if(contestProblem.getIndex() != null){
                    VALUES("`index`",contestProblem.getIndex()+"");
                }
            }}.toString();
        }
        public String selectPartByPage(ContestPageSelectDto pageSelectDto){
            return new SQL(){{
                SELECT(partColumnList);
                FROM("contest,user");
                WHERE("contest.user_id = user.id");
                if(pageSelectDto.getTitle() != null){
                    WHERE("title like '%${title}%'");
                }
                if(pageSelectDto.getAccountName() != null){
                    WHERE("user.account_name = #{accountName}");
                }
                if(pageSelectDto.getStatus() != null){
                    if(pageSelectDto.getStatus().equals("Pending")){
                        WHERE("start_time &gt; UNIX_TIMESTAMP()*1000");
                    }
                    if(pageSelectDto.getStatus().equals("Runing")){
                        WHERE("start_time &lt; UNIX_TIMESTAMP()*1000");
                        WHERE("(start_time+duration) &gt; UNIX_TIMESTAMP()*1000");
                    }
                    if(pageSelectDto.getStatus().equals("Ended")){
                        WHERE("(start_time+duration) &lt; UNIX_TIMESTAMP()*1000");
                    }
                }
                if(pageSelectDto.getContestType() != null){
                    WHERE("contest_type = #{contestType}");
                }
                ORDER_BY("contest.id desc");
            }}.toString() + " \nLIMIT #{start},#{size}";
        }
        public String selectTotalCount(ContestPageSelectDto pageSelectDto){
            return new SQL(){{
                SELECT("count(*)");
                FROM("contest");
                if(pageSelectDto.getAccountName() != null){
                    FROM("user");
                }
                if(pageSelectDto.getTitle() != null){
                    WHERE("title like '%${title}%'");
                }
                if(pageSelectDto.getAccountName() != null){
                    WHERE("contest.user_id = user.id");
                    WHERE("user.account_name = #{accountName}");
                }
                if(pageSelectDto.getStatus().equals("Pending")){
                    WHERE("start_time &gt; UNIX_TIMESTAMP()*1000");
                }
                if(pageSelectDto.getStatus().equals("Runing")){
                    WHERE("start_time &lt; UNIX_TIMESTAMP()*1000");
                    WHERE("(start_time+duration) &gt; UNIX_TIMESTAMP()*1000");
                }
                if(pageSelectDto.getStatus().equals("Ended")){
                    WHERE("(start_time+duration) &lt; UNIX_TIMESTAMP()*1000");
                }
                if(pageSelectDto.getContestType() != null){
                    WHERE("contest_type = #{contestType}");
                }
                ORDER_BY("contest.id desc");
            }}.toString();
        }
        public String updateContestProblemByPrimaryKey(ContestProblem contestProblem){
            return new SQL(){{
                UPDATE("contest_problem");
                if(contestProblem.getContestId() != null){
                    SET("contest_id = #{contestId}");
                }
                if(contestProblem.getRemoteOj() != null){
                    SET("remote_oj = #{remoteOj}");
                }
                if(contestProblem.getRemoteProblemId() != null){
                    SET("remote_problem_id = #{remoteProblemId,jdbcType=INTEGER}");
                }
                if(contestProblem.getEditedProblemId() != null){
                    SET("edited_problem_id = #{editedProblemId,jdbcType=INTEGER}");
                }
                if(contestProblem.getIndex() != null){
                    SET("`index` = #{index,jdbcType=INTEGER}");
                }
                WHERE("id = #{id}");
            }}.toString();
        }

    }
}