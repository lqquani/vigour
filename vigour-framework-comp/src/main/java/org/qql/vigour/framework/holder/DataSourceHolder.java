package org.qql.vigour.framework.holder;

/**
 * 
 */
public class DataSourceHolder {
	private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<String>();
	
	public static String getDataSourceId(){
		return dataSourceHolder.get();
	}
	public static void setDataSourceId(String dataSourceId){
		dataSourceHolder.set(dataSourceId);
	}
	
	public static void clear() {
		dataSourceHolder.remove();
    }
	
	public enum DbType {
        DB_TYPE_RW("dataSourceKeyRW"), DB_TYPE_R("dataSourceKeyR");
        private String dataSourceKey;

        DbType(String dataSourceKey) {
            this.dataSourceKey = dataSourceKey;
        }

        public String getDataSourceKey() {
            return dataSourceKey;
        }
    }
}
