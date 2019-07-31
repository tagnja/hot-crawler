package com.taogen.hotcrawler.commons.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Info implements Serializable, Comparable<Info>
{
    private static final long serialVersionUID = -3946734305303957850L;
    private String id;
    private String title;
    private String url;

    public Info() {}

    public Info(String id, String title, String url)
    {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    @Override
    public int compareTo(Info info) {
        return Integer.valueOf(this.id).compareTo(Integer.valueOf(info.id));
    }


    /**
     * @SuppressWarnings("unused")
     */
    public Map<Object, Object> getMap()
    {
        Map<Object, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("title", this.title);
        map.put("url", this.url);
        return map;
    }
}
