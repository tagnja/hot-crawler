package com.taogen.hotcrawler.commons.crawler.handler;

import com.taogen.hotcrawler.api.service.BaseService;
import com.taogen.hotcrawler.commons.crawler.handler.impl.DeduplicationDataHandler;
import com.taogen.hotcrawler.commons.entity.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class HandlerCenter {
    private DataHandler firstDataHandler;

    @Autowired
    private BaseService baseService;

    private final List<Class> dataHandlers = new ArrayList<>(Arrays.asList(
            DeduplicationDataHandler.class
    ));

    @PostConstruct
    private void initialize(){
        setHandlerChain();
    }

    private void setHandlerChain(){
        if (dataHandlers != null && dataHandlers.size() > 0){
            firstDataHandler = (DataHandler) baseService.getBean(dataHandlers.get(0));
            DataHandler dataHandler = firstDataHandler;
            for (int i = 1; i < dataHandlers.size(); i++){
                dataHandler.nextDataHandler = (DataHandler) baseService.getBean(dataHandlers.get(i));
                dataHandler = dataHandler.nextDataHandler;
            }
        }
    }

    public List<Info> handleData(List<Info> infoList){
        DataHandler dataHandler = firstDataHandler;
        while(dataHandler != null){
            infoList = dataHandler.handleRequest(infoList);
            dataHandler = dataHandler.getNextDataHandler();
        }
        return infoList;
    }


}
