package com.kdkj.caijin.util;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.kdkj.caijin.dao.TemporaryInformationDao;
import com.kdkj.caijin.entity.TemporaryInformation;
import com.kdkj.caijin.service.TemporaryInformationService;
import com.kdkj.caijin.vo.CrawlerElementVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Crawling news from hfut news
 *
 * @author hu
 */
@Component
@Scope("prototype")
@Data
@Slf4j
public class NewsCrawler extends BreadthCrawler {
    private CrawlerElementVo crawlerElementVo;
    @Autowired
    private TemporaryInformationDao temporaryInformationDao;

    public NewsCrawler() {
        super("crawl", true);
    }

    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     *                  information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     *                  links which match regex rules from pag
     */
    public NewsCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*start page*/
        this.addSeed("http://book.chaoxing.com/");

        /*fetch url like http://news.hfut.edu.cn/show-xxxxxxhtml*/
        //http://book.chaoxing.com/ebook/*.*html
        //search
        this.addRegex("http://book.chaoxing.com/ebook/*.*html");
        /*do not fetch jpg|png|gif*/
        this.addRegex("-.*\\.(jpg|png|gif).*");
        /*do not fetch url contains #*/
        this.addRegex("-.*#.*");
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.url();
        /*if page is news page*/
        //"http://book.chaoxing.com/ebook/detail*.*html"
        if (page.matchUrl(crawlerElementVo.getMatchUrl())) {
            System.out.println("___________________________________________");
            /*we use jsoup to parse page*/
//        Document doc = page.getDoc();

            /*extract title and content of news by css selector*/
//        String title = page.select("div[class=box_title]>h1").text();
//        String content = page.select("a[class=shidu leftF]").attr("href");
            String title = page.select(crawlerElementVo.getTitleElement()).text();
            String content = page.select(crawlerElementVo.getContentElement()).text();
            String contenthtml = page.select(crawlerElementVo.getContentElement()).html();
//        String author=page.select(crawlerElementVo.getAuthorElement()).text();
//        System.out.println("autor:\n"+author);
            System.out.println("URL:\n" + url);
            System.out.println("title:\n" + title);
            System.out.println("content:\n" + content);
            System.out.println("contenthtml:\n" + contenthtml);
            TemporaryInformation temporaryInformation = new TemporaryInformation();
            temporaryInformation.setTitle(title);
            temporaryInformation.setContent(content);
            temporaryInformation.setSource(url);
            temporaryInformation.setHtml(contenthtml);
            if (!StringUtils.isEmpty(crawlerElementVo.getType())) {
                temporaryInformation.setType(crawlerElementVo.getType());
            }
            temporaryInformation.setTime(new Date());
            if (!StringUtils.isEmpty(temporaryInformation.getTitle())) {
                temporaryInformationDao.saveAndFlush(temporaryInformation);
            }
            /*If you want to add urls to crawl,add them to nextLink*/
            /*WebCollector automatically filters links that have been fetched before*/
        /*If autoParse is true and the link you add to nextLinks does not
          match the regex rules,the link will also been filtered.*/
            //next.add("http://xxxxxx.com");
        }
    }

//    public static void main(String[] args) {
//
//    NewsCrawler crawler = new NewsCrawler("crawl", true);
//    crawler.setThreads(50);
//    crawler.setTopN(10000);
//    //crawler.setResumable(true);
//    /*start crawl with depth of 4*/
//    try {
//		crawler.start(4);
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//    }

}
