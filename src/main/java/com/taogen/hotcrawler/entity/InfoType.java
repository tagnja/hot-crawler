package com.taogen.hotcrawler.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("infotype")
public class InfoType
{
    private String id;
    private String name;

    public InfoType(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
