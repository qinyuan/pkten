package com.qinyuan15.pkten.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.mvc.security.AbstractUserDao;
import com.qinyuan.lib.mvc.security.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
