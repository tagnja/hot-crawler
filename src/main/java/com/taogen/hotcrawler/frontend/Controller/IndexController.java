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
    @GetMapping("/v2")
    public String toIndexPageV2(@RequestParam(name = "tab", required = false) String tab, Model model)
    {
        log.debug("Go to index page: " + domain);
        /*String tab = request.getRequestURL().toString();
        log.debug("url:" + tab);
        tab = tab.substring(tab.indexOf("#") + 1);*/
        log.debug("tab: " + tab);
        String cateId = "1";
        String typeId = "1";
        if (tab != null && tab.split("-").length == 2)
        {
            tab = tab.trim();
            cateId = tab.split("-")[0];
            typeId = tab.split("-")[1];
        }
        log.debug("cateId: " + cateId);
        log.debug("typeId: " + typeId);
        List<InfoCate> cates = siteProperties.convertToInfoCateList();
        List<Info> infos = infoService.findListByCateIdAndTypeId(cateId, typeId);
        log.debug("cates size: " + cates.size());
        log.debug("infos size: " + infos.size());

        model.addAttribute("domain", domain);
        model.addAttribute("cates", cates);
        model.addAttribute("infos", infos);
        return "index2"; //view
    }

    /**
     * v3
     */
    @GetMapping("/")
    public String toIndexPageV3(Model model)
    {
        log.debug("Go to index page: " + domain);
        model.addAttribute("domain", domain);
        return "index3"; //view
    }
}
