package com.saturn.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

public class BuilderPlugin extends PluginAdapter {

    private static final String BUILDER_CLASS_NAME = "Builder";
    private static final String BUILDER_METHOD_NAME = "build";

    /**
     * Constructor.
     */
    public BuilderPlugin() {
        super();
    }

    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        generateBuilder(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateBuilder(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        generateBuilder(topLevelClass, introspectedTable);
        return true;
    }

    /**
     * This method generates a static builder class for a domain class
     *
     * @param topLevelClass         the domain class
     * @param introspectedTable     the table associated with the domain class
     */
    private void generateBuilder(TopLevelClass topLevelClass,
                                 IntrospectedTable introspectedTable) {

        InnerClass builderClass = new InnerClass(BUILDER_CLASS_NAME);
        context.getCommentGenerator().addClassComment(builderClass,
                introspectedTable);
        builderClass.setVisibility(JavaVisibility.PUBLIC);
        builderClass.setStatic(true);
        // private instance field
//        Field instance = new Field(BUILDER_INSTANCE_NAME, topLevelClass.getType());
//        instance.setVisibility(JavaVisibility.PRIVATE);
//        instance.setInitializationString(" new " + topLevelClass.getType().getShortName() + "()");
//        builderClass.addTableNameField(instance);

        // builder methods
        for (Field field : topLevelClass.getFields()) {
            // field
            Field f = new Field(field);
            f.setVisibility(JavaVisibility.PRIVATE); // make sure it's not exposed
            builderClass.addField(f);
            // builder method
            Method builderMethod = new Method(field.getName());
            builderMethod.setVisibility(JavaVisibility.PUBLIC);
            builderMethod.setReturnType(builderClass.getType());
            Parameter parameter = new Parameter(field.getType(), field.getName());
            builderMethod.addParameter(parameter);
            StringBuilder methodBody = new StringBuilder("this.")
                    .append(field.getName())
                    .append(" = ")
                    .append(parameter.getName())
                    .append("; return this;");
            builderMethod.addBodyLine(methodBody.toString());
            builderClass.addMethod(builderMethod);
        }


        // build() method
        Method buildMethod = new Method(BUILDER_METHOD_NAME);
        buildMethod.setReturnType(topLevelClass.getType());
        buildMethod.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder buildBody = new StringBuilder("return new ")
                .append(topLevelClass.getType().getShortName() + "(this)")
                .append(";");
        buildMethod.addBodyLine(buildBody.toString());
        builderClass.addMethod(buildMethod);

        // add builder class
        topLevelClass.addInnerClass(builderClass);
        // add builder() method
        Method staticBuilderMethod = new Method(BUILDER_CLASS_NAME.toLowerCase());
        staticBuilderMethod.setStatic(true);
        staticBuilderMethod.setVisibility(JavaVisibility.PUBLIC);
        staticBuilderMethod.setReturnType(builderClass.getType());
        StringBuilder staticBuilderBody = new StringBuilder("return new ")
                .append(builderClass.getType().getShortName())
                .append("();");
        staticBuilderMethod.addBodyLine(staticBuilderBody.toString());

        topLevelClass.addMethod(staticBuilderMethod);

        Method c2 = new Method(topLevelClass.getType().getShortName());
        c2.setConstructor(true);
        c2.setVisibility(JavaVisibility.PUBLIC);
        c2.addBodyLine("");
        topLevelClass.addMethod(c2);

        Method c1 = new Method(topLevelClass.getType().getShortName());
        c1.setConstructor(true);
        c1.setVisibility(JavaVisibility.PRIVATE);
        c1.addParameter(new Parameter(builderClass.getType(),"builder"));
        StringBuilder sb = new StringBuilder();
        for (Field field : topLevelClass.getFields()) {
            // field
            c1.addBodyLine("set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1) + "(builder." + field.getName() + ");\r\n");
        }
        topLevelClass.addMethod(c1);


    }
}
