package com.lucuicheng.plugin.generator;

/*
 * Copyright 2016 Software.
 * lucuicheng
 */

import com.lucuicheng.plugin.exception.TableException;
import com.lucuicheng.plugin.model.Field;
import com.lucuicheng.plugin.utils.*;
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
 * @goal mapper
 * @execute goal = "dao"
 * @phase process-sources
 */
public class GeneratorJavaMapper extends AbstractMojo {

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
        JSONObject sqlMap = ResourcesUtils.getSqlMap(config);

        getLog().info("init resource files config end");

        getLog().info(" :: " + jdbc.toString());

        //连接数据库 TODO Exception
        JDBCUtils.init(jdbc.getString("driver"), jdbc.getString("url"), jdbc.getString("username"), jdbc.getString("password"));

        if((tables != null && table != null) || tables != null) {//一次多张表
            //TODO 多线程
            for(int i = 0;i < tables.size(); i++) {
                generateMapper(info, jdbc, javaModel, javaClient, sqlMap, tables.getJSONObject(i));
            }
            getLog().info("mapper xml files generated successfully :)");

        } else if(table != null) {//一次单张表
            generateMapper(info, jdbc, javaModel, javaClient, sqlMap, table);
            getLog().info("mapper xml file generated successfully :)");

        } else {
            //getLog().info("no table or tables!");
            throw new TableException("no table or tables!");
        }
    }

    /**
     * generate java mapper xml
     * @param info
     * @param jdbc
     * @param sqlMap
     * @param table
     */
    private void generateMapper(JSONObject info, JSONObject jdbc, JSONObject javaModel,
            JSONObject javaClient, JSONObject sqlMap, JSONObject table) {

        //获取要创建的全局变量信息
        List<Field> fields = null;
        Field key = null;

        Map<String, Object> result = JDBCUtils.getTableInfoFrom(table.getString("name"), jdbc.getString("type"));
        if(result.containsKey("fields")) {
            fields = (List<Field>)result.get("fields");
        }
        if(result.containsKey("key")) {
            key = (Field)result.get("key");
        }

        //生成表对应的java mapper
        String daoPackageName = javaClient.getString("package");//dao的包名
        String fileName = table.getString("object") + "Mapper";//mapper xml 文件名
        String modelPackageName = javaModel.getString("package");//实体类报名
        Boolean hasCustom = Boolean.parseBoolean(table.getString("custom"));

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("packageName", sqlMap.getString("package"));//mapper xml 包名
        model.put("fileName", fileName);//mapper xml 文件名
        model.put("namespace", daoPackageName+"." + fileName);//命名空间，dao类位置

        model.put("tableName", table.getString("name"));
        model.put("fields", fields);
        model.put("key", key);

        model.put("model", modelPackageName+"." + table.getString("object"));//实体类
        model.put("modelExample", modelPackageName+"." + table.getString("object") + "Example");//实体类

        model.put("author", info.getString("author"));
        model.put("fileType", "xml");

        //从模板输出到文件//
        TemplateFileUtils.generateFrom("mapper.ftl", model, sourceDir, this.getClass(), getLog());
        if(hasCustom) {
            model.put("packageName", javaClient.getString("package") + ".custom");
            fileName = table.getString("object") + "CustomMapper";
            model.put("fileName", fileName);//mapper xml 文件名
            model.put("namespace", daoPackageName+".custom." + fileName);//命名空间，dao类位置

            model.put("dto", modelPackageName.replaceAll(".model", ".dto") + "." + table.getString("object") + "DTO");//实体类

            TemplateFileUtils.generateFrom("customMapper.ftl", model, sourceDir, this.getClass(), getLog());
        }

        getLog().info(table.getString("object") + " mapper(xml) generated!");
    }
}
