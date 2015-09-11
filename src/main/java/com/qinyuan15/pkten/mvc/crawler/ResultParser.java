package com.qinyuan15.pkten.mvc.crawler;

import com.qinyuan15.pkten.mvc.dao.DrawnRecord;

import java.util.List;

public interface ResultParser {
    List<DrawnRecord> parse();
}
