package com.lucuicheng.plugin.generator;

/*
 * Copyright 2016 Software.
 * lucuicheng
 */

import com.lucuicheng.plugin.exception.TableException;
import com.lucuicheng.plugin.utils.ResourcesUtils;
import com.lucuicheng.plugin.utils.TemplateFileUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Goal which touches a timestamp file.
 * @goal dao
 * @execute goal = "model"
 * @phase process-sources
 */
public class GeneratorJavaDao extends AbstractMojo {

    /**
     * @parameter expression="${project.basedir}"
     * @required
     * @readonly
     */
    private File baseDir;
    /**
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     * @readonly
     */
    private File sourceDir;
    /**
     * @parameter expression="${project.build.testSourceDirectory}"
     * @required
     * @readonly
     */
    private File testSourceDir;
    /**
     * @parameter expression="${project.resources}"
     * @required
     * @readonly
     */
    private List<Resource> resources;
    //private List<File> resources;
    /**
     * @parameter expression="${project.testResources}"
     * @required
     * @readonly
     */
    private List<Resource> testResources;

    public void execute() throws MojoExecutionException {
        getLog().info("generating...");

        //获取项目资源配置文件
        getLog().info("init resource files config start");
        JSONObject config = ResourcesUtils.getConfig(resources);

        //配置文件中的各种参数
        JSONObject info = ResourcesUtils.getInfo(config);
        JSONObject jdbc = ResourcesUtils.getJdbc(config);
        JSONObject table = ResourcesUtils.getTable(config);
        JSONArray tables = ResourcesUtils.getTables(config);
        JSONObject javaModel = ResourcesUtils.getJavaModel(config);
        JSONObject javaClient = ResourcesUtils.getJavaClient(config);

        if((tables != null && table != null) || tables != null) {//一次多张表
            //TODO 多线程
            for(int i = 0;i < tables.size(); i++) {
                generateDao(info, javaModel, javaClient, tables.getJSONObject(i));
            }
            getLog().info("mapper dao files generated successfully :)");

        } else if(table != null) {//一次单张表
            generateDao(info, javaModel, javaClient, table);
            getLog().info("mapper dao file generated successfully :)");

        } else {
            //getLog().info("no table or tables!");
            throw new TableException("no table or tables!");
        }
    }

    /**
     * generate java mapper(dao)
     * @param info
     * @param javaModel
     * @param javaClient
     * @param table
     */
    private void generateDao(JSONObject info, JSONObject javaModel, JSONObject javaClient, JSONObject table) {

        //获取要创建的全局变量信息
        /*List<Attr> list = null;
        if("mysql".equalsIgnoreCase(jdbc.getString("type"))) {//获取不同数据库的表字段内容
            list = JDBCUtils.getMySqlFieldsFrom(table.getString("name"));
        } else {
            //TODO
        }*/

        //生成表对应的java dao 层
        String modelPackageName = javaModel.getString("package");//实体类报名
        Boolean hasCustom = Boolean.parseBoolean(table.getString("custom"));

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("packageName", javaClient.getString("package"));
        model.put("className", table.getString("object") + "Mapper");
        model.put("fileName", table.getString("object") + "Mapper");

        model.put("tableName", table.getString("name"));

        model.put("model", modelPackageName + "." + table.getString("object"));//实体类
        model.put("modelExample", modelPackageName + "." + table.getString("object") + "Example");//实体类
        model.put("modelName", table.getString("object"));//实体类名称
        model.put("modelExampleName", table.getString("object")  + "Example");//实体类名称

        model.put("author", info.getString("author"));
        model.put("fileType", "java");

        //从模板输出到文件
        TemplateFileUtils.generateFrom("dao.ftl", model, sourceDir, this.getClass(), getLog());
        if(hasCustom) {
            model.put("packageName", javaClient.getString("package") + ".custom");
            model.put("className", table.getString("object") + "CustomMapper");
            model.put("fileName", table.getString("object") + "CustomMapper");

            model.put("dto", modelPackageName.replaceAll(".model", ".dto") + "." + table.getString("object") + "DTO");//实体类
            model.put("dtoName", table.getString("object") + "DTO");//实体类

            TemplateFileUtils.generateFrom("customDao.ftl", model, sourceDir, this.getClass(), getLog());
        }

        getLog().info(table.getString("object") + " mapper(dao) generated!");
    }
}
