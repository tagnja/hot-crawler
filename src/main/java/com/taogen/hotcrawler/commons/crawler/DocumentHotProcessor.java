package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DocumentHotProcessor extends AbstractHotProcessor {

    protected abstract Elements getElements(Document document);
    protected abstract List<Info> getInfoDataByElements(Elements elements);

    @Override
    public List<Info> crawlHotList() {
        return crawlHotListFromDoc();
    }

    private List<Info> crawlHotListFromDoc(){
        List<Info> infoList = new ArrayList<>();
        Document document = getDocument();
        if (document != null){
            Elements elements = getElements(document);
            if (elements != null){
                infoList = getInfoDataByElements(elements);
            }
        }
        log.debug("crawl hot list from {}, list size is {}", this.name, infoList.size());
        return handlerCenter.handleData(infoList);
    }

    private Document getDocument(){
        Document doc = null;
        Connection connection = Jsoup.connect(this.url);
        if (this.header != null) {
            addBasicHeaders(connection);
            connection.headers(this.header);
        }
        try {
            doc = connection.timeout(10 * 1000).get();
        } catch (IOException e) {
            log.error("Fail to connect!", e);
        }
        return doc;
    }
}
