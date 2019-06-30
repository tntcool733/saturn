package com.saturn.mybatis.plugin;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * <pre>
 * add pagination using mysql limit.
 * This class is only used in ibator code generator.
 * </pre>
 */
public class PaginationPlugin extends PluginAdapter {
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        // add field, getter, setter for limit clause//
        addLimit(topLevelClass, introspectedTable, "limitStart");
        addLimit(topLevelClass, introspectedTable, "limitEnd");
        addLimitMethod(topLevelClass, introspectedTable);
        return super.modelExampleClassGenerated(topLevelClass,introspectedTable);
    }
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {

        XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
        isNotNullElement.addAttribute(new Attribute("test", "limitStart != null and limitEnd != null")); //$NON-NLS-1$ //$NON-NLS-2$
        isNotNullElement.addElement(new TextElement("limit ${limitStart} , ${limitEnd}"));
        element.addElement(isNotNullElement);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }



    private void addLimit(TopLevelClass topLevelClass,IntrospectedTable introspectedTable, String name) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(new FullyQualifiedJavaType("Long"));
        field.setName(name);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("Long"), name));
        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType("Long"));
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    private void addLimitMethod(TopLevelClass topLevelClass,IntrospectedTable introspectedTable) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("limit");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("Long"),"limitStart"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("Long"),"limitEnd"));
        method.addBodyLine("this.limitStart = limitStart;");
        method.addBodyLine("this.limitEnd = limitEnd;");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    /**
     * This plugin is always valid - no properties are required
     */
    public boolean validate(List<String> warnings) {
        return true;
    }

}