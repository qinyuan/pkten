package com.qinyuan15.pkten.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan.lib.lang.time.DateUtils;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest extends DatabaseTestCase {
    private UserDao userDao = new UserDao();

    @Test
    public void testAddNormal() throws Exception {
        assertThat(userDao.getInstanceByName("test001")).isNull();
        userDao.addNormal("test001", "test", DateUtils.nowString());
        assertThat(userDao.getInstanceByName("test001")).isNotNull();

        assertThat(userDao.addNormal("test001", "test", DateUtils.nowString())).isNull();

        userDao.addNormal("test002", "test", null);
        assertThat(userDao.getInstanceByName("test002")).isNotNull();
    }

    @Test
    public void testGetInstanceByName() throws Exception {
        assertThat(userDao.getIdByName("hello")).isEqualTo(1);
        assertThat(userDao.getIdByName("world")).isNull();
    }

    @Test
    public void testGetIdByName() throws Exception {
        User user = userDao.getInstanceByName("hello");
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("hello");
        assertThat(user.getPassword()).isEqualTo("world");
        assertThat(user.getRole()).isEqualTo("ROLE_NORMAL");
        assertThat(user.getExpireTime()).isEqualTo("2015-01-02 12:13:14");

        assertThat(userDao.getInstanceByName("world")).isNull();
    }

    @Test
    public void testDeleteNormal() {
        assertThat(userDao.hasUsername("hello")).isTrue();
        userDao.deleteNormal(1);
        assertThat(userDao.hasUsername("hello")).isFalse();

        assertThat(userDao.hasUsername("admin")).isTrue();
        userDao.deleteNormal(userDao.getIdByName("admin"));
        assertThat(userDao.hasUsername("admin")).isTrue();
    }

    @Test
    public void testGetNormalInstances() {
        assertThat(userDao.getInstances()).hasSize(2);
        List<User> users = userDao.getNormalInstances();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getId()).isEqualTo(1);
        assertThat(users.get(0).getUsername()).isEqualTo("hello");
    }
}
