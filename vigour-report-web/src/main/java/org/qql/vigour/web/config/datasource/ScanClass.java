package org.qql.vigour.web.config.datasource;

import org.qql.vigour.framework.annotation.Dbsource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
public class ScanClass {

	@Qualifier
	@Dbsource("1")
	public void print() {
		System.out.println("scanClass");
	}
}
