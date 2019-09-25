package com.taogen.hotcrawler.frontend.controller;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.api.service.InfoService;
import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.entity.InfoCate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController
{
    Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private InfoService infoService;

    @Autowired
    private SiteProperties siteProperties;

    @Value("${domain}")
    private String domain;

    public static final String KEY_DOMAIN = "domain";
    public static final String DOMAIN_DESC = "The domain is {}";
    /**
     * v1
     */
    @GetMapping("/v1")
    public String toIndexPageV1(Model model)
    {
        log.debug(DOMAIN_DESC, domain);
        model.addAttribute(KEY_DOMAIN, domain);
        return "index"; //view
    }

    /**
     * v2
     */
    @GetMapping("/")
    public String toIndexPageV2(@RequestParam(name = "tab", required = false) String tab, Model model,
        HttpServletRequest request)
    {
        log.debug(DOMAIN_DESC, domain);
        log.debug("tab: {}", tab);
        String cateId = "1";
        String typeId = "1";
        if (tab != null && tab.split("-").length == 2)
        {
            tab = tab.trim();
            cateId = tab.split("-")[0];
            typeId = tab.split("-")[1];
        }
        List<InfoCate> cates = siteProperties.convertToInfoCateList();
        List<Info> infos = infoService.findListByCateIdAndTypeId(cateId, typeId);
        Long visitUserCount = infoService.countVisitUser();

        model.addAttribute(KEY_DOMAIN, domain);
        model.addAttribute("cates", cates);
        model.addAttribute("infos", infos);
        model.addAttribute("visitUserCount", visitUserCount);
        infoService.statVisitUser(request);
        log.info("Current visit by {}", InfoService.getRealIpAddress(request));
        log.info("Today visited user size is {}", visitUserCount);
        return "index2"; //view
    }

    /**
     * v3
     */
    @GetMapping("/v3")
    public String toIndexPageV3(Model model)
    {
        log.debug(DOMAIN_DESC, domain);
        model.addAttribute(KEY_DOMAIN, domain);
        return "index3"; //view
    }
}
