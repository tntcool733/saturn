package com.saturn.demo;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.saturn.demo.dao.bean.DemoInfo;
import com.saturn.demo.dao.bean.DemoInfoExample;
import com.saturn.demo.dao.bean.DemoSplit;
import com.saturn.demo.dao.bean.DemoSplitExample;
import com.saturn.demo.dao.mapper.DemoInfoMapper;
import com.saturn.demo.dao.mapper.DemoSplitMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@MapperScan(basePackages = {"com.saturn.demo.dao.mapper"})
@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

    @Autowired
    DemoInfoMapper demoInfoMapper;

    @Autowired
    DemoSplitMapper demoSplitMapper;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("run...");
        DemoInfoExample demoInfoExample = new DemoInfoExample();
        demoInfoExample.createCriteria().andIdEqualTo(10L);
        List<DemoInfo> demoInfoList = demoInfoMapper.selectByExample(demoInfoExample);
        demoInfoList.forEach(demoInfo -> {
            log.info("[cmd=DemoApplication.run] demoInfo from db:{}", demoInfo);
        });

        DemoSplitExample demoSplitExample = new DemoSplitExample();
        demoSplitExample.setUid(773L);
        demoSplitExample.createCriteria().andUidEqualTo(773L);
        List<DemoSplit> demoSplitList = demoSplitMapper.selectByExample(demoSplitExample);
        demoSplitList.forEach(demoSplit -> {
            log.info("[cmd=DemoApplication.run] demoSplit from db:{}", demoSplit);
        });
    }

}
