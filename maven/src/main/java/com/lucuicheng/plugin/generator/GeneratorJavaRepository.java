package com.lucuicheng.plugin.generator;

/*
 * Copyright 2016 Software.
 * lucuicheng
 */

import com.lucuicheng.plugin.exception.TableException;
import com.lucuicheng.plugin.utils.ResourcesUtils;
import com.lucuicheng.plugin.utils.StringUtils;
import com.lucuicheng.plugin.utils.TemplateFileUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Goal which touches a timestamp file.
 *
 * @goal repository
 * @phase process-sources
 */
public class GeneratorJavaRepository extends AbstractMojo {

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
        JSONObject model = ResourcesUtils.getJavaModel(config);


        if ((tables != null && table != null) || tables != null) {//一次多张表
            // 多线程
            JSONArray.toList(tables, new JSONObject(), new JsonConfig()).parallelStream().forEach(t -> {
                generateDao(info, model, (JSONObject) t);
            });
            getLog().info("mapper dao files generated successfully :)");

        } else if (table != null) {//一次单张表
            generateDao(info, model, table);
            getLog().info("mapper dao file generated successfully :)");

        } else {
            //getLog().info("no table or tables!");
            throw new TableException("no table or tables!");
        }
    }

    /**
     * generate java mapper(dao)
     *
     * @param info
     * @param model
     * @param table
     */
    private void generateDao(JSONObject info, JSONObject model, JSONObject table) {
        try {

            String objectStr = "";
            String str = "";
            if (table.getString("object") != null && "".equals(table.getString("object"))) {
                str = StringUtils.upcaseUnderlineNext(table.getString("name"));
                objectStr = str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
            }

            //生成表对应的java dao 层
            Map<String, Object> templModel = new HashMap<String, Object>();
            templModel.put("fileName", objectStr + "Repository");

            templModel.put("modelPackageName", model.getString("package"));//实体类
            templModel.put("packageName", model.getString("repository"));//实体类
            templModel.put("modelName", objectStr);//实体类名称

            templModel.put("author", info.getString("author"));
            templModel.put("fileType", "java");

            //从模板输出到文件
            TemplateFileUtils.generateFrom("repository.ftl", templModel, sourceDir, this.getClass(), getLog());

            getLog().info(objectStr + " (repository) generated!");
        } catch (Exception e) {

        }
    }
}
