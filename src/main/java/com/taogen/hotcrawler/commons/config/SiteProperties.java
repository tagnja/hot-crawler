package com.taogen.hotcrawler.commons.config;

import com.taogen.hotcrawler.commons.entity.InfoCate;
import com.taogen.hotcrawler.commons.entity.InfoType;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Configuration
@EnableConfigurationProperties(SiteProperties.class)
@PropertySource("classpath:sites.properties")
@ConfigurationProperties
public class SiteProperties
{
    Logger log = LoggerFactory.getLogger(SiteProperties.class);

    List<SiteCate> cates;

    public SiteInfo getDefaultSiteInfo() {
        SiteInfo siteInfo = findSiteByFieldAndValue("type", "default");
        if (siteInfo == null && ! cates.isEmpty() && ! cates.get(0).getSites().isEmpty()){
            return cates.get(0).getSites().get(0);
        }
        return siteInfo;
    }

    @Data
    public static class SiteCate
    {
        private String code;
        private String name;
        private List<SiteInfo> sites;
    }

    @Data
    public static class SiteInfo
    {
        private String code;
        private String name;
        private String processorName;
        private String url;
        private String prefix;
        private String type;
    }

    @PostConstruct
    public void argumentsCheck(){
        log.debug("begin argument check in property file...");
        // Is url right? Does processName exist?
        // TODO
    }

    public List<InfoCate> convertToInfoCateList()
    {
        List<InfoCate> infoCates = new ArrayList<>();
        if (this.cates != null)
        {
            for (SiteCate cate : cates)
            {
                List<InfoType> infoTypes = new ArrayList<>();
                if (cate.getSites() != null)
                {
                    cate.getSites().forEach(site -> infoTypes.add(new InfoType(site.getCode(), site.getName())));
                }
                infoCates.add(new InfoCate(cate.getCode(), cate.getName(), infoTypes));
            }
        }
        return infoCates;
    }

    public List<SiteInfo> sites()
    {
        List<SiteInfo> siteInfos = new ArrayList<>();
        if (this.cates != null)
        {
            for (SiteCate siteCate : cates)
            {
                if (siteCate.getSites() != null)
                {
                    siteInfos.addAll(siteCate.getSites().stream().collect(Collectors.toList()));
                }
            }
        }
        return siteInfos;
    }

    public SiteInfo findByCateIdAndTypeId(String cateId, String code)
    {
        if (this.cates == null)
        {
            return null;
        }
        for (SiteCate siteCate : this.cates)
        {
            if (siteCate.getSites() != null && siteCate.getCode().equals(cateId))
            {
                for (SiteInfo siteInfo : siteCate.getSites())
                {
                    if (siteInfo.getCode().equals(code))
                    {
                        return siteInfo;
                    }
                }
            }
        }
        return null;
    }

    public SiteInfo getSiteBySiteCode(String siteCode){
        return findSiteByFieldAndValue("code", siteCode);
    }

    public SiteCate getCateBySiteCode(String siteCode){
        return findCateByFieldValue("code", siteCode);
    }

    private SiteInfo findSiteByFieldAndValue(String key, String value){
        if (this.cates != null) {
            for (SiteCate siteCate : this.cates) {
                if (siteCate.getSites() != null) {
                    for (SiteInfo siteInfo : siteCate.getSites()) {
                        if (getFieldValueByName(siteInfo, key).equals(value)) {
                            return siteInfo;
                        }
                    }
                }
            }
        }
        return null;
    }
    private SiteCate findCateByFieldValue(String key, String value){
        if (this.cates != null) {
            for (SiteCate siteCate : this.cates) {
                if (siteCate.getSites() != null) {
                    for (SiteInfo siteInfo : siteCate.getSites()) {
                        if (getFieldValueByName(siteInfo, key).equals(value)) {
                            return siteCate;
                        }
                    }
                }
            }
        }
        return null;
    }

    private String getFieldValueByName(Object object, String fieldName){
        Object value = null;
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            value = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return String.valueOf(value);
    }
}
