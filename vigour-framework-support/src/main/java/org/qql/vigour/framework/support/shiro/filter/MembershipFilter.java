package org.qql.vigour.framework.support.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.PathMatchingFilter;

/**
 *
 */
public class MembershipFilter extends PathMatchingFilter {

    @Override
    protected boolean onPreHandle(final ServletRequest request, final ServletResponse response,
                                  final Object mappedValue)
        throws Exception {
        return true;
    }
}
