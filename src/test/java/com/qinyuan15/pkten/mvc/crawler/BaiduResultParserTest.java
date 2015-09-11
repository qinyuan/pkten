package com.qinyuan15.pkten.mvc.crawler;

import com.qinyuan.lib.lang.DateUtils;
import com.qinyuan.lib.lang.test.TestFileUtils;
import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BaiduResultParserTest {
    @Test
    public void testParse() throws Exception {
        BaiduResultParser parser = new BaiduResultParser(TestFileUtils.read("baidu-pk10.html"));

        List<DrawnRecord> records = parser.parse();
        assertThat(records).hasSize(180);
        for (DrawnRecord record : records) {
            assertThat(DateUtils.isDateTime(record.getDrawTime())).isTrue();
            assertThat(record.getPhase()).isGreaterThan(510673).isLessThan(510853);
            assertThat(record.getResult()).hasSize(29);
        }
    }
}
