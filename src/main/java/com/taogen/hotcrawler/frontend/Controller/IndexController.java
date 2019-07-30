package com.taogen.hotcrawler.frontend.controller;

import com.taogen.hotcrawler.api.constant.SiteProperties;
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

    /**
     * v1
     */
    @GetMapping("/v1")
    public String toIndexPageV1(Model model)
    {
        log.debug("Go to index page: " + domain);
        model.addAttribute("domain", domain);
        return "index"; //view
    }

    /**
     * v2
     */
    @GetMapping("/")
    public String toIndexPageV2(@RequestParam(name = "tab", required = false) String tab, Model model,
        HttpServletRequest request)
    {
        log.debug("Go to index page: " + domain);
        log.debug("tab: " + tab);
        String cateId = "1", typeId = "1";
        if (tab != null && tab.split("-").length == 2)
        {
            tab = tab.trim();
            cateId = tab.split("-")[0];
            typeId = tab.split("-")[1];
        }
        List<InfoCate> cates = siteProperties.convertToInfoCateList();
        List<Info> infos = infoService.findListByCateIdAndTypeId(cateId, typeId);

        model.addAttribute("domain", domain);
        model.addAttribute("cates", cates);
        model.addAttribute("infos", infos);

        infoService.statVisitUser(request);
        log.debug("Current visit by: " + request.getRemoteAddr());
        log.info("Today visit user size: " + infoService.countVisitUser());
        return "index2"; //view
    }

    /**
     * v3
     */
    @GetMapping("/v3")
    public String toIndexPageV3(Model model)
    {
        log.debug("Go to index page: " + domain);
        model.addAttribute("domain", domain);
        return "index3"; //view
    }
}
