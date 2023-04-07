package com.sankuai.avatar.workflow.core.notify.builder;

import com.dianping.cat.Cat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;

/**
 * 模版解析器
 * @author Jie.li.sh
 * @create 2023-03-09
 **/
@Component
public class TemplateParser {

    private Configuration cfg;

    @PostConstruct
    public void init() {
        cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setClassForTemplateLoading(this.getClass(), "/templates/");
    }

    public String parse(String templateName, Object paramObject) {
        StringWriter stringWriter = new StringWriter();
        try {
            Template template = cfg.getTemplate(templateName);
            template.process(paramObject, stringWriter);
        } catch (TemplateException | IOException e) {
            Cat.logError(e);
            return null;
        }
        return stringWriter.toString();
    }
}
