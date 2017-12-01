package top.tosim.actrainer.dao;

import org.apache.ibatis.annotations.Param;
import top.tosim.actrainer.dto.UserPageSelectDto;
import top.tosim.actrainer.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    //用于带参数的分页查询
    List<Map<String, Object>> selectPartByPage(UserPageSelectDto pageSelectDto);

    //用于带参数的分页查询的总数
    Integer selectTotalCount(UserPageSelectDto pageSelectDto);

    //插入
    int insert(User record);

    //插入,排除null字段
    int insertSelective(User record);

    //根据主键查询
    User selectByPrimaryKey(Integer id);

    //根据主键更新，排除null字段
    int updateByPrimaryKeySelective(User record);

    //根据用户名密码查询用户
    User selectByAccountNameAndPass(String accountName,String password);

    //更新用户的ac或者非ac的提交数量
    int updateAcOrFailCount(@Param("userId") Integer userId, @Param("flag") Integer flag);

    int updateIconByPrimaryKey(@Param("id") Integer id,@Param("relativePath") String relativePath);

    Integer selectByAccountName(String accountName);

    //not used
    int deleteByPrimaryKey(Integer id);
    int updateByPrimaryKey(User record);
}