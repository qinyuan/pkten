package com.qinyuan15.pkten.mvc.crawler;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan.lib.lang.time.DateUtils;
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
    private int startHour = -1;
    private int endHour = -1;

    private List<ResultCrawler> crawlers;

    public void setCrawlers(List<ResultCrawler> crawlers) {
        this.crawlers = crawlers;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public void stop() {
        stop = true;
    }

    public void init() {
        new DownloadThread().start();
    }

    private boolean inCrawlTime() {
        if (startHour < 0 || endHour < 0) {
            return true;
        }
        int currentHour = DateUtils.currentHour();
        if (startHour <= endHour) {
            return currentHour >= startHour && currentHour <= endHour;
        } else {
            return currentHour >= startHour || currentHour <= endHour;
        }
    }

    private class DownloadThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    ThreadUtils.sleep(interval);

                    if (crawlers == null) {
                        LOGGER.warn("no crawlers, skip crawling");
                        continue;
                    } else if (!inCrawlTime()) {
                        LOGGER.info("not in crawl time, skip crawling");
                        continue;
                    }

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
