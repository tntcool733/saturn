package com.saturn.mybatis.plugin;

import java.io.File;
import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

/**
 * @author wenziheng
 * @date 2019/06/30
 */
public class DeleteExistingSqlMapsPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        String sqlMapPath = sqlMap.getTargetProject() + File.separator
            + sqlMap.getTargetPackage().replaceAll("\\.", File.separator) + File.separator + sqlMap.getFileName();
        File sqlMapFile = new File(sqlMapPath);

        boolean deleteSuccess = sqlMapFile.delete();
        if(deleteSuccess) {
            System.out.println("[DeleteExistingSqlMapsPlugin.sqlMapGenerated] delete " + sqlMapPath + " success");
        }

        return true;
    }

}