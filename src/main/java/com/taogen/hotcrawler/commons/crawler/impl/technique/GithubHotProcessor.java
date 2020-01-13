package com.taogen.hotcrawler.commons.crawler.impl.technique;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.DocumentHotProcessor;
import com.taogen.hotcrawler.commons.crawler.handler.HandlerCenter;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("GithubHotProcessor")
public class GithubHotProcessor extends DocumentHotProcessor
{
    public static final String ITEM_KEY = "Box-row";

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        injectBeans(context);
        setFieldsByProperties(siteProperties);
        this.log = LoggerFactory.getLogger(GithubHotProcessor.class);
        this.header = generateHeader();
        this.requestBody = generateRequestBody();
        this.requestMethod = RequestMethod.GET;
    }

    @Override
    protected Elements getElements(Document document) {
        return document.getElementsByClass(ITEM_KEY);
    }

    @Override
    protected List<Info> getInfoDataByElements(Elements elements) {
        List<Info> list = new ArrayList<>();
        if (elements != null) {
            int i = 0;
            for (Element element : elements) {
                // id
                String id = String.valueOf(++i);
                Element urlElement = null;
                Element descElement = null;

                try {
                    // url
                    urlElement = element.getElementsByTag("h1").get(0).getElementsByTag("a").get(0);
                    // title-desc
                    if (!element.getElementsByTag("p").isEmpty()) {
                        descElement = element.getElementsByTag("p").get(0);
                    }
                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    log.error("Can't found item element by attribute!", e);
                    log.debug("index of error element is {}.", i);
                    continue;
                }

                String infoUrl = urlElement.attr("href");
                String infoTitle = infoUrl.substring(infoUrl.indexOf('/', 1) + 1) + ". ";
                String desc = descElement == null ? "" : descElement.html();
                infoTitle = infoTitle + desc;
                infoUrl = getDomainByUrl(this.url) + infoUrl;
                list.add(new Info(id, infoTitle, infoUrl));
            }
        }
        return handlerCenter.handleData(list);
    }

    @Override
    protected Map<String, String> generateHeader() {
        return null;
    }

    @Override
    protected String generateRequestBody() {
        return null;
    }
}
