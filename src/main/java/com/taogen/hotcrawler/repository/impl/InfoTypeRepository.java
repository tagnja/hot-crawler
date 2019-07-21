package com.taogen.hotcrawler.repository.impl;

import com.taogen.hotcrawler.entity.InfoType;
import com.taogen.hotcrawler.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class InfoTypeRepository implements RedisRepository<InfoType>
{

    private static final String KEY = "InfoType";
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public InfoTypeRepository(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public List<InfoType> findAll() {
        Map<Object, Object> dataMap = hashOperations.entries(KEY);
        if (dataMap == null)
        {
            return null;
        }
        List<InfoType> infoTypeList = new ArrayList<>();
        dataMap.forEach( (k,v) -> { infoTypeList.add(new InfoType(k.toString(), v.toString())); });
        return infoTypeList;
    }

    @Override
    public void save(InfoType infoType)
    {
        hashOperations.put(KEY, infoType.getId(), infoType.getName());
    }

    @Override
    public void remove(String id)
    {
        hashOperations.delete(KEY, id);
    }

    @Override
    public InfoType findById(String id) {
        Object name = hashOperations.get(KEY, id);
        if (name == null)
        {
            return null;
        }
        return new InfoType(id, name.toString());
    }
}
