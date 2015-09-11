package com.qinyuan15.pkten.mvc.crawler;

import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BaiduResultCrawlerTest {
    @Test
    public void testCrawl() {
        BaiduResultCrawler crawler = new BaiduResultCrawler();
        List<DrawnRecord> records = crawler.crawl();
        assertThat(records).isNotNull().isNotEmpty();
        System.out.println("crawl records count: "+records.size());
    }
}
