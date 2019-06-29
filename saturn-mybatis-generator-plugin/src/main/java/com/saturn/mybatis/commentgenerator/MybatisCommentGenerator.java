package com.saturn.mybatis.commentgenerator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;

public class MybatisCommentGenerator extends DefaultCommentGenerator {

    public MybatisCommentGenerator() {
        super();
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        if(remarks == null || remarks.trim().equals("")) {
            remarks = "[注释为空! 增加db comment是更好的实践!]";
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" *  ");
        sb.append(remarks);
        sb.append("。 对应表字段为: ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");
    }

}
