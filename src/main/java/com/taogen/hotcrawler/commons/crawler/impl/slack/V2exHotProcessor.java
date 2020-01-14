package com.taogen.hotcrawler.commons.crawler.impl.slack;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.DocumentHotProcessor;
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

@Component("V2exHotProcessor")
public class V2exHotProcessor extends DocumentHotProcessor
{
    private static final String ITEM_KEY = "item_title";

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        injectBeans(context);
        setFieldsByProperties(siteProperties);
        this.log = LoggerFactory.getLogger(V2exHotProcessor.class);
        this.header = generateHeader();
        this.requestBody = generateRequestBody();
        this.requestMethod = RequestMethod.GET;
    }

    @Override
    public Map<String, String> generateHeader() {
        // not need
        return null;
    }

    @Override
    protected String generateRequestBody() {
        // not need
        return null;
    }

    @Override
    public Elements getElements(Document document) {
        Elements elements = document.getElementsByClass(ITEM_KEY);
        return elements;
    }

    @Override
    public List<Info> getInfoDataByElements(Elements elements) {
        List<Info> list = new ArrayList<>();
        if (elements != null) {
            int i = 0;
            for (Element element : elements) {
                Elements elements1 = element.getElementsByTag("a");
                String infoTitle = elements1.html();
                StringBuilder infoUrl = new StringBuilder();
                infoUrl.append(getDomainByUrl(this.url));
                infoUrl.append(elements1.attr("href"));
                String id = String.valueOf(++i);
                String url = infoUrl.substring(0, infoUrl.indexOf("#"));
                list.add(new Info(id, infoTitle, url));
            }
        }
        return list;
    }
}
