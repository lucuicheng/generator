package com.lucuicheng.plugin.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.maven.plugin.logging.Log;

import java.io.*;
import java.util.Map;


public class TemplateFileUtils {

    /**
     * 将文件内容写入创建的包文件中
     * @param model
     * @param sourceDir
     * @param out
     * @param template
     * @throws IOException
     * @throws TemplateException
     */
    public static void writeIntoFile(Map<String,Object> model, File sourceDir, Writer out, Template template)
            throws IOException, TemplateException {
        //创建文件
        File file = FileUtils.createFile(model, sourceDir);

        //内容写入文件
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        template.process(model, out);
    }

    public static void generateFrom(String templateName, Map<String, Object> model, File sourceDir, Class local, Log log) {
        Configuration cfg = new Configuration();
        Writer out = null;

        try {
            cfg.setClassForTemplateLoading(local, "templates");
            Template template = cfg.getTemplate(templateName);
            TemplateFileUtils.writeIntoFile(model, sourceDir, out, template);

        } catch (IOException e) {
            log.info("create file error : 创建Template模版中出现错误");

        } catch (TemplateException e) {
            log.info("write file error : 写入Template模版中出现错误");

        } finally {
            FileUtils.closeOut(out);
        }
    }
}
