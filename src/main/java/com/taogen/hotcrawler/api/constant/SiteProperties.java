package com.taogen.hotcrawler.api.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
    }
}
