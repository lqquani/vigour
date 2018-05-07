package org.qql.vigour.web.service;

import java.util.Date;

import org.qql.vigour.framework.common.service.CommonService;
import org.qql.vigour.web.repository.Event;
import org.springframework.stereotype.Service;

@Service
public class TestService extends CommonService<Event, Integer>{

	public void save(final String source, final String account, final String message, final String level,
            final String url){
		 final Event event = Event.builder()
		            .source(source)
		            .account(account)
		            .userId(-1)
		            .message(message)
		            .level(level)
		            .url(url)
		            .gmtCreated(new Date())
		            .build();
		this.save(event);
	}
}
