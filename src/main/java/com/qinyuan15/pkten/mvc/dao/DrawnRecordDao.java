package com.qinyuan15.pkten.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DrawnRecordDao extends AbstractDao<DrawnRecord> {
    private final static Logger LOGGER = LoggerFactory.getLogger(DrawnRecordDao.class);

    public Integer add(Integer phase, String drawnTime, String result) {
        if (hasPhase(phase)) {
            LOGGER.error("phase {} already exists", phase);
            return null;
        }

        DrawnRecord record = new DrawnRecord();
        record.setPhase(phase);
        record.setDrawTime(drawnTime);
        record.setResult(result);

        return HibernateUtils.save(record);
    }

    public boolean hasPhase(Integer phase) {
        return new HibernateListBuilder().addEqualFilter("phase", phase)
                .count(DrawnRecord.class) > 0;
    }
}
