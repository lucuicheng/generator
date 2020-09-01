package com.lucuicheng.plugin.generator;

/*
 * Copyright 2019
 * lucuicheng
 */

import com.lucuicheng.plugin.exception.TableException;
import com.lucuicheng.plugin.utils.FileUtils;
import com.lucuicheng.plugin.utils.ResourcesUtils;
import com.lucuicheng.plugin.utils.TemplateFileUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Goal which touches a timestamp file.
 *
 * @goal antdProAPI
 * @phase process-sources
 */
public class GeneratorAntdProAPIService extends AbstractMojo {

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

        //配置文件中的各种参数
        JSONObject config = ResourcesUtils.getConfig(resources);
        JSONObject info = ResourcesUtils.getInfo(config);
        JSONObject api = ResourcesUtils.getAPI(config);

        //获取所有接口信息


        generateAPI(info, api);
    }

    private JSONObject getMappingsFrom(String url) {
        CloseableHttpClient httpCilent2 = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        HttpGet httpGet2 = new HttpGet(url);
        httpGet2.setConfig(requestConfig);
        String srtResult;
        try {
            HttpResponse httpResponse = httpCilent2.execute(httpGet2);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                srtResult = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果

                return JSONObject.fromObject(srtResult);

            } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                //..........
            } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                //.............
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpCilent2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new JSONObject();
    }

    private void generateAPI(JSONObject info, JSONObject api) {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("packageName", api.getString("dist"));

        model.put("mapping", getMappingsFrom(api.getString("url")));
        model.put("author", info.getString("author"));

        model.put("fileName", "antd_pro_service_api");
        model.put("fileType", "js");

        String resourcesFilePath = resources.get(0).getDirectory();//TDOD 判断和输出说明
        getLog().info("resourcesFilePath : " + resourcesFilePath);

        TemplateFileUtils.generateFrom("api.ftl", model, FileUtils.createFolder(resourcesFilePath), this.getClass(), getLog());

        getLog().info("antd pro api generated!");
    }
}