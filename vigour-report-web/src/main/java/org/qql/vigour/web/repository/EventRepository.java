package org.qql.vigour.web.repository;

import org.qql.vigour.framework.repository.mybatis.MyBatisRepository;
import org.qql.vigour.framework.repository.mybatis.crud.CrudRepository;

@MyBatisRepository("EventRepository")
public interface EventRepository extends CrudRepository<Event, EventExample, Integer> {
}
