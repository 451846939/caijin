package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Advertisement;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.entity.Information;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.enums.InformationRecommend;
import com.kdkj.caijin.service.AdvertisementService;
import com.kdkj.caijin.service.CollectionService;
import com.kdkj.caijin.service.FilesService;
import com.kdkj.caijin.service.InformationService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import com.kdkj.caijin.vo.AdvertisementVo;
import com.kdkj.caijin.vo.InformationVo;
import com.kdkj.caijin.vo.PageVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 新闻（信息）
 *
 * @author lin
 * @create 2018-03-31 10:28
 **/
@RestController
@RequestMapping("/information")
@Slf4j
public class InformationController {
    @Autowired
    private InformationService informationService;
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private FilesService filesService;

    @PostMapping("/add")
    public Result addInfoemation(Information information, MultipartFile file) {
        //表明内部上传
        information.setSource(null);
        try {
            informationService.insert(information, file);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.ok("成功", information);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)
    })
    @GetMapping("/findAll")
    public Result findAllPage(Pageinfo pageinfo) {
        Page<Information> all = informationService.findAll(PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(all));
    }

    @GetMapping("/findByType")
    public Result findByType(String type, String position, Pageinfo pageinfo) {
        try {
            if (StringUtils.isEmpty(type)) {
                return Result.error("信息类型不能为空");
            }
            Page<Information> byType = informationService.findByType(type, PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
            List<Information> content = byType.getContent();
            List<Advertisement> byPosition = null;
            if (!StringUtils.isEmpty(position)) {
                byPosition = advertisementService.findByPosition(position);
            }
            List<Object> contentVo = new ArrayList<>(content);
            List<Object> collect = contentVo.stream().map(e -> {
                InformationVo informationVo = new InformationVo();
                informationVo.setAdvert("1");
                try {
                    CopyObj.copyObjNotNullFieldsAsObj(e, informationVo);
                    if (!StringUtils.isEmpty(informationVo.getShowpicture())) {
                        Files byId = filesService.findById(informationVo.getShowpicture());
                        informationVo.setPicture(byId);
                    }

                } catch (Exception e1) {
                    log.error(e1.getMessage());
                }
                return informationVo;
            }).collect(Collectors.toList());
            if (byPosition != null && !byPosition.isEmpty()) {
                Advertisement advertisement = byPosition.get(0);
                AdvertisementVo advertisementVo = new AdvertisementVo();
                //表示这是一个广告
                advertisementVo.setAdvert("2");
                CopyObj.copyObjNotNullFieldsAsObj(advertisement, advertisementVo);
                try {
                    Files byId = filesService.findById(advertisementVo.getFileid());
                    advertisementVo.setFiles(byId);
                } catch (ErrMsgException e) {
                    log.error(e.getMsg());
                }
                collect.add(advertisementVo);
            }
            return Result.ok("成功",/*PageUtis.getInfoInPageinfo(byType)*/collect);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "String", paramType = "query", required = false)
    })
    @GetMapping("/findByTitle")
    public Result findByTitle(String title, Pageinfo pageinfo) {
        Page<Information> allByTitleContaining = informationService.findAllByTitleContaining(title, PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(allByTitleContaining));
    }

    @GetMapping("/findeByKeyword")
    public Result findByKeyword(String keyword, Pageinfo pageinfo) {
        try {
            Page<Information> byKeyword = informationService.findByKeyword(keyword, PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
            List<Object> collect = byKeyword.getContent().stream().map(infor -> {
                InformationVo informationVo = new InformationVo();
                try {
                    CopyObj.copyObjNotNullFieldsAsObj(infor, informationVo);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                if (informationVo.getShowpicture() != null) {
                    Files byId = filesService.findById(informationVo.getShowpicture());
                    informationVo.setPicture(byId);
                }
                return informationVo;
            }).collect(Collectors.toList());
            PageVo infoInPageinfo = PageUtis.getInfoInPageinfo(byKeyword);
            infoInPageinfo.setContent(collect);
            return Result.ok("成功", infoInPageinfo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update(@RequestBody Information information) {
        try {
            informationService.update(information);
            return Result.ok("成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/getFindByDetails")
    public Result findByDetails(String informationid, String position) {
        Information byId = informationService.findById(informationid);
        try {
            InformationVo informationVo = new InformationVo();
            List<Object> keyInfoInPageinfo = null;
            Pageinfo pageinfo = new Pageinfo();
            pageinfo.setPageNum(0);
            pageinfo.setPageSize(4);
            pageinfo.setOrderBy("time");
            if (byId != null) {
                CopyObj.copyObjNotNullFieldsAsObj(byId, informationVo);
                if (!StringUtils.isEmpty(byId.getShowpicture())) {
                    setPicture(byId, informationVo);
                }
                //相关新闻
                if (!StringUtils.isEmpty(informationVo.getKeyword())) {
                    String[] strings = StringUtils.commaDelimitedListToStringArray(informationVo.getKeyword());
                    List<String> key = Arrays.asList(strings);
                    Optional<String> any = key.stream().findAny();

                    if (any.isPresent()) {
                        Page<Information> byKeyword = informationService.findByKeyword(any.get(), PageUtis.getPageRequest(pageinfo, Sort.Direction.DESC));
                        keyInfoInPageinfo = getInformationVoList(byKeyword);
                    }
                }
                //推荐新闻
                Page<Information> byRecommend = informationService.findByRecommend(InformationRecommend.RECOMMEND.getCode(), PageUtis.getPageRequest(pageinfo, Sort.Direction.DESC));
                List<Object> recommendInformationVoList = getInformationVoList(byRecommend);
                //返回
                Result put = Result.ok().put("data", informationVo).put("keyInfo", keyInfoInPageinfo).put("recommend", recommendInformationVoList);
                if (StringUtils.isEmpty(position)) {
                    return put;
                }
                List<Advertisement> byPosition = advertisementService.findByPosition(position);
                List<AdvertisementVo> advertisementVos = byPosition.stream().map(advertisement -> {
                    AdvertisementVo advertisementVo = new AdvertisementVo();
                    try {
                        CopyObj.copyObjNotNullFieldsAsObj(advertisement, advertisementVo);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    if (!StringUtils.isEmpty(advertisement.getFileid())) {
                        Files byId1 = filesService.findById(advertisement.getFileid());
                        advertisementVo.setFiles(byId1);
                        //广告
                        advertisementVo.setAdvert("2");
                    }
                    return advertisementVo;
                }).collect(Collectors.toList());
                return put.put("advertisements",advertisementVos);
            }
            return Result.ok("成功", informationVo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/findByRecommend")
    public Result findByRecommend(Pageinfo pageinfo){
        try {
            Page<Information> byRecommend = informationService.findByRecommend(InformationRecommend.RECOMMEND.getCode(), PageUtis.getPageRequest(pageinfo, Sort.Direction.DESC));
            List<Object> informationVoList = getInformationVoList(byRecommend);
            PageVo infoInPageinfo = PageUtis.getInfoInPageinfo(byRecommend);
            infoInPageinfo.setContent(informationVoList);
            return Result.ok("成功",infoInPageinfo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }


    private List<Object> getInformationVoList(Page<Information> byKeyword) {
        List<Information> content = byKeyword.getContent();
        List<Object> keyInfoInPageinfo = content.stream().map(e -> {
            InformationVo keyInformationVo = new InformationVo();
            try {
                CopyObj.copyObjNotNullFieldsAsObj(e, keyInformationVo);
                if (!StringUtils.isEmpty(e.getShowpicture())) {
                    setPicture(e, keyInformationVo);
                }
                return keyInformationVo;
            } catch (Exception e1) {
            }
            return keyInformationVo;
        }).collect(Collectors.toList());
        return keyInfoInPageinfo;
    }

    private void setPicture(Information byId, InformationVo informationVo) {
        String showpicture = byId.getShowpicture();
        Files files = filesService.findById(showpicture);
        informationVo.setPicture(files);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        try {
            informationService.deleteById(id);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

}
