package org.qql.vigour.framework.repository.jpa;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能描述： JPA 持久化的标准实现
 * 
 */
@Slf4j
@Repository
public class JpaEntityManagerImpl implements JpaEntityManager {


	@PersistenceContext
	private javax.persistence.EntityManager em;

	public javax.persistence.EntityManager getPersistEntityManager() {
		return this.em;
	}
	
	@Autowired
	public void setEntityManager(javax.persistence.EntityManager entityManager) {
		this.em = entityManager;
	}

	/**
	 * 保存新增的对象
	 */
	
	public void persist(final Object entity) {
		log.debug("persist entity: {}", entity);
		em.persist(entity);
	}

	/**
	 * 修改
	 */
	
	public Object merge(final Object entity) {
		log.debug("merge entity: {}", entity);
		return em.merge(entity);
	}

	/**
	 * 保存或者修改
	 */
	
	public Object save(final Object entity) {
		log.debug("save entity: {}", entity);
		if (em.contains(entity)) {
			return entity;
		}
		JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
		Serializable id = metadataUtil.getId(entity);
		if (!validId(id)) {
			this.persist(entity);
			return entity;
		}
		@SuppressWarnings("unchecked")
		Object prev = em.find((Class<Object>) entity.getClass(), id);
		if (prev == null) {
			this.persist(entity);
			return entity;
		} else {
			return this.merge(entity);
		}
	}

	/**
	 * 删除对象.
	 * @param entity
	 *            .
	 */
	
	public void remove(final Object entity) {
		log.debug("delete entity: {}", entity);
		em.remove(entity);
	}

	/**
	 * 根据id获取对象
	 * 
	 * @param id
	 * @return
	 */
	
	public Object get(final Class<?> entityClass, final Serializable id) {
		return em.find(entityClass, id);
	}

	/**
	 * 根据id获取对象
	 * 
	 * @param id
	 * @return
	 */
	
	public Object getObjectById(Class<?> entityClass, Serializable id) {
		return em.find(entityClass, id);
	}

	/**
	 * 根据查询JQL语句与参数列表创建Query对象，JQL中参数按顺序绑定，从0开始.
	 * @param values 一个或者多个参数
	 */
	
	public Query createQueryWithIndexParam(final String queryJQL, final Object... values) {
		Query query = this.em.createQuery(queryJQL);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询JQL语句与命名参数列表创建Query对象，JQL中参数按名称绑定
	 * @param values 参数Map
	 */
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Query createQueryWithNameParam(final String queryJQL, final Map<String, ?> values) {
		Query query = this.em.createQuery(queryJQL);
		if (values != null) {
			Iterator it = values.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query;
	}

	/**
	 * 根据查询SQL语句与参数列表创建Query对象，SQL中参数按顺序绑定，从0开始.
	 * @param values 一个或者多个参数
	 */
	
	public Query createNativeQueryWithIndexParam(final String sql, final Object... values) {
		Query query = this.em.createNativeQuery(sql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询SQL语句与参数列表创建Query对象，JQL中参数按名称绑定
	 * @param values 一个或者多个参数
	 */
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Query createNativeQueryWithNameParam(final String sql, final Map<String, ?> values) {
		Query query = this.em.createNativeQuery(sql);
		if (values != null) {
			Iterator it = values.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query;
	}

	/**
	 * Flush当前Session.
	 */
	
	public void flush() {
		this.em.flush();
	}

	/**
	 * Flush当前Session.
	 */
	
	public void commit() {
		this.em.getTransaction().commit();
	}

	/**
	 * 验证ID是否有效
	 * @param id
	 * @return
	 */
	
	public boolean validId(Serializable id) {
		if (id == null)
			return false;
		if (id instanceof Number && ((Number) id).equals(0))
			return false;
		if (id instanceof String && "".equals(id))
			return false;
		return true;
	}

	
}
