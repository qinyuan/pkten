package com.qinyuan15.pkten.mvc.dao;

import com.qinyuan.lib.database.DatabaseInitializer;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DrawnRecordDaoTest {
    private DrawnRecordDao dao = new DrawnRecordDao();

    @Before
    public void setUp() {
        new DatabaseInitializer().init();
    }

    @Test
    public void testGetInstances() {
        assertThat(dao.getInstances()).hasSize(5);
    }

    @Test
    public void testAdd() {
        dao.add(510785, "2015-09-10 18:22:30", "07,10,03,02,01,06,09,05,08,04");
        assertThat(dao.getInstances()).hasSize(6);
        assertThat(dao.hasPhase(510785));

        dao.add(510785, "2015-09-10 18:22:30", "07,10,03,02,01,06,09,05,08,04");
        assertThat(dao.getInstances()).hasSize(6);
    }

    @Test
    public void testHasTerm() {
        assertThat(dao.hasPhase(510786)).isTrue();
        assertThat(dao.hasPhase(510784)).isFalse();
    }
}
