package com.taogen.hotcrawler.commons.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVisitStat implements Serializable
{
    private static final long serialVersionUID = -3946754305303957850L;
    private String date;
    private String ip; // user_id
    private long recentVisitTime;
    private long todayVisitTimes;

    public UserVisitStat() {}

    public UserVisitStat(String date, String ip, long recentVisitTime, long todayVisitTimes)
    {
        this.date = date;
        this.ip = ip;
        this.recentVisitTime = recentVisitTime;
        this.todayVisitTimes = todayVisitTimes;
    }
}
