package com.taogen.hotcrawler.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BaseService
{
    @Autowired
    private ApplicationContext context;

    public Object getBean(String beanName)
    {
        return context.getBean(beanName);
    }

}
