package org.qql.vigour.framework.common.service.mybatis;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.qql.vigour.framework.common.entity.page.Pager;
import org.qql.vigour.framework.repository.mybatis.crud.SelectRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @param <Dao>
 * @param <Po>
 * @param <Example>
 * @param <Type>    Key字段数据类型(Integer,Long,String等)
 */
public abstract class AbstractGetService<Dao extends SelectRepository<Po, Example, Type>, Po, Example, Type>
    implements GetService<Po, Example, Type> {
    @Autowired
    protected Dao dao;

    @Override
    public boolean exists(final Example example) {
        return this.dao.countByExample(example) > 0;
    }

    @Override
    public Po getById(final Type id) {
        return this.dao.selectById(id);
    }

    @Override
    public List<Po> getByExample(final Example example) {
        return this.dao.selectByExample(example);
    }

    @Override
    public List<Po> getAll() {
        return this.dao.selectByExample(null);
    }

    @Override
    public Po getOneByExample(final Example example) {
        return this.dao.selectOneByExample(example);
    }

    @Override
    public List<Po> getIn(final List<Po> records) {
        return this.dao.selectIn(records);
    }

    @Override
    public List<Po> getByPage(final Pager pageInfo) {
        return this.getByPage(pageInfo, "", "");
    }

    @Override
    public List<Po> getByPage(final Pager pageInfo, final String fieldName, final String keyword) {
        if (StringUtils.isBlank(fieldName)) {
            return this.getByPage(pageInfo, null);
        }
        return this.getByPage(pageInfo, this.getPageExample(fieldName, keyword));
    }

    @Override
    public List<Po> getByPage(final Pager pageInfo, final Example example) {
        pageInfo.setTotalCount(this.dao.countByPager(pageInfo, example));
        return this.dao.selectByPager(pageInfo, example);
    }

    protected abstract Example getPageExample(String fieldName, String keyword);
}
