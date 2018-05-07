package org.qql.vigour.framework.common.service.mybatis;

import java.util.List;

import org.qql.vigour.framework.repository.mybatis.crud.InsertRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @param <Dao>
 * @param <Po>
 */
public abstract class AbstractAddService<Dao extends InsertRepository<Po>, Po> implements AddService<Po> {
    @Autowired
    protected Dao dao;

    @Override
    public int add(final Po record) {
        return this.dao.insert(record);
    }

    @Override
    public int batchAdd(final List<Po> records) {
        return this.dao.batchInsert(records);
    }

    @Override
    public int batchAddOnDuplicateKey(final List<Po> records) {
        return this.dao.batchInsertOnDuplicateKey(records);
    }
}
