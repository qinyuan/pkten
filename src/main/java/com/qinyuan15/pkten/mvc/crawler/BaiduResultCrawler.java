package com.qinyuan15.pkten.mvc.crawler;

public class BaiduResultCrawler extends AbstractResultCrawler {
    @Override
    protected String getUrl() {
        return "http://baidu.lecai.com/lottery/draw/view/557";
    }

    @Override
    protected ResultParser getParser(String html) {
        return new BaiduResultParser(html);
    }
}
