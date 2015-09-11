package com.qinyuan15.pkten.mvc.crawler;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import com.qinyuan15.pkten.mvc.dao.DrawnRecordDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ResultDownloader {
    private final static Logger LOGGER = LoggerFactory.getLogger(ResultDownloader.class);
    private final static int DEFAULT_INTERVAL = 60;

    private int interval = DEFAULT_INTERVAL;
    private boolean stop = false;

    private List<ResultCrawler> crawlers;

    public void setCrawlers(List<ResultCrawler> crawlers) {
        this.crawlers = crawlers;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void stop() {
        stop = true;
    }

    public void init() {
        new DownloadThread().start();
    }

    private class DownloadThread extends Thread {
        @Override
        public void run() {
            while (true) {
                ThreadUtils.sleep(interval);

                if (crawlers == null) {
                    continue;
                }

                try {
                    for (ResultCrawler crawler : crawlers) {
                        List<DrawnRecord> records = crawler.crawl();
                        if (stop) {
                            break;
                        }
                        if (records == null) {
                            LOGGER.warn("records is null, crawler: {}", crawler);
                        } else {
                            new DrawnRecordDao().addBatch(records);
                        }
                    }
                } catch (Throwable e) {
                    LOGGER.error("Fail to crawl result, info: {}", e);
                }
                if (stop) {
                    break;
                }
            }
        }
    }
}
