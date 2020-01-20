package com.taogen.hotcrawler.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InfoCate
{
    private String code;
    private String name;
    private List<InfoType> infoTypes;
}
