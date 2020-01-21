package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class DocumentHotProcessor extends AbstractHotProcessor {

    protected abstract Elements getElements(Document document);
    protected abstract List<Info> getInfoDataByElements(Elements elements);

    @Override
    public List<Info> crawlHotList() {
        return crawlHotListFromDoc();
    }

    private List<Info> crawlHotListFromDoc(){
        List<Info> infoList = new ArrayList<>();
        Document document = getDocument(this.httpRequest);
        if (document != null){
            Elements elements = getElements(document);
            if (elements != null){
                log.debug("elements size is {}", elements.size());
                infoList = getInfoDataByElements(elements);
            }
        }
        log.debug("crawl hot list from {}, list size is {}", this.name, infoList.size());
        return handlerCenter.handleData(infoList);
    }

    @Override
    protected Map<String, String> generateHeader(){
        // Basic implementation, most situation not need header
        return null;
    }

    @Override
    protected String generateRequestBody(){
        // Basic implementation, most situation not need request body
        return null;
    }
}
