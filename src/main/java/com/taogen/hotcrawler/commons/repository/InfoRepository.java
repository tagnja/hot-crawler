package com.taogen.hotcrawler.commons.repository;

import com.taogen.hotcrawler.commons.entity.db.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Repository
public class InfoRepository
{
    private static final Logger log = LoggerFactory.getLogger(InfoRepository.class);

    @Resource
    private RedisTemplate<String, Info> redisTemplate;
    private HashOperations<String, String, Info> hashOps;
    private ListOperations<String, Info> listOps;

    @PostConstruct
    public void init()
    {
        hashOps = redisTemplate.opsForHash();
        listOps = redisTemplate.opsForList();
    }

    public void save(Info info, String typeId)
    {
        String key = "site:" + typeId.trim() + ":info";
//        log.info("key: " + key);
        hashOps.putIfAbsent(key, info.getId(), info);
//        log.info("has key: " + redisTemplate.hasKey(key));
//        log.info("myinfo: "+ hashOps.get(key, info.getId()));
        return;
    }

    public void saveAll(List<Info> infoList, String typeId)
    {
        String key = "site:" + typeId.trim() + ":info";
        Map<String, Info> map = new HashMap<>();
        infoList.forEach(info -> {map.put(info.getId(), info);});
        hashOps.putAll(key, map);
    }

    public void update(Info info, String typeId)
    {
        System.out.println();
        String key = "site:" + typeId.trim() + ":info";
//        log.info("key: " + key);
        hashOps.put(key, info.getId(), info);
//        log.info("has key: " + redisTemplate.hasKey(key));
//        log.info("myinfo: "+ hashOps.get(key, info.getId()));
        return;
    }

    public List<Info> findByTypeId(String typeId)
    {
        Map<String, Info> infoMap = new HashMap<>();
        String key = "site:" + typeId.trim() + ":info";
//        log.info("key: " + key);
        if (redisTemplate.hasKey(key))
        {
            infoMap = hashOps.entries(key);
        }
//        log.info("infoMap: " + infoMap);
        List<Info> infoList = new ArrayList<>();
        infoMap.forEach((k, v) -> {infoList.add(v);} );
        Collections.sort(infoList);
        return infoList;
    }

    public Info findByInfoId(String typeId, String infoId)
    {
        Info info = null;
        String key = "site:" + typeId.trim() + ":info";
//        log.info("key: " + key);
        if (redisTemplate.hasKey(key))
        {
            info = hashOps.get(key, infoId);
        }
        return info;
    }

    public long countByTypeId(String typeId)
    {
        String key = "site:" + typeId.trim() + ":info";
        Long count = hashOps.size(key);
        return count == null ? 0 : count;
    }

    public void removeByTypeId(String typeId)
    {
        String key = "site:" + typeId.trim() + ":info";
        redisTemplate.delete(key);
    }
}
