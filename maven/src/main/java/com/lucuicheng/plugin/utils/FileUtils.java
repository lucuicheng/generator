package com.lucuicheng.plugin.utils;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucc1 on 2016/6/28.
 */
public class FileUtils {

    public static void closeOut(Writer out) {
        if (out != null) {//如果不为空
            try {
                out.flush();//刷新
                out.close();//关闭
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成文件夹
     * @param folderPath
     * @return
     */
    public static File createFolder(String folderPath) {

        File folder = new File(folderPath);
        //if file doesnt exists, then create it
        if (!folder.exists()) {
            folder.mkdirs();
            return folder;
        } else {
            return folder;
        }
    }

    /**
     * 创建包，如果不存在的话
     *
     * @param model
     * @param sourceDir
     * @return
     */
    public static File createPackage(Map<String, Object> model, File sourceDir) {
        //生成包结果
        String packageName = model.get("packageName").toString();
        packageName = packageName.replace(".", File.separator.toString());
        String folderPath = sourceDir.getAbsolutePath() + File.separator + packageName;

        //创建文件夹
        return createFolder(folderPath);
    }

    /**
     * 创建要生成的文件
     *
     * @param model
     * @param sourceDir
     * @return
     */
    public static File createFile(Map<String,Object> model, File sourceDir) {
        //创建包
        File folderPath = createPackage(model, sourceDir);

        //创建文件
        String filePath = folderPath + File.separator + model.get("fileName") + "." + model.get("fileType");
        File file = new File(filePath);

        return file;
    }
}
