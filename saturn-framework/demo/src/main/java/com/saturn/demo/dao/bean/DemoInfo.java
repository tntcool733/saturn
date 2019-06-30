package com.saturn.demo.dao.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* Created by Mybatis Generator 2019-06-30
*/
@Getter
@Setter
@ToString
@Builder
public class DemoInfo implements Serializable {
    /**
     *  [注释为空! 增加db comment是更好的实践!]。 对应表字段为: demo_info.id
     */
    private Long id;

    /**
     *  名字。 对应表字段为: demo_info.name
     */
    private String name;

    /**
     *  创建时间。 对应表字段为: demo_info.create_time
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}