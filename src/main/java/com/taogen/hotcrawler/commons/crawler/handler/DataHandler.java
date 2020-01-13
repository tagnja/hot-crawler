package com.taogen.hotcrawler.commons.crawler.handler;

import com.taogen.hotcrawler.commons.entity.Info;
import lombok.Data;

import java.util.List;

@Data
public abstract class DataHandler {
    protected DataHandler nextDataHandler;

    public abstract List<Info> handleRequest(List<Info> infoList);

    public void setNext(DataHandler datahandler){
        this.nextDataHandler = datahandler;
    }
}
