package com.qinyuan15.pkten.mvc.crawler;

import com.qinyuan.lib.network.http.HttpClient;
import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractResultCrawler implements ResultCrawler {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractResultCrawler.class);

    protected abstract String getUrl();

    protected abstract ResultParser getParser(String html);

    @Override
    public List<DrawnRecord> crawl() {
        HttpClient client = new HttpClient();
        String url = getUrl();
        try {
            String pageContent = client.getContent(url);
            if (pageContent == null) {
                LOGGER.error("fail to get page content from url {}", url);
                return new ArrayList<>();
            }
            return getParser(pageContent).parse();
        } catch (Exception e) {
            LOGGER.error("Fail to crawl result from {}", url);
            return new ArrayList<>();
        }
    }
}
