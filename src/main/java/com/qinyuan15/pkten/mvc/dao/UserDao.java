package com.qinyuan15.pkten.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateDeleter;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.security.AbstractUserDao;
import com.qinyuan.lib.mvc.security.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDao extends AbstractUserDao<User> {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    private Integer add(String username, String password, String role, String expireTime) {
        if (hasUsername(username)) {
            LOGGER.error("username '{}' already exists", username);
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setExpireTime(expireTime);
        return HibernateUtils.save(user);
    }

    public Integer addAdmin(String username, String password) {
        return add(username, password, UserRole.ADMIN, null);
    }

    public Integer addNormal(String username, String password, String expireTime) {
        return add(username, password, UserRole.NORMAL, expireTime);
    }

    public void updateNormal(Integer id, String password, String expireTime) {
        User user = getInstance(id);
        if (user == null) {
            LOGGER.error("user is null");
            return;
        }

        if (!UserRole.NORMAL.equals(user.getRole())) {
            LOGGER.error("user {} is not normal user", user.getId());
        }

        user.setPassword(password);
        user.setExpireTime(expireTime);
        HibernateUtils.update(user);
    }

    public List<User> getNormalInstances() {
        return new HibernateListBuilder().addEqualFilter("role", UserRole.NORMAL).build(User.class);
    }

    public void deleteNormal(Integer id) {
        if (IntegerUtils.isPositive(id)) {
            new HibernateDeleter().addEqualFilter("id", id).addEqualFilter("role", UserRole.NORMAL).delete(User.class);
        } else {
            LOGGER.error("invalid id: {}", id);
        }
    }
}
