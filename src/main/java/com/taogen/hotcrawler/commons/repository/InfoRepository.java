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

    public void save(Info info, String code)
    {

        String key = code;
        hashOps.putIfAbsent(key, info.getId(), info);
    }

    public void saveAll(List<Info> infoList, String code)
    {
        String key = code;
        Map<String, Info> map = new HashMap<>();
        infoList.forEach(info -> map.put(info.getId(), info));
        hashOps.putAll(key, map);
    }

    public void update(Info info, String code)
    {
        String key = code;
        hashOps.put(key, info.getId(), info);
    }

    public List<Info> findByTypeId(String code)
    {
        Map<String, Info> infoMap = new HashMap<>();
        if (redisTemplate.hasKey(code))
        {
            infoMap = hashOps.entries(code);
        }
        List<Info> infoList = new ArrayList<>();
        infoMap.forEach((k, v) -> infoList.add(v));
        Collections.sort(infoList);
        log.debug("redis info list size is {}", infoList.size());
        return infoList;
    }

    public Info findByInfoId(String code, String infoId)
    {
        Info info = null;
        if (redisTemplate.hasKey(code))
        {
            info = hashOps.get(code, infoId);
        }
        return info;
    }

    public long countByTypeId(String code)
    {
        Long count = hashOps.size(code);
        return count == null ? 0 : count;
    }

    public void removeByTypeId(String code)
    {
        redisTemplate.delete(code);
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
