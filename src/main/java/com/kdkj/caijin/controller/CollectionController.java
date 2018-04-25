package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Collection;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.entity.Information;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.service.CollectionService;
import com.kdkj.caijin.service.FilesService;
import com.kdkj.caijin.service.InformationService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import com.kdkj.caijin.vo.CollectionVo;
import com.kdkj.caijin.vo.InformationVo;
import com.kdkj.caijin.vo.PageVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 收藏
 *
 * @author lin
 * @create 2018-04-02 13:45
 **/
@RestController
@RequestMapping("/collection")
@Slf4j
public class CollectionController {
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private FilesService filesService;
    @PostMapping("/add")
    public Result addCollection(@RequestBody Collection collection) {
        try {
            Collection byUserIdAndinformationId = collectionService.findByUserIdAndinformationId(collection.getUserid(), collection.getInformationid());
            if (byUserIdAndinformationId == null) {
                Collection insert = collectionService.insert(collection);
                return Result.ok("成功", insert);
            }else {
                collectionService.deleteByid(byUserIdAndinformationId.getId());
                return Result.ok("成功","删除成功");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    @PostMapping("/checkHaveCoolection")
    public Result checkHaveCoolection(@RequestBody Collection collection){
        Collection byUserIdAndinformationId = collectionService.findByUserIdAndinformationId(collection.getUserid(), collection.getInformationid());
        if (byUserIdAndinformationId==null){
            return Result.ok("成功",false);
        }
        return Result.ok("成功",true);
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userid", value = "用户id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)

    })
    @GetMapping("/findAllByUserid")
    public Result findAll(Pageinfo pageinfo, String userid) {
        Page<Collection> all = collectionService.findAllById(userid, PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        List<Collection> content = all.getContent();
        List<Object> collect = content.stream().map(e -> {
            CollectionVo collectionVo = new CollectionVo();
            if (!StringUtils.isEmpty(e.getInformationid())) {
                Information byId = informationService.findById(e.getInformationid());
                if (byId != null) {
                    InformationVo informationVo = new InformationVo();
                    try {
                        CopyObj.copyObjNotNullFieldsAsObj(byId, informationVo);
                    } catch (Exception e1) {
                        log.error(e1.getMessage());
                    }
                    if (!StringUtils.isEmpty(informationVo.getShowpicture())) {
                        Files filesById = filesService.findById(informationVo.getShowpicture());
                        if (filesById != null) {
                            informationVo.setPicture(filesById);
                        }
                    }
                    collectionVo.setInformationVo(informationVo);
                    collectionVo.setCollection(e);
                }
            }
            return collectionVo;
        }).collect(Collectors.toList());
        PageVo infoInPageinfo = PageUtis.getInfoInPageinfo(all);
        infoInPageinfo.setContent(collect);
        return Result.ok("成功",infoInPageinfo );
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        try {
            collectionService.deleteByid(id);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
