package com.taogen.hotcrawler.commons.crawler.impl.technique;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.SimpleDocumentHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Element;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("GithubHotProcessor")
public class GithubHotProcessor extends SimpleDocumentHotProcessor
{
    public static final String ITEM_KEY = "Box-row";

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        RequestMethod requestMethod = RequestMethod.GET;
        setFieldsByProperties(siteProperties, requestMethod, generateHeader(),generateRequestBody());
        injectBeansByContext(context);
        setLog(LoggerFactory.getLogger(getClass()));
        this.elementClass = ITEM_KEY;
    }

    @Override
    protected Info getInfoByElement(Element element) {
        Element urlElement = element.getElementsByTag("h1").get(0).getElementsByTag("a").get(0);
        Element descElement = null;
        if (! element.getElementsByTag("p").isEmpty()) {
            descElement = element.getElementsByTag("p").get(0);
        }
        String repositoryName = urlElement.attr("href");
        // Title
        StringBuilder infoTitle = new StringBuilder();
        infoTitle.append(repositoryName.substring(repositoryName.indexOf('/', 1) + 1));
        infoTitle.append(". ");
        String desc = descElement == null ? "" : descElement.html();
        infoTitle.append(desc);
        // Url
        StringBuilder infoUrl = new StringBuilder();
        infoUrl.append(this.prefix);
        infoUrl.append(repositoryName);
        return new Info(infoTitle.toString(), infoUrl.toString());
    }
}
