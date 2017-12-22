package top.tosim.actrainer.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import top.tosim.actrainer.dto.UserPageSelectDto;
import top.tosim.actrainer.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    //用于带参数的分页查询
    @SelectProvider(type = UserDaoProvider.class,method = "selectPartByPage")
    @ResultMap("PartResultMap")
    List<Map<String, Object>> selectPartByPage(UserPageSelectDto pageSelectDto);

    //用于带参数的分页查询的总数
    @SelectProvider(type = UserDaoProvider.class,method = "selectTotalCount")
    Integer selectTotalCount(UserPageSelectDto pageSelectDto);

    //根据用户名密码查询用户
    @SelectProvider(type = UserDaoProvider.class,method = "selectByAccountNameAndPass")
    @ResultMap("BaseResultMap")
    User selectByAccountNameAndPass(@Param("accountName") String accountName,@Param("password") String password);

    //更新用户的ac或者非ac的提交数量
    @UpdateProvider(type = UserDaoProvider.class,method = "updateAcOrFailCount")
    int updateAcOrFailCount(@Param("userId") Integer userId, @Param("flag") Integer flag);

    @Update("UPDATE user SET icon=#{relativePath} WHERE id=#{id}")
    int updateIconByPrimaryKey(@Param("id") Integer id,@Param("relativePath") String relativePath);

    @Select("SELECT count(*) FROM user WHERE account_name = #{accountName}")
    Integer selectByAccountName(String accountName);

    @Select("select account_name from user where id = #{uid}")
    String selectNameByUserId(Integer uid);

    //
    int updateByPrimaryKeySelective(User record);
    int insert(User record);
    int insertSelective(User record);
    User selectByPrimaryKey(Integer id);
    int deleteByPrimaryKey(Integer id);
    int updateByPrimaryKey(User record);

    class UserDaoProvider{
        private final String baseColumnList = " id, account_name, password, nick_name, school, email,description,icon,gender,ac_count,fail_count ";
        private final String partColumnList = " id, account_name, nick_name,icon,ac_count,fail_count,description ";

        public String selectByAccountNameAndPass(@Param("accountName") String accountName,@Param("password") String password){
            return new SQL(){{
                SELECT(baseColumnList);
                FROM("user");
                WHERE("account_name = #{accountName}");
                WHERE("password = #{password}");
            }}.toString();
        }
        public String selectTotalCount(UserPageSelectDto pageSelectDto){
            return new SQL(){{
                SELECT("count(*)");
                FROM("user");
                if(pageSelectDto.getAccountName() != null){
                    WHERE("account_name like '%${accountName}%'");
                }
            }}.toString();
        }
        public String selectPartByPage(UserPageSelectDto pageSelectDto){
            return new SQL(){{
                SELECT(partColumnList);
                FROM("user");
                if(pageSelectDto.getAccountName() != null){
                    WHERE("account_name like '%${accountName}%'");
                }
                ORDER_BY("ac_count desc,(ac_count/(ac_count+fail_count)) desc");
            }}.toString() + " \nLIMIT #{start},#{size}";
        }
        public String updateAcOrFailCount(@Param("userId") Integer userId, @Param("flag") Integer flag){
            String t = new SQL(){{
                UPDATE("user");
                if(flag == 1){
                    SET("ac_count = ac_count+1");
                }else if(flag == -1){
                    SET("fail_count = fail_count+1");
                }
                WHERE("id = #{userId}");
            }}.toString();
//            System.out.println(t);
            return t;
        }
    }
}