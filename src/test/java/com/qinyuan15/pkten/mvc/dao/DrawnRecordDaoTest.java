package com.qinyuan15.pkten.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DrawnRecordDaoTest extends DatabaseTestCase {
    private DrawnRecordDao dao = new DrawnRecordDao();

    @Test
    public void testSetMaxPhase() {
        List<DrawnRecord> records = DrawnRecordDao.factory().getInstances();
        assertThat(records).hasSize(17);
        assertThat(records.get(0).getPhase()).isEqualTo(510790);

        records = DrawnRecordDao.factory().setMaxPhase(510788).getInstances();
        assertThat(records).hasSize(15);
        assertThat(records.get(0).getPhase()).isEqualTo(510788);
    }

    @Test
    public void testGetInstances() {
        assertThat(dao.getInstances()).hasSize(17);
    }

    @Test
    public void testAdd() {
        assertThat(dao.getInstances()).hasSize(17);
        assertThat(dao.hasPhase(510773)).isFalse();

        dao.add(510773, "2015-09-10 17:22:30", "02,01,07,03,10,06,08,05,04,09");
        assertThat(dao.getInstances()).hasSize(18);
        assertThat(dao.hasPhase(510773)).isTrue();

        dao.add(510773, "2015-09-10 17:22:30", "02,01,07,03,10,06,08,05,04,09");
        assertThat(dao.getInstances()).hasSize(18);
    }

    @Test
    public void testHasTerm() {
        assertThat(dao.hasPhase(510786)).isTrue();
        assertThat(dao.hasPhase(510773)).isFalse();
    }

    @Test
    public void testGetMaxPhase() {
        assertThat(dao.getMaxPhase()).isEqualTo(510790);
    }
}
