package com.taogen.hotcrawler.api.web.controller;

import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.api.exception.DataNotFoundException;
import com.taogen.hotcrawler.api.service.InfoService;
import com.taogen.hotcrawler.api.web.model.response.GenericResponseModel;
import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.entity.InfoCate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Information API")
@RestController("InfoController")
@RequestMapping({"/api/v1"})
//@CrossOrigin
public class InfoController extends BaseV1Controller
{
    private static final Logger log = LoggerFactory.getLogger(InfoController.class);
    public static final String PRODUCES_JSON = "application/json;charset=UTF-8";

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private InfoService infoService;


    @GetMapping(value = "/types", produces = PRODUCES_JSON)
    @ApiOperation("Get All Type of Information.")
    public GenericResponseModel<InfoCate> getTypes()
    {
        GenericResponseModel result = new GenericResponseModel();
        result.setData(siteProperties.convertToInfoCateList());
        return result;
    }

    @GetMapping(value = "/cates/{cid}/types/{tid}/infos", produces = PRODUCES_JSON)
    @ApiOperation("Get All Information of specified type.")
    public GenericResponseModel<Info> getTypeInfos(@PathVariable(value = "cid") String cateId, @PathVariable(value = "tid") String typeId)
    {
        log.debug("cateId is {}, typeId is  {}", cateId, typeId);
        GenericResponseModel result = new GenericResponseModel();
        List<Info> infoList = infoService.findListByCateIdAndTypeId(cateId, typeId);
        if (infoList != null)
        {
            result.setData(infoList);
        }
        else
        {
            throw new DataNotFoundException("无法找到指定数据");
        }
        return result;
    }
}
