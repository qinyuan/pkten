package com.qinyuan15.pkten.mvc.crawler;

import com.google.common.base.Joiner;
import com.qinyuan.lib.lang.DateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.network.html.HtmlParser;
import com.qinyuan.lib.network.html.JavaScriptExecutor;
import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Element;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaiduResultParser extends AbstractResultParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(BaiduResultParser.class);

    public BaiduResultParser(String html) {
        super(html);
    }

    public List<DrawnRecord> parse() {
        HtmlParser parser = new HtmlParser(html);
        for (Element script : parser.getElements("script")) {
            String scriptBody = script.html();
            if (scriptBody.contains("phaseData") || scriptBody.contains("latest_draw_result")) {
                return parseScript(scriptBody);
            }
        }
        LOGGER.error("no script containing variable phaseData, html: \n{}", html);
        return new ArrayList<>();
    }

    private List<DrawnRecord> parseScript(String script) {
        script = "var $=function(){};" + script;
        script += ";var extractResult=" +
                "{latestResult:latest_draw_result,latestPhase:latest_draw_phase,latestTime:latest_draw_time,data:phaseData}" +
                ";extractResult";
        Object obj = new JavaScriptExecutor().evaluate(script);

        if (!(obj instanceof NativeObject)) {
            LOGGER.error("invalid extract result: {}", obj);
            return new ArrayList<>();
        }

        List<DrawnRecord> records = parsePhaseData((NativeObject) obj);
        DrawnRecord latestRecord = parseLatestRecord((NativeObject) obj);
        if (latestRecord != null) {
            records.add(latestRecord);
        }
        return records;
    }

    private List<DrawnRecord> parsePhaseData(NativeObject obj) {
        Object data = obj.get("data");
        if (!(data instanceof NativeObject)) {
            LOGGER.error("Invalid data format: {}", data);
            return new ArrayList<>();
        }

        NativeObject dataObj = (NativeObject) data;
        for (Object value : dataObj.values()) {
            if (!(value instanceof NativeObject)) {
                continue;
            }
            List<DrawnRecord> records = parsePhaseDataItems((NativeObject) value);
            if (records != null && records.size() > 0) {
                return records;
            }
        }

        LOGGER.error("data object is empty");
        return new ArrayList<>();
    }

    private List<DrawnRecord> parsePhaseDataItems(NativeObject obj) {
        List<DrawnRecord> records = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : obj.entrySet()) {
            Object phase = entry.getKey();
            if (!IntegerUtils.isPositive(phase.toString())) {
                LOGGER.warn("invalid phase: {}", phase);
                continue;
            }

            Object value = entry.getValue();
            if (!(value instanceof NativeObject)) {
                LOGGER.warn("invalid value: {}", value);
                continue;
            }

            // parse result
            Object result = ((NativeObject) value).get("result");
            if (result == null) {
                LOGGER.warn("result key not exists");
                continue;
            }
            if (!(result instanceof NativeObject)) {
                LOGGER.warn("invalid result format: {}", result);
                continue;
            }
            String resultString = parseResult((NativeObject) result);
            if (resultString == null) {
                LOGGER.warn("fail to parse result");
                continue;
            }

            // parse time
            Object time = ((NativeObject) value).get("open_time");
            if (!DateUtils.isDateTime(time.toString())) {
                LOGGER.warn("invalid time format: {}", time);
                continue;
            }

            records.add(createDrawnRecord(Integer.parseInt(phase.toString()),
                    time.toString(), resultString));
        }
        return records;
    }

    private DrawnRecord parseLatestRecord(NativeObject obj) {
        // validate class
        Object result = obj.get("latestResult");
        if (!(result instanceof NativeObject)) {
            LOGGER.error("Invalid latest result: {}", result);
            return null;
        }

        // parse result
        String resultString = parseResult((NativeObject) result);
        if (resultString == null) {
            LOGGER.error("Fail to parse latest result");
            return null;
        }

        // parse time
        Object time = obj.get("latestTime");
        if (time == null) {
            LOGGER.error("Fail to parse latest time");
            return null;
        }
        if (!(DateUtils.isDateTime(time.toString()))) {
            LOGGER.error("Invalid latest time format: {}", time);
            return null;
        }

        // parse phase
        Object phase = obj.get("latestPhase");
        if (phase == null) {
            LOGGER.error("Fail to parse latest phase");
            return null;
        }
        if (!(IntegerUtils.isPositive(phase.toString()))) {
            LOGGER.error("Invalid latest phase format: {}", phase);
            return null;
        }

        return createDrawnRecord(Integer.parseInt(phase.toString()), time.toString(), resultString);
    }

    private String parseResult(NativeObject result) {
        if (!result.containsKey("red")) {
            LOGGER.error("result not contains red key: {}", result);
            return null;
        }

        Object red = result.get("red");
        if (!(red instanceof NativeArray)) {
            LOGGER.error("invalid red key value: {}", red);
            return null;
        }

        List<String> items = new ArrayList<>();
        for (Object item : ((NativeArray) red)) {
            if (NumberUtils.isDigits(item.toString())) {
                items.add(item.toString());
            } else {
                LOGGER.error("invalid format of red ball value: {}", item);
                return null;
            }
        }
        return Joiner.on(",").join(items);
    }
}
