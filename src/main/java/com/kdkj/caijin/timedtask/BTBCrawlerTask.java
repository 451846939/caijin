package com.kdkj.caijin.timedtask;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.kdkj.caijin.entity.BTBInfo;
import com.kdkj.caijin.entity.Information;
import com.kdkj.caijin.service.impl.BTBInfoServiceImpl;
import com.kdkj.caijin.util.BTBCrawler;
import com.kdkj.caijin.vo.CrawlerElementVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 比特币数据定时爬取
 *
 * @author lin
 * @create 2018-04-10 16:57
 **/
@Component
@Slf4j
public class BTBCrawlerTask {
    @Autowired
    private BTBCrawler btbCrawler;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BTBInfoServiceImpl btbInfoService;

    @Async
    @Scheduled(cron = "0 0/1 * * * ?")
    public void btb2Task() {
        Map forObject = restTemplate.getForObject("https://blockchain.info/ticker", Map.class);
//        restTemplate.optionsForAllow()
//        log.info(forObject.toString());
        Set set = forObject.keySet();
        List<BTBInfo> btbInfos = new ArrayList<>();
        set.forEach(key -> {
            BTBInfo btbInfo = new BTBInfo();
            btbInfo.setType(key.toString());
            Object s = forObject.get(key);
            String s1 = JSONObject.toJSONString(s);
            JSONObject jsonObject = JSONObject.parseObject(s1);
            btbInfo.setM15(jsonObject.get("15m").toString());
            btbInfo.setLast(jsonObject.get("last").toString());
            btbInfo.setBuy(jsonObject.get("buy").toString());
            btbInfo.setSell(jsonObject.get("sell").toString());
            btbInfo.setSymbol(jsonObject.get("symbol").toString());
            btbInfo.setDate(new Date());
            log.info(jsonObject.toString());
            log.info(btbInfo.toString());
            btbInfos.add(btbInfo);
        });
        btbInfoService.saveAll(btbInfos);
    }

    //    @Async
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void btbTask() {
        log.info("开始定时任务");
        CrawlerElementVo crawlerElementVo = new CrawlerElementVo();
        crawlerElementVo.setStartUrl("https://www.feixiaohao.com/currencies/");
        crawlerElementVo.setLikeUrl("https://www.feixiaohao.com/currencies/*/.*");
        crawlerElementVo.setMatchUrl("https://www.feixiaohao.com/currencies/*/.*");
        crawlerElementVo.setTitleElement("div[class=cell maket]>h1");
        crawlerElementVo.setContentElement("div[class=value]");
        startCrawler(crawlerElementVo);
    }

    private void startCrawler(CrawlerElementVo crawlerElementVo) {
        btbCrawler.setCrawlerElementVo(crawlerElementVo);
        btbCrawler.addSeed(crawlerElementVo.getStartUrl());
        btbCrawler.addRegex(crawlerElementVo.getLikeUrl());
        btbCrawler.addRegex("-.*\\.(jpg|png|gif).*");
        btbCrawler.addRegex("-.*#.*");
        btbCrawler.setThreads(10);
//        if (crawlerElementVo.getClimbNum()==0) {
//            crawlerElementVo.setClimbNum(10);
//        }
//        crawlerElementVo.getClimbNum()
        btbCrawler.getConf().setTopN(1000);
        try {
            btbCrawler.start(4);
            btbCrawler.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
