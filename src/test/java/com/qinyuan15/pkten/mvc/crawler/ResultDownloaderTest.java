package com.qinyuan15.pkten.mvc.crawler;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan15.pkten.mvc.dao.DrawnRecordDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultDownloaderTest extends DatabaseTestCase {
    @Test
    public void testRun() throws Exception {
        ResultDownloader downloader = new ResultDownloader();
        int recordCountBeforeDownload = new DrawnRecordDao().count();

        downloader.setInterval(1);
        List<ResultCrawler> crawlers = new ArrayList<>();
        crawlers.add(new BaiduResultCrawler());
        downloader.setCrawlers(crawlers);
        downloader.init();

        ThreadUtils.sleep(10);
        downloader.stop();
        ThreadUtils.sleep(1); // leaving time to stop

        int recordCountAfterDownload = new DrawnRecordDao().count();
        assertThat(recordCountBeforeDownload).isLessThan(recordCountAfterDownload);
    }
}
