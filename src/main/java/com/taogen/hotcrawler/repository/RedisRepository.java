package com.taogen.hotcrawler.repository;

import java.util.List;

public interface RedisRepository<T>
{
    List<T> findAll();
    void save(T t);
    void remove(String id);
    T findById(String id);

}
