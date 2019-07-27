package com.taogen.hotcrawler.commons.repository;

import com.taogen.hotcrawler.commons.entity.Info;
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

    private String getKeyByCateIdAndTypeId(String cateId, String typeId)
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("site:");
        stringBuffer.append(cateId.trim());
        stringBuffer.append("-");
        stringBuffer.append(typeId.trim());
        stringBuffer.append(":info");
        log.debug("redis key: " + stringBuffer.toString());
        return stringBuffer.toString();
    }
    public void save(Info info, String cateId, String typeId)
    {

        String key = getKeyByCateIdAndTypeId(cateId, typeId);
        hashOps.putIfAbsent(key, info.getId(), info);
        return;
    }

    public void saveAll(List<Info> infoList, String cateId, String typeId)
    {
        String key = getKeyByCateIdAndTypeId(cateId, typeId);
        Map<String, Info> map = new HashMap<>();
        infoList.forEach(info -> {map.put(info.getId(), info);});
        hashOps.putAll(key, map);
    }

    public void update(Info info, String cateId, String typeId)
    {
        System.out.println();
        String key = getKeyByCateIdAndTypeId(cateId, typeId);
        hashOps.put(key, info.getId(), info);
        return;
    }

    public List<Info> findByCateIdAndTypeId(String cateId, String typeId)
    {
        Map<String, Info> infoMap = new HashMap<>();
        String key = getKeyByCateIdAndTypeId(cateId, typeId);
        if (redisTemplate.hasKey(key))
        {
            infoMap = hashOps.entries(key);
        }
        List<Info> infoList = new ArrayList<>();
        infoMap.forEach((k, v) -> {infoList.add(v);} );
        Collections.sort(infoList);
        log.info("redis info list size: " + infoList.size());
        return infoList;
    }

    public Info findByInfoId(String cateId, String typeId, String infoId)
    {
        Info info = null;
        String key = getKeyByCateIdAndTypeId(cateId, typeId);
        if (redisTemplate.hasKey(key))
        {
            info = hashOps.get(key, infoId);
        }
        return info;
    }

    public long countByTypeId(String cateId, String typeId)
    {
        String key = getKeyByCateIdAndTypeId(cateId, typeId);
        Long count = hashOps.size(key);
        return count == null ? 0 : count;
    }

    public void removeByTypeId(String cateId, String typeId)
    {
        String key = getKeyByCateIdAndTypeId(cateId, typeId);
        redisTemplate.delete(key);
    }
}
