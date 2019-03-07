package com.lucuicheng.plugin.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.maven.model.Resource;

import java.io.File;
import java.util.List;

/**
 * Created by lucc1 on 2016/6/28.
 */
public class ResourcesUtils {
    private static JSONObject config;

    public JSONObject getConfig() {
        return config;
    }

    public void setConfig(JSONObject config) {
        this.config = config;
    }

    public static JSONObject getConfig(List<Resource> resources) {
        for (Resource res : resources) {
            File rescorce = new File(res.getDirectory());
            for (File resourcefile : rescorce.listFiles()) {
                //getLog().info("resource file -> " + resourcefile);
                if ("generator.config.json".equalsIgnoreCase(resourcefile.getName())) {
                    config = JSONUtils.toJsonObj(resourcefile);
                }
            }
        }
        return config;
    }

    public static JSONObject getInfo(JSONObject config) {
        if(config.has("info")) {
           return config.getJSONObject("info");
        }
        return null;
    }

    public static JSONObject getJdbc(JSONObject config) {
        if(config.has("jdbc")) {
            return config.getJSONObject("jdbc");
        }
        return null;
    }

    public static JSONObject getAPI(JSONObject config) {
        if(config.has("api")) {
            return config.getJSONObject("api");
        }
        return null;
    }

    public static JSONObject getTable(JSONObject config) {
        if(config.has("table")) {
            return config.getJSONObject("table");
        }
        return null;
    }

    public static JSONArray getTables(JSONObject config) {
        if(config.has("tables")) {
            return config.getJSONArray("tables");
        }
        return null;
    }

    public static JSONObject getJavaModel(JSONObject config) {
        if(config.has("javaModel")) {
            return config.getJSONObject("javaModel");
        }
        return null;
    }

    public static JSONObject getSqlMap(JSONObject config) {
        if(config.has("sqlMap")) {
            return config.getJSONObject("sqlMap");
        }
        return null;
    }

    public static JSONObject getJavaClient(JSONObject config) {
        if(config.has("javaClient")) {
            return config.getJSONObject("javaClient");
        }
        return null;
    }

    public static JSONObject getJavaService(JSONObject config) {
        if(config.has("javaService")) {
            return config.getJSONObject("javaService");
        }
        return null;
    }

    public static JSONObject getJavaController(JSONObject config) {
        if(config.has("javaController")) {
            return config.getJSONObject("javaController");
        }
        return null;
    }
}
