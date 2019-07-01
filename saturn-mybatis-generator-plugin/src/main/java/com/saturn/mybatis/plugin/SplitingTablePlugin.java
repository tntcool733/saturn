package com.saturn.mybatis.plugin;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.io.File;
import java.util.List;

public class SplitingTablePlugin extends PluginAdapter {

    private String tableName = "tableName";

    public SplitingTablePlugin() {
    }

    @Override
    public boolean validate(List<java.lang.String> warnings) {
        return true;
    }
    /**
     * 在Exmaple类中添加tableName字段
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {

        if (isUseMysqlSplitingTablePlugin(introspectedTable)){
            // 添加对应的 fieldName
            String splitFieldName = introspectedTable.getTableConfiguration().getProperties().getProperty("splitFieldName");
            List<IntrospectedColumn> columns =  introspectedTable.getPrimaryKeyColumns();
            IntrospectedColumn column = null;
            for(IntrospectedColumn temp : columns) {
                if (splitFieldName.equals(temp.getJavaProperty())){
                    column = temp;
                    break;
                }
            }
            if (column == null) {
                columns =  introspectedTable.getBaseColumns();
                for(IntrospectedColumn temp : columns) {
                    if (splitFieldName.equals(temp.getJavaProperty())){
                        column = temp;
                        break;
                    }
                }
            }
            if (column == null) {
                System.out.println(splitFieldName  + " is invalid,please check configuration");
            } else {
                List<Field> fields = topLevelClass.getFields();
                boolean isExist = false;
                for(Field temp : fields) {
                    if (temp.getName().equals(splitFieldName)){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist) {
                    Field filedName = new Field(column.getJavaProperty(), column.getFullyQualifiedJavaType());
                    filedName.setVisibility(JavaVisibility.PRIVATE);
                    addField(topLevelClass,introspectedTable,filedName);
                }
            }

            String splitTablePrefix = introspectedTable.getTableConfiguration().getProperties().getProperty("splitTablePrefix");
            Field tableName = new Field(this.tableName, PrimitiveTypeWrapper.getStringInstance());
            // 默认设置为当前的table名字
            tableName.setVisibility(JavaVisibility.PRIVATE);
            addTableNameField(topLevelClass, introspectedTable, tableName);

            // 默认设置为当前的table名字
            tableName.setVisibility(JavaVisibility.PRIVATE);

            // 生成 tablePrefix static final 字段
            Field splitTablePrefixField = new Field("tablePrefix", PrimitiveTypeWrapper.getStringInstance());
            splitTablePrefixField.setStatic(true);
            splitTablePrefixField.setInitializationString("\"" + splitTablePrefix + "\"");
            splitTablePrefixField.setFinal(true);
            splitTablePrefixField.setVisibility(JavaVisibility.PUBLIC);
            topLevelClass.addField(splitTablePrefixField);



            return super.modelExampleClassGenerated(topLevelClass,
                    introspectedTable);
        } else {
            return super.modelExampleClassGenerated(topLevelClass,
                    introspectedTable);
        }
    }



    /**
     * 在object类中添加tableName字段
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            Field tableName = new Field(this.tableName,
                    PrimitiveTypeWrapper.getStringInstance());
            // 默认设置为当前的table名字
            String splitTablePrefix = introspectedTable.getTableConfiguration().getProperties().getProperty("splitTablePrefix");
            tableName.setVisibility(JavaVisibility.PRIVATE);
            addTableNameField(topLevelClass, introspectedTable, tableName);

            Field splitTablePrefixField = new Field("tablePrefix", PrimitiveTypeWrapper.getStringInstance());
            splitTablePrefixField.setInitializationString("\"" + splitTablePrefix + "\"");
            splitTablePrefixField.setFinal(true);
            splitTablePrefixField.setStatic(true);
            splitTablePrefixField.setVisibility(JavaVisibility.PUBLIC);
            topLevelClass.addField(splitTablePrefixField);

            String splitFieldName = introspectedTable.getTableConfiguration().getProperties().getProperty("splitFieldName");
            List<IntrospectedColumn> columns =  introspectedTable.getPrimaryKeyColumns();
            IntrospectedColumn column = null;
            for(IntrospectedColumn temp : columns) {
                if (splitFieldName.equals(temp.getJavaProperty())){
                    column = temp;
                    break;
                }
            }
            if (column == null) {
                columns =  introspectedTable.getBaseColumns();
                for(IntrospectedColumn temp : columns) {
                    if (splitFieldName.equals(temp.getJavaProperty())){
                        column = temp;
                        break;
                    }
                }
            }
            if (column != null) {
                String splitTableModel = introspectedTable.getTableConfiguration().getProperties().getProperty("splitTableModel","100");
                // 生成 genSplitTableName 方法
                Method method = new Method();
                method.setVisibility(JavaVisibility.PUBLIC);
                method.setStatic(true);
                method.setName("genSplitTableName");
                method.addParameter(new Parameter(column.getFullyQualifiedJavaType(), splitFieldName));
                method.setReturnType(FullyQualifiedJavaType.getStringInstance());
                method.addBodyLine("return tablePrefix + String.valueOf(" + splitFieldName  + " % " + Integer.parseInt(splitTableModel) + ");");
                topLevelClass.addMethod(method);
            }

        }
        return super.modelBaseRecordClassGenerated(topLevelClass,
                introspectedTable);
    }

    /**
     * 这三个函数在分表中需要用其他函数替换，以为分表需要传入table名字，但是count函数需要处理*/
    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            List<Element> elements = element.getElements();
            String content = elements.get(0).getFormattedContent(0);
            String[] data = content.split(" ");
            data[2] = "${" + tableName + "}";
            TextElement subSentence = new TextElement(
                    SplitingTablePlugin.join(" ", data));
            elements.set(0, subSentence);
        }
        return true;
    };

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            List<Parameter> parameters = method.getParameters();
            Parameter tableNameParameter = new Parameter(FullyQualifiedJavaType.getStringInstance(), "tableName");
            tableNameParameter.addAnnotation("@Param(\"tableName\")");
            parameters.add(0, tableNameParameter);
        }
        return super.clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            List<Element> elements = element.getElements();
            String content = elements.get(2).getFormattedContent(0);
            String[] data = content.split(" ");
            data[1] = "${" + tableName + "}";
            TextElement subSentence = new TextElement(
                    SplitingTablePlugin.join(" ", data));
            elements.set(2, subSentence);
        }
        return true;
    };

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            List<Parameter> parameters = method.getParameters();
            Parameter tableNameParameter = new Parameter(FullyQualifiedJavaType.getStringInstance(), "tableName");
            tableNameParameter.addAnnotation("@Param(\"tableName\")");
            parameters.add(0, tableNameParameter);
        }
        return super.clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean sqlMapCountByExampleElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable){
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            resetCountByExample(element);
        }
        return super.sqlMapCountByExampleElementGenerated(element, introspectedTable);
    }

    /**
     * 在xml的SelectByExample的SQL语句添加limit
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            resetSelectXmlElementTableName(element);
        }
        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element,
                introspectedTable);
    }

    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            resetUpdateXmlElementTableName(element);
        }
        return super.sqlMapUpdateByExampleWithBLOBsElementGenerated(element,
                introspectedTable);
    };
    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            resetUpdateXmlElementTableName(element);
        }
        return super.sqlMapUpdateByExampleWithBLOBsElementGenerated(element,
                introspectedTable);
    }
    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            resetUpdateXmlElementTableNameNotMapType(element);
        }
        return super.sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(
                element, introspectedTable);
    };
    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            resetUpdateXmlElementTableNameNotMapType(element);
        }
        return super.sqlMapUpdateByPrimaryKeySelectiveElementGenerated(element, introspectedTable);
    };

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element,
                                                IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            resetInsertXmlElementTableName(element);
        }
        return super.sqlMapInsertElementGenerated(element, introspectedTable);
    }
    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,
                                                         IntrospectedTable introspectedTable) {
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            resetInsertXmlElementTableName(element);
        }
        return super.sqlMapInsertSelectiveElementGenerated(element,
                introspectedTable);

    };

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element,
                                                         IntrospectedTable introspectedTable){
        if (isUseMysqlSplitingTablePlugin(introspectedTable)) {
            resetDeleteXmlElementTableName(element);
        }
        return super.sqlMapDeleteByExampleElementGenerated(element, introspectedTable);
    }



    private void resetSelectXmlElementTableName(XmlElement element) {
        List<Element> elements = element.getElements();
        TextElement subSentence = new TextElement("from ${" + tableName + "}");
        int index = 0;
        int size = elements.size();

        while(index < size){
            String content = elements.get(index).getFormattedContent(0);
            if(content.contains("from")){
                elements.set(index, subSentence);
                return;
            }
            index++;
        }

        elements.set(3, subSentence);
    }
    private void resetInsertXmlElementTableName(XmlElement element) {

        List<Element> elements = element.getElements();
        String content = elements.get(0).getFormattedContent(0);
        String[] data = content.split(" ");
        data[2] = "${" + tableName + "}";
        TextElement subSentence = new TextElement(
                SplitingTablePlugin.join(" ", data));
        elements.set(0, subSentence);
    }
    private void resetDeleteXmlElementTableName(XmlElement element) {

        List<Element> elements = element.getElements();
        String content = elements.get(0).getFormattedContent(0);
        String[] data = content.split(" ");
        data[2] = "${" + tableName + "}";
        TextElement subSentence = new TextElement(
                SplitingTablePlugin.join(" ", data));
        elements.set(0, subSentence);
    }
    private void resetUpdateXmlElementTableName(XmlElement element) {
        List<Element> elements = element.getElements();
        TextElement subSentence = new TextElement("update ${record."
                + tableName + "}");
        elements.set(0, subSentence);
    }
    private void resetUpdateXmlElementTableNameNotMapType(XmlElement element) {
        List<Element> elements = element.getElements();
        TextElement subSentence = new TextElement("update ${" + tableName + "}");
        elements.set(0, subSentence);
    }

    private void resetCountByExample(XmlElement element) {
        List<Element> elements = element.getElements();
        String content = elements.get(0).getFormattedContent(0);
        String[] data = content.split(" ");
        data[3] = "${" + tableName + "}";
        TextElement subSentence = new TextElement(
                SplitingTablePlugin.join(" ", data));
        elements.set(0, subSentence);
    }

    public static String join(String join, String[] strAry) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strAry.length; i++) {
            if (i == (strAry.length - 1)) {
                sb.append(strAry[i]);
            } else {
                sb.append(strAry[i]).append(join);
            }
        }
        return new String(sb);
    }

    /**
     * 添加字段，同时也添加get,set方法
     *
     * @param topLevelClass
     * @param introspectedTable
     * @param field
     */
    protected void addTableNameField(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, Field field) {

        CommentGenerator commentGenerator = context.getCommentGenerator();
        // 添加Java字段
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);

        String fieldName = field.getName();

        // 生成Set方法
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(generateSetMethodName(fieldName));
        method.addParameter(new Parameter(field.getType(), fieldName));
        method.addBodyLine("this." + fieldName + "=" + fieldName + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        // 生成Get方法
        String splitTableModel = introspectedTable.getTableConfiguration().getProperties().getProperty("splitTableModel","100");
        String splitFieldName = introspectedTable.getTableConfiguration().getProperties().getProperty("splitFieldName");
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(field.getType());
        method.setName(generateGetMethodName(fieldName));
        method.addBodyLine("if(" + tableName + " != null){ return " +tableName + ";}");
        method.addBodyLine("if(" + splitFieldName + " == null){ return tablePrefix;}");
        method.addBodyLine("return tablePrefix + String.valueOf( " + splitFieldName + " % " + Integer.parseInt(splitTableModel) + ");");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    protected void addField(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, Field field) {

        CommentGenerator commentGenerator = context.getCommentGenerator();

        // 添加Java字段
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);

        String fieldName = field.getName();

        // 生成Set方法
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(generateSetMethodName(fieldName));
        method.addParameter(new Parameter(field.getType(), fieldName));
        method.addBodyLine("this." + fieldName + "=" + fieldName + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(field.getType());
        method.setName(generateGetMethodName(fieldName));
        method.addBodyLine("return " + fieldName + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    protected static String generateGetMethodName(String fieldName) {
        return "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    protected static String generateSetMethodName(String fieldName) {
        return "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    private boolean isUseMysqlSplitingTablePlugin(IntrospectedTable introspectedTable) {
        String splitTablePrefix = introspectedTable.getTableConfiguration().getProperties().getProperty("splitTablePrefix");
        String splitFieldName = introspectedTable.getTableConfiguration().getProperties().getProperty("splitFieldName");
        return splitFieldName != null && splitFieldName.length() > 0 && splitTablePrefix != null && splitTablePrefix.length() > 0;
    }
}
