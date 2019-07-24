package com.taogen.hotcrawler.api.constant;

import com.taogen.hotcrawler.commons.entity.InfoType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@EnableConfigurationProperties(SiteProperties.class)
@PropertySource("classpath:sites.properties")
@ConfigurationProperties
public class SiteProperties
{
    List<SiteInfo> sites;

    @Data
    public static class SiteInfo
    {
        private String id;
        private String name;
        private String processorName;
        private String domain;
        private String hotPageUrl;
        private String hotApiUrl;
        private String itemKey;
    }

    public List<InfoType> convertToInfoTypeList()
    {
        List<InfoType> infoTypes = new ArrayList<>();
        if (this.sites != null)
        {
            this.sites.forEach(site -> { infoTypes.add(new InfoType(site.getId(), site.getName()));});
        }
        return infoTypes;
    }

    public SiteInfo findByProcessorName(String proceesorName)
    {
        if (this.sites != null)
        {
            for (SiteInfo siteInfo : this.sites)
            {
                if (siteInfo.getProcessorName().equals(proceesorName))
                {
                    return siteInfo;
                }
            }
        }
        return null;
    }
}
