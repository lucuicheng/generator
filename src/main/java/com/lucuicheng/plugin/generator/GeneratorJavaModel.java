package com.lucuicheng.plugin.generator;

/*
 * lucuicheng
 */

import com.lucuicheng.plugin.exception.TableException;
import com.lucuicheng.plugin.model.Field;
import com.lucuicheng.plugin.utils.JDBCUtils;
import com.lucuicheng.plugin.utils.ResourcesUtils;
import com.lucuicheng.plugin.utils.TemplateFileUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Goal which touches a timestamp file.
 *
 * @goal model
 * @phase process-sources
 */
public class GeneratorJavaModel extends AbstractMojo {

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
        getLog().info("getting config...");
        JSONObject config = ResourcesUtils.getConfig(resources);

        //配置文件中的各种参数
        JSONObject info = ResourcesUtils.getInfo(config);
        JSONObject jdbc = ResourcesUtils.getJdbc(config);
        JSONObject table = ResourcesUtils.getTable(config);
        JSONArray tables = ResourcesUtils.getTables(config);
        JSONObject javaModel = ResourcesUtils.getJavaModel(config);

        //连接数据库 TODO Exception
        getLog().info("connecting database...");

        JDBCUtils.init(jdbc.getString("driver"), jdbc.getString("url"), jdbc.getString("username"), jdbc.getString("password"));

        if ((tables != null && table != null) || tables != null) {//一次多张表
            //TODO 多线程
            JSONArray.toList(tables, new JSONObject(), new JsonConfig()).parallelStream().forEach(t -> {
                generateModel(info, jdbc, javaModel, (JSONObject) t);
            });
            /*for(int i = 0;i < tables.size(); i++) {
                generateModel(info, jdbc, javaModel, tables.getJSONObject(i));
            }*/
            getLog().info("model java files generated successfully :)");

        } else if (table != null) {//一次单张表
            generateModel(info, jdbc, javaModel, table);
            getLog().info("model java file generated successfully :)");

        } else {
            throw new TableException("no table or tables!");
        }
    }

    /**
     * generate java model(entity)
     *
     * @param info
     * @param jdbc
     * @param javaModel
     * @param table
     */
    private void generateModel(JSONObject info, JSONObject jdbc, JSONObject javaModel, JSONObject table) {

        //获取要创建的全局变量信息
        List<Field> fields = null;
        Field key = null;

        Map<String, Object> result = JDBCUtils.getTableInfoFrom(table.getString("name"), jdbc.getString("type"));
        if (result.containsKey("fields")) {
            fields = (List<Field>) result.get("fields");
        }
        if (result.containsKey("key")) {
            key = (Field) result.get("key");
        }

        Boolean hasDTO = Boolean.parseBoolean(javaModel.getString("dto"));

        //生成表对应的java模型
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("packageName", javaModel.getString("package"));
        model.put("className", table.getString("object"));
        model.put("fileName", table.getString("object"));

        model.put("tableName", table.getString("name"));
        model.put("fields", fields);
        model.put("key", key);

        model.put("author", info.getString("author"));
        model.put("fileType", "java");

        //从模板输出到文件
        TemplateFileUtils.generateFrom("model.ftl", model, sourceDir, this.getClass(), getLog());

        //model.put("className", table.getString("object") + "Example");
        //model.put("fileName", table.getString("object") + "Example");
        //TemplateFileUtils.generateFrom("modelExample.ftl", model, sourceDir, this.getClass(), getLog());

        if (hasDTO) {
            String modelPackageName = javaModel.getString("package");//实体类报名

            model.put("className", table.getString("object") + "DTO");
            model.put("fileName", table.getString("object") + "DTO");

            model.put("model", modelPackageName + "." + table.getString("object"));//实体类
            model.put("modelName", table.getString("object"));//实体类名称

            model.put("packageName", modelPackageName.replaceAll(".model", ".dto"));//实体类
            model.put("dtoName", table.getString("object") + "DTO");//实体类名称

            TemplateFileUtils.generateFrom("dto.ftl", model, sourceDir, this.getClass(), getLog());
        }


        getLog().info(table.getString("object") + " model(entity) generated!");
    }
}
