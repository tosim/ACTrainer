package top.tosim.actrainer.dao;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import top.tosim.actrainer.dto.SolutionPageSelectDto;
import top.tosim.actrainer.entity.Solution;

import java.util.List;
import java.util.Map;

public interface SolutionDao {
    @SelectProvider(type = SolutionDaoProvider.class,method = "selectTotalCount")
    Integer selectTotalCount(SolutionPageSelectDto pageSelectDto);

    @SelectProvider(type = SolutionDaoProvider.class,method = "selectPartByPage")
    @ResultMap("PartResultMap")
    List<Map<String, Object>> selectPartByPage(SolutionPageSelectDto pageSelectDto);

    int deleteByPrimaryKey(Integer id);
    int insert(Solution record);
    int insertSelective(Solution record);
    Solution selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Solution record);
    int updateByPrimaryKey(Solution record);

    class SolutionDaoProvider{
        private final String partColumnList = " id, title, user_id, create_time\n ";

        public String selectTotalCount(SolutionPageSelectDto pageSelectDto){
            return new SQL(){{
                SELECT("count(*)");
                FROM("solution");
                if(pageSelectDto.getRemoteOj() != null){
                    WHERE("remote_oj = #{remoteOj}");
                }
                if(pageSelectDto.getRemoteProblemId() != null){
                    WHERE("remote_problem_id = #{remoteProblemId}");
                }
                if(pageSelectDto.getUserId() != null){
                    WHERE("user_id = #{userId}");
                }
            }}.toString();
        }
        public String selectPartByPage(SolutionPageSelectDto pageSelectDto){
            return new SQL(){{
                SELECT(partColumnList);
                FROM("solution");
                if(pageSelectDto.getRemoteOj() != null){
                    WHERE("remote_oj = #{remoteOj}");
                }
                if(pageSelectDto.getRemoteProblemId() != null){
                    WHERE("remote_problem_id = #{remoteProblemId}");
                }
                if(pageSelectDto.getUserId() != null){
                    WHERE("user_id = #{userId}");
                }
                ORDER_BY("id desc");
            }}.toString() + " \nLIMIT #{start},#{size}";
        }
    }
}