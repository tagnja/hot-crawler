package com.taogen.hotcrawler.commons.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InfoCate
{
    private String id;
    private String name;
    private List<InfoType> infoTypes;

    public InfoCate(){}

    public InfoCate(String id, String name, List<InfoType> types)
    {
        this.id = id;
        this.name = name;
        this.infoTypes = (types == null ? new ArrayList<>() : types);
    }
}
