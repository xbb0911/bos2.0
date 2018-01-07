package com.xbb.bos.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * freemarker测试类
 * Created by xbb on 2018/1/7.
 */
public class FreemarkerTest {

    @Test
    public void testOutput() throws IOException, TemplateException {
        //配置对象,配置模板位置
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        configuration.setDirectoryForTemplateLoading(new File("src/main/webapp/WEB-INF/templates"));
        //获取模板对象
        Template template = configuration.getTemplate("hello.ftl");
        //动态数据对象
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("title","xbb");
        paramsMap.put("msg","hello");
        //合并输出
        template.process(paramsMap,new PrintWriter(System.out));

    }
}
