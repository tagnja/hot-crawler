package com.taogen.hotcrawler.commons.crawler;

import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleDocumentHotProcessor extends DocumentHotProcessor {
    protected String elementClass;

    protected abstract Info getInfoByElement(Element element);

    @Override
    protected Elements getElements(Document document) {
        return document.getElementsByClass(elementClass);
    }

    @Override
    protected List<Info> getInfoDataByElements(Elements elements) {
        List<Info> list = new ArrayList<>();
        if (elements != null) {
            int i = 0;
            for (Element element : elements) {
                try {
                    Info info = getInfoByElement(element);
                    list.add(info);
                } catch (IndexOutOfBoundsException e) {
                    log.error("Can't find attribute in element {}!", i, e);
                }
                i++;
            }
        }
        return list;
    }
}
