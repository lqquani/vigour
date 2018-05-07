package org.qql.vigour.web.service;

import java.util.Date;

import org.qql.vigour.framework.common.service.mybatis.AbstractCrudService;
import org.qql.vigour.web.repository.Event;
import org.qql.vigour.web.repository.EventExample;
import org.qql.vigour.web.repository.EventRepository;
import org.springframework.stereotype.Service;

/**
 */
@Service("EventService")
public class EventServiceImpl
    extends AbstractCrudService<EventRepository, Event, EventExample, Integer>
    implements EventService {

    @Override
    protected EventExample getPageExample(final String fieldName, final String keyword) {
        final EventExample example = new EventExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public void clear() {
        this.dao.deleteByExample(null);
    }

    @Override
    public void add(final String source, final String account, final String message, final String level,
                    final String url) {
        final Event event = Event.builder()
            .source(source)
            .account(account)
            .userId(-1)
            .message(message)
            .level(level)
            .url(url)
            .gmtCreated(new Date())
            .build();
        this.add(event);
    }
}