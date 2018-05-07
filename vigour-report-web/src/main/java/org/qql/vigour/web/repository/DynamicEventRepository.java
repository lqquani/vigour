package org.qql.vigour.web.repository;

import org.qql.vigour.framework.repository.mybatis.DynamicMyBatisRepository;
import org.qql.vigour.framework.repository.mybatis.MyBatisRepository;
import org.qql.vigour.framework.repository.mybatis.crud.CrudRepository;

@DynamicMyBatisRepository("DynamicEventRepository")
public interface DynamicEventRepository extends CrudRepository<Event, EventExample, Integer> {
}
