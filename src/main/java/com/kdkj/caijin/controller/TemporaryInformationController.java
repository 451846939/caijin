package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Information;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.entity.TemporaryInformation;
import com.kdkj.caijin.enums.CrawlerWebsite;
import com.kdkj.caijin.service.InformationService;
import com.kdkj.caijin.service.TemporaryInformationService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.NewsCrawler;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import com.kdkj.caijin.vo.CrawlerElementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 爬新闻
 *
 * @author lin
 * @create 2018-04-04 16:35
 **/
@RestController
@RequestMapping("/temporaryInformation")
public class TemporaryInformationController {
    @Autowired
    private TemporaryInformationService temporaryInformationService;
    @Autowired
    private NewsCrawler newsCrawler;
    @Autowired
    private InformationService informationService;
    @PostMapping("/addInformation")
    public Result addInformation(@RequestBody String id) {
        try {
            TemporaryInformation byId = temporaryInformationService.findById(id);
            Information information = new Information();
            CopyObj.copyObjNotNullFieldsAsObj(byId, information);
            informationService.insert(information);
            return Result.ok("成功", information);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    @PostMapping("/addAll")
    public Result addAll() {
        try {
            List<TemporaryInformation> all = temporaryInformationService.findAll();
            List<Information> informationAll = new ArrayList<>();
            for (TemporaryInformation e : all) {
                Information information = new Information();
                Information newInformation = CopyObj.copyObjNotNullFieldsAsObj(e, information);
                informationAll.add(newInformation);
            }
            informationService.insertAll(informationAll);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    @GetMapping("/findAll")
    public Result findAll(Pageinfo pageinfo) {
        Page<TemporaryInformation> all = temporaryInformationService.findAll(PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(all));
    }
    @DeleteMapping("/deleteAll")
    public Result deleteAll() {
        try {
            temporaryInformationService.deleteAll();
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    //    @DeleteMapping()
//    public Result success(){
//        temporaryInformationService.deleteAll();
//        return Result.ok();
//    }
    @PostMapping("/satrt")
    public Result start(@RequestBody CrawlerElementVo crawlerElementVo) {
        Integer crawlerWebsite = crawlerElementVo.getCrawlerWebsite();
        if (StringUtils.isEmpty(crawlerElementVo.getSource())) {
            crawlerElementVo.setSource("");
        }
        if (crawlerWebsite == CrawlerWebsite.BITKAN.getCode()) {
            crawlerElementVo.setStartUrl(CrawlerWebsite.BITKAN.getMessage());
            crawlerElementVo.setLikeUrl("http://bitkan.com/news/topic/.*");
            crawlerElementVo.setMatchUrl("http://bitkan.com/news/topic/.*");
            crawlerElementVo.setTitleElement("div[class=news-v3-in]");
            crawlerElementVo.setContentElement("div[class=news-v3-in]>div");
        }
        if (crawlerWebsite == CrawlerWebsite.W_8BTC.getCode()) {
            crawlerElementVo.setStartUrl(CrawlerWebsite.W_8BTC.getMessage());
            crawlerElementVo.setLikeUrl("http://www.8btc.com/.*");
            crawlerElementVo.setMatchUrl("http://www.8btc.com/.*");
            crawlerElementVo.setTitleElement("div[class=article-title]");
            crawlerElementVo.setContentElement("div[class=article-content]");
        }
        if (crawlerWebsite == CrawlerWebsite.BTC798.getCode()) {
            crawlerElementVo.setStartUrl(CrawlerWebsite.BTC798.getMessage());
            crawlerElementVo.setLikeUrl("http://www.btc798.com/.*");
            crawlerElementVo.setMatchUrl("http://www.btc798.com/.*");
            crawlerElementVo.setTitleElement("div[class=deanacticletop]");
            crawlerElementVo.setContentElement("table[class=vwtb]");
        }
        if (crawlerWebsite == CrawlerWebsite.BTC123.getCode()) {
            crawlerElementVo.setStartUrl(CrawlerWebsite.BTC123.getMessage());
            crawlerElementVo.setLikeUrl("http://news.btc123.com/news/.*");
            crawlerElementVo.setMatchUrl("http://news.btc123.com/news/.*");
            crawlerElementVo.setTitleElement("div[class=n_dttile]");
            crawlerElementVo.setContentElement("div[class=n_dtcont clearfloat]");
        }

        startCrawler(crawlerElementVo);

        return Result.ok();
    }

    private void startCrawler(@RequestBody CrawlerElementVo crawlerElementVo) {
        newsCrawler.setCrawlerElementVo(crawlerElementVo);
        newsCrawler.addSeed(crawlerElementVo.getStartUrl());
        newsCrawler.addRegex(crawlerElementVo.getLikeUrl());
        newsCrawler.addRegex("-.*\\.(jpg|png|gif).*");
        newsCrawler.addRegex("-.*#.*");
        /**
         * @param nextItem 被探测到的URL
         * @param referer 被探测到的URL所在的页面
         * @return 如果希望过滤nextItem，返回null，否则返回nextItem
         */
        newsCrawler.setNextFilter((nextItem, referer) -> {

            List<Information> bySourceNotNull = informationService.findBySourceNotNull();
            Object[] objects = bySourceNotNull.stream().filter(e -> nextItem.url().equals(e)).toArray();
//            System.out.println("nextItem"+nextItem);
            if (objects.length != 0) {
                return null;
            } else {
                return nextItem;
            }
        });
        newsCrawler.setThreads(200);
        if (crawlerElementVo.getClimbNum() == 0) {
            crawlerElementVo.setClimbNum(10);
        }
        newsCrawler.getConf().setTopN(crawlerElementVo.getClimbNum());
        try {
            newsCrawler.start(3);
            newsCrawler.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
