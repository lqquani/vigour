package org.qql.vigour.web.service;

import org.qql.vigour.framework.common.service.mybatis.CrudService;
import org.qql.vigour.web.repository.Event;
import org.qql.vigour.web.repository.EventExample;

/**
 *
 */
public interface EventService extends CrudService<Event, EventExample, Integer> {
    /**
     *
     */
    void clear();

    /**
     * @param source
     * @param account
     * @param message
     * @param level
     * @param url
     */
    void add(String source, String account, String message, String level, String url);
}