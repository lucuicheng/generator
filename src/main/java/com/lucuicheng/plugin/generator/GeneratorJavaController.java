package com.lucuicheng.plugin.generator;

/*
 * Copyright 2016 Software.
 * lucuicheng
 */

import com.lucuicheng.plugin.exception.TableException;
import com.lucuicheng.plugin.utils.FileUtils;
import com.lucuicheng.plugin.utils.ResourcesUtils;
import com.lucuicheng.plugin.utils.StringUtils;
import com.lucuicheng.plugin.utils.TemplateFileUtils;
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
 * @goal controller
 * @phase process-sources
 */
public class GeneratorJavaController extends AbstractMojo {

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
        JSONObject table = ResourcesUtils.getTable(config);
        JSONArray tables = ResourcesUtils.getTables(config);
        JSONObject javaModel = ResourcesUtils.getJavaModel(config);
        JSONObject javaClient = ResourcesUtils.getJavaClient(config);
        JSONObject javaController = ResourcesUtils.getJavaController(config);

        if((tables != null && table != null) || tables != null) {//一次多张表
            //TODO 多线程
            for(int i = 0;i < tables.size(); i++) {
                generateService(info, javaModel, javaClient, javaController, tables.getJSONObject(i));
            }
            getLog().info("controller java files generated successfully :)");

        } else if(table != null) {//一次单张表
            generateService(info, javaModel, javaClient, javaController, table);
            getLog().info("controller java file generated successfully :)");

        } else {
            throw new TableException("no table or tables!");
        }
    }

    private void generateService(JSONObject info, JSONObject javaModel, JSONObject javaClient, JSONObject javaController, JSONObject table) {

        //生成表对应的java service 层
        String modelPackageName = javaModel.getString("package");//实体类包名
        Boolean hasDTO = Boolean.parseBoolean(javaModel.getString("dto"));

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("packageName", javaController.getString("package"));
        model.put("tableName", table.getString("name"));

        model.put("model", modelPackageName + "." + table.getString("object"));//实体类
        model.put("page", modelPackageName + "." + "Pageable");//实体类
        model.put("dataTablePage", modelPackageName + "." + "DataTablePage");//实体类
        model.put("modelName", table.getString("object"));//实体类名称

        model.put("service", modelPackageName.replaceAll(".model", ".service") + "." + "I" + table.getString("object") + "Service");//服务类
        model.put("serviceName", table.getString("object") + "Service");//服务类

        model.put("exceptionPackage", modelPackageName.replaceAll(".model", ".exception"));//异常包

        if(hasDTO) {
            model.put("dto", modelPackageName.replaceAll(".model", ".dto") + "." + table.getString("object") + "DTO");//实体类
            model.put("dtoName", table.getString("object") + "DTO");//实体类名称
        }

        model.put("className", table.getString("object") + "Controller");
        model.put("fileName", table.getString("object") + "Controller");
        model.put("author", info.getString("author"));
        model.put("fileType", "java");

        model.put("serviceName", table.getString("object") + "Service");//服务实现类名称

        TemplateFileUtils.generateFrom("controller.ftl", model, sourceDir, this.getClass(), getLog());

        getLog().info(table.getString("object") + " service generated!");
    }
}
