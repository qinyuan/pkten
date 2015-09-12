package com.qinyuan15.pkten.mvc.dao;

import com.google.common.collect.Lists;
import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.AbstractPaginationItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DrawnRecordDao extends AbstractDao<DrawnRecord> {
    private final static Logger LOGGER = LoggerFactory.getLogger(DrawnRecordDao.class);

    public static class Factory extends AbstractPaginationItemFactory<DrawnRecord> {
        private Integer maxPhase;

        public Factory setMaxPhase(Integer maxPhase) {
            this.maxPhase = maxPhase;
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<DrawnRecord> getInstances(int firstResult, int maxResults) {
            HibernateListBuilder listBuilder = setOrder(super.getListBuilder());
            if (IntegerUtils.isPositive(maxPhase)) {
                listBuilder.addFilter("phase<=:phase").addArgument("phase", maxPhase);
            }
            listBuilder.limit(firstResult, maxResults);
            return listBuilder.build(getPersistClass());
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    private static HibernateListBuilder setOrder(HibernateListBuilder listBuilder) {
        listBuilder.addOrder("drawTime", false);    // order by draw time descend
        return listBuilder;
    }

    @Override
    protected HibernateListBuilder getListBuilder() {
        return setOrder(super.getListBuilder());
    }

    public Integer add(Integer phase, String drawnTime, String result) {

        DrawnRecord record = new DrawnRecord();
        record.setPhase(phase);
        record.setDrawTime(drawnTime);
        record.setResult(result);
        List<Integer> ids = addBatch(Lists.newArrayList(record));
        return ids.size() == 0 ? null : ids.get(0);
    }

    public List<Integer> addBatch(List<DrawnRecord> records) {
        if (records == null) {
            LOGGER.error("records is null, nothing to add");
            return new ArrayList<>();
        }

        List<Integer> ids = new ArrayList<>();
        for (DrawnRecord record : records) {
            if (hasPhase(record.getPhase())) {
                LOGGER.info("phase {} already exists", record.getPhase());
            } else {
                ids.add(HibernateUtils.save(record));
            }
        }
        return ids;
    }

    public boolean hasPhase(Integer phase) {
        return new HibernateListBuilder().addEqualFilter("phase", phase)
                .count(DrawnRecord.class) > 0;
    }
}
