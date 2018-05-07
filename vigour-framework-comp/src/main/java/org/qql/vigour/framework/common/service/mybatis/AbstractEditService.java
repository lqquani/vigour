package org.qql.vigour.framework.common.service.mybatis;

import java.util.List;

import org.qql.vigour.framework.repository.mybatis.crud.UpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @param <Dao>
 * @param <Po>
 * @param <Example>
 */
public abstract class AbstractEditService<Dao extends UpdateRepository<Po, Example>, Po, Example>
    implements EditService<Po, Example> {
    @Autowired
    protected Dao dao;

    @Override
    public int editById(final Po record) {
        return this.dao.updateById(record);
    }

    @Override
    public int editByExample(final Po record, final Example example) {
        return this.dao.updateByExample(record, example);
    }

    @Override
    public int batchEdit(final List<Po> records) {
        return this.dao.batchUpdate(records);
    }
}
