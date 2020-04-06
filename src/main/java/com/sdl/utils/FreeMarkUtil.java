package com.sdl.utils;

import com.sdl.pojo.Tables;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * @program databasedoc
 * @description:
 * @author: songdeling
 * @create: 2020/04/06 23:27
 */
@Slf4j
public class FreeMarkUtil {

    /**
     * 生成word文件(全局可用)
     * @param dataMap 需要展示的动态数据
     * @param templateName 模板名称，例如：test.ftl
     * @param fileFullPath 要生成的文件全路径
     */
    @SuppressWarnings("unchecked")
    public static File createFileFromTemplate(Map<String,List<Tables>> dataMap, String templateName, String fileFullPath) {
        log.info("【createWord】：==>方法进入");
        log.info("【fileFullPath】：==>" + fileFullPath);
        log.info("【templateName】：==>" + templateName);

        try {
            // 创建配置实例
            Configuration configuration = new Configuration();
            log.info("【创建配置实例】：==>");

            // 设置编码
            configuration.setDefaultEncoding("UTF-8");
            log.info("【设置编码】：==>");

            // 设置处理空值
            configuration.setClassicCompatible(true);

            // 设置ftl模板文件加载方式
            configuration.setClassForTemplateLoading(FreeMarkUtil.class,"/templates");

            //创建文件
            File file = new File(fileFullPath);
            // 如果输出目标文件夹不存在，则创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // 将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            // 获取模板
            Template template = configuration.getTemplate(templateName);
            // 生成文件
            template.process(dataMap, out);

            // 清空缓存
            out.flush();
            // 关闭流
            out.close();
            return file;
        } catch (Exception e) {
            log.info("【生成word文件出错】：==>" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
