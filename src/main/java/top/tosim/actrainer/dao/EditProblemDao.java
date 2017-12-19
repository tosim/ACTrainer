package top.tosim.actrainer.dao;

import top.tosim.actrainer.entity.EditedProblem;
import top.tosim.actrainer.entity.Problem;

public interface EditProblemDao {
    int deleteByPrimaryKey(Integer id);
    int insert(Problem record);
    int insertSelective(Problem record);
    Problem selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Problem record);
    int updateByPrimaryKey(Problem record);
}