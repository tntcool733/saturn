package com.saturn.demo.dao.bean;

import java.io.Serializable;
import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public DemoInfo() {
        
    }

    private DemoInfo(Builder builder) {
        setId(builder.id);

        setName(builder.name);

        setCreateTime(builder.createTime);

    }

    public static class Builder {
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

        public Builder id(Long id) {
            this.id = id; return this;
        }

        public Builder name(String name) {
            this.name = name; return this;
        }

        public Builder createTime(Date createTime) {
            this.createTime = createTime; return this;
        }

        public DemoInfo build() {
            return new DemoInfo(this);
        }
    }
}