package com.saturn.demo.dao.bean;

import java.io.Serializable;

public class DemoSplit implements Serializable {
    /**
     *  [注释为空! 增加db comment是更好的实践!]。 对应表字段为: demo_split.id
     */
    private Integer id;

    /**
     *  [注释为空! 增加db comment是更好的实践!]。 对应表字段为: demo_split.uid
     */
    private Long uid;

    /**
     *  [注释为空! 增加db comment是更好的实践!]。 对应表字段为: demo_split.name
     */
    private String name;

    private static final long serialVersionUID = 1L;

    private String tableName;

    public static final String tablePrefix = "demo_split_";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public DemoSplit() {
        
    }

    private DemoSplit(Builder builder) {
        setId(builder.id);

        setUid(builder.uid);

        setName(builder.name);

    }

    public void setTableName(String tableName) {
        this.tableName=tableName;
    }

    public String getTableName() {
        if(tableName != null){ return tableName;}
        if(uid == null){ return tablePrefix;}
        return tablePrefix + String.valueOf( uid % 10);
    }

    public static String genSplitTableName(Long uid) {
        return tablePrefix + String.valueOf(uid % 10);
    }

    public static class Builder {
        /**
         *  [注释为空! 增加db comment是更好的实践!]。 对应表字段为: demo_split.id
         */
        private Integer id;

        /**
         *  [注释为空! 增加db comment是更好的实践!]。 对应表字段为: demo_split.uid
         */
        private Long uid;

        /**
         *  [注释为空! 增加db comment是更好的实践!]。 对应表字段为: demo_split.name
         */
        private String name;

        public Builder id(Integer id) {
            this.id = id; return this;
        }

        public Builder uid(Long uid) {
            this.uid = uid; return this;
        }

        public Builder name(String name) {
            this.name = name; return this;
        }

        public DemoSplit build() {
            return new DemoSplit(this);
        }
    }
}