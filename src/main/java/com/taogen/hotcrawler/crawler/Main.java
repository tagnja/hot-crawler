package com.taogen.hotcrawler.crawler;

public class Main
{
    public static void main(String[] args)
    {
        PageProcess v2ex = new V2exPageProcessor();
        System.out.println("v2ex");
        System.out.println(v2ex.getHotList());

        PageProcess zhihu = new ZhihuPageProcessor();
        System.out.println("zhihu");
        System.out.println(zhihu.getHotList());
    }
}
