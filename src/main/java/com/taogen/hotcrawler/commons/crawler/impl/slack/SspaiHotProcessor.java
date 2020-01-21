package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.SimpleDocumentHotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SspaiHotProcessor extends SimpleDocumentHotProcessor {
    public static final String ITEM_KEY = "articleCard";

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
        Elements paiElements = element.getElementsByAttributeValueContaining("class", "pai_title");
        StringBuilder infoUrl = new StringBuilder();
        if (paiElements != null && ! paiElements.isEmpty()){
            infoUrl.append(this.prefix);
            infoUrl.append(paiElements.get(0).getElementsByTag("a").get(0).attr("href"));
            Elements infoTitleElements = paiElements.get(0).getElementsByClass("time").get(0).getElementsByTag("div");
            StringBuilder infoTitle = new StringBuilder();
            infoTitle.append("派日报");
            infoTitle.append(" ");
            infoTitle.append(infoTitleElements.get(0).text());
            return new Info(infoTitle.toString(), infoUrl.toString());
        }else{
            element = element.getElementsByClass("card_content").get(0);
            infoUrl.append(this.prefix);
            infoUrl.append(element.getElementsByTag("a").get(0).attr("href"));
            String infoTitle = element.getElementsByClass("title").get(0).html();
            return new Info(infoTitle, infoUrl.toString());
        }

    }
}
