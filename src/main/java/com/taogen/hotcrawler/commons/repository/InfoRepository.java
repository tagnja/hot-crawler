package com.taogen.hotcrawler.commons.repository;

import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.entity.UserVisitStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository
public class InfoRepository
{
    private static final Logger log = LoggerFactory.getLogger(InfoRepository.class);

    @Resource
    private RedisTemplate<String, Info> redisTemplate;
    private HashOperations<String, String, Info> hashOps;

    private HashOperations<String, String, UserVisitStat> userVisitCountHashOps;

    @PostConstruct
    public void init()
    {
        hashOps = redisTemplate.opsForHash();
        userVisitCountHashOps = redisTemplate.opsForHash();
    }

    private String getKeyByCateIdAndTypeId(String cateId, String typeId)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("site:");
        stringBuilder.append(cateId.trim());
        stringBuilder.append("-");
        stringBuilder.append(typeId.trim());
        stringBuilder.append(":info");
        return stringBuilder.toString();
    }
    public void save(Info info, String cateId, String typeId)
    {

        String key = getKeyByCateIdAndTypeId(cateId, typeId);
        hashOps.putIfAbsent(key, info.getId(), info);
    }

    public void saveAll(List<Info> infoList, String cateId, String typeId)
    {
        String key = getKeyByCateIdAndTypeId(cateId, typeId);
        Map<String, Info> map = new HashMap<>();
        infoList.forEach(info -> map.put(info.getId(), info));
        hashOps.putAll(key, map);
    }

    public void update(Info info, String cateId, String typeId)
    {
        String key = getKeyByCateIdAndTypeId(cateId, typeId);
        hashOps.put(key, info.getId(), info);
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
        infoMap.forEach((k, v) -> infoList.add(v));
        Collections.sort(infoList);
        log.debug("redis info list size is {}", infoList.size());
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

    public void statVisitUser(String ip, String today)
    {
        UserVisitStat userVisitStat = findVisitUser(ip, today);
        if (userVisitStat != null)
        {
            updateVisitUser(userVisitStat);
        }
        else
        {
            insertVisitUser(ip, today);
        }
    }

    public long countVisitUser(String date)
    {
        String key = getUserVisitCountKey(date);
        return userVisitCountHashOps.entries(key).size();
    }

    private void updateVisitUser(UserVisitStat userVisitStat)
    {
        String date = userVisitStat.getDate();
        String ip = userVisitStat.getIp();
        userVisitCountHashOps.put(getUserVisitCountKey(date), ip,
            new UserVisitStat(date, ip, getCurrentTime(), userVisitStat.getTodayVisitTimes() + 1));
    }

    private void insertVisitUser(String ip, String date)
    {
        String key = getUserVisitCountKey(date);
        userVisitCountHashOps.put(key, ip,
                new UserVisitStat(date, ip, getCurrentTime(), 1L));
        userVisitCountHashOps.getOperations().expire(key, 24, TimeUnit.HOURS);
    }

    public UserVisitStat findVisitUser(String ip, String today)
    {
        String key = getUserVisitCountKey(today);
        return userVisitCountHashOps.get(key, ip);
    }

    public String getUserVisitCountKey(String date)
    {
        return "user_visit:" + date;
    }

    public static long getCurrentTime()
    {
        return System.currentTimeMillis();
    }

}
