/**
 * 
 */
package org.qql.vigour.framework.common.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 基础Dao接口定义
 * @author kevin
 */
@NoRepositoryBean
public interface CommonDao<E,ID extends Serializable>  extends JpaRepository<E,ID>, JpaSpecificationExecutor<E> {

}
