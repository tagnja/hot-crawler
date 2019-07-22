package com.taogen.hotcrawler.api.web.controller;

import com.taogen.hotcrawler.api.constant.SiteProperties;
import com.taogen.hotcrawler.api.exception.DataNotFoundException;
import com.taogen.hotcrawler.api.service.InfoService;
import com.taogen.hotcrawler.api.web.model.response.GenericResponseModel;
import com.taogen.hotcrawler.commons.entity.Info;
import com.taogen.hotcrawler.commons.entity.InfoType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "Information API")
@RestController("InfoController")
@RequestMapping({"/api/v1"})
public class InfoController extends BaseV1Controller
{
    private static final Logger log = LoggerFactory.getLogger(InfoController.class);
    public static final String PRODUCES_JSON = "application/json;charset=UTF-8";

    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private InfoService infoService;


    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = PRODUCES_JSON)
    public String test()
    {
        return "{ \"ret_code\": 0, \"ret_msg\": \"ok\"}";
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET, produces = PRODUCES_JSON)
    @ApiOperation("Get All Type of Information.")
    public GenericResponseModel<InfoType> getTypes()
    {
        GenericResponseModel result = new GenericResponseModel();
        result.setData(siteProperties.getSites());
        return result;
    }

    @RequestMapping(value = "/type/{id}/infos", method = RequestMethod.GET, produces = PRODUCES_JSON)
    @ApiOperation("Get All Information of specified type.")
    public GenericResponseModel<Info> getTypeInfos(@PathVariable(value = "id") Integer id)
    {
        GenericResponseModel result = new GenericResponseModel();
        List<Info> infoList = infoService.findListByTypeId(String.valueOf(id));
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
