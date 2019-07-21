package com.taogen.hotcrawler.service;

import com.taogen.hotcrawler.entity.InfoType;
import com.taogen.hotcrawler.repository.impl.InfoTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoTypeService
{
    @Autowired
    private InfoTypeRepository infoTypeRepository;

    public void save(InfoType infoType)
    {
        infoTypeRepository.save(infoType);
        return;
    }

    public List<InfoType> findAll()
    {
        return infoTypeRepository.findAll();
    }

    public InfoType findById(String id)
    {
        return infoTypeRepository.findById(id);
    }

    public void remove(String id)
    {
        infoTypeRepository.remove(id);
    }
}
