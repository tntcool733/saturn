package com.saturn.demo.dao.mapper;

import com.saturn.demo.dao.bean.DemoInfo;
import com.saturn.demo.dao.bean.DemoInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator 2019-06-30
*/
public interface DemoInfoMapper {
    long countByExample(DemoInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DemoInfo record);

    int insertSelective(DemoInfo record);

    List<DemoInfo> selectByExample(DemoInfoExample example);

    DemoInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DemoInfo record, @Param("example") DemoInfoExample example);

    int updateByExample(@Param("record") DemoInfo record, @Param("example") DemoInfoExample example);

    int updateByPrimaryKeySelective(DemoInfo record);

    int updateByPrimaryKey(DemoInfo record);
}