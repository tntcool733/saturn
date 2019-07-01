package com.saturn.demo.dao.mapper;

import com.saturn.demo.dao.bean.DemoSplit;
import com.saturn.demo.dao.bean.DemoSplitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DemoSplitMapper {
    int countByExample(DemoSplitExample example);

    int deleteByPrimaryKey(@Param("tableName") String tableName, Integer id);

    int insert(DemoSplit record);

    int insertSelective(DemoSplit record);

    List<DemoSplit> selectByExample(DemoSplitExample example);

    DemoSplit selectByPrimaryKey(@Param("tableName") String tableName, Integer id);

    int updateByExampleSelective(@Param("record") DemoSplit record, @Param("example") DemoSplitExample example);

    int updateByExample(@Param("record") DemoSplit record, @Param("example") DemoSplitExample example);

    int updateByPrimaryKeySelective(DemoSplit record);

    int updateByPrimaryKey(DemoSplit record);
}