package org.qql.vigour.framework.common.readwrite;

/**
 */
public final class DynamicDataSourceHolder {
    private static final ThreadLocal<DynamicDataSourceType> holder = new ThreadLocal<>();

    private DynamicDataSourceHolder() {
    }

    public static void putDataSource(final DynamicDataSourceType dataSource) {
        holder.set(dataSource);
    }

    public static DynamicDataSourceType getDataSource() {
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }

}
