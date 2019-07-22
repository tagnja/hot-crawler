package com.taogen.hotcrawler.commons.entity;

import lombok.Data;

@Data
public class InfoType
{
    private String id;
    private String name;

    public InfoType(){}

    public InfoType(String id, String name)
    {
        this.id = id;
        this.name = name;
    }
}
