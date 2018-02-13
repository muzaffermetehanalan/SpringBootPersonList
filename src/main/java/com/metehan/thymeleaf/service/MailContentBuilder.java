package com.metehan.thymeleaf.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.metehan.thymeleaf.model.Person;


@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(Person per) {
        Context context = new Context();
        context.setVariable("per", per);
        context.setVariable("jobs",per.getJobs());
        return templateEngine.process("email", context);
    }
}
