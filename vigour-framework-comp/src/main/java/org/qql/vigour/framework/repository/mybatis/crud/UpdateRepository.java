package org.qql.vigour.framework.repository.mybatis.crud;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * @param <T> Po
 * @param <U> Example
 */
public interface UpdateRepository<T, U> {
    /**
     * 根据主键更新用户信息
     *
     * @param record
     * @return 影响的记录数
     */
    int updateById(@Param("record") T record);

    /**
     * 根据条件更新数据
     *
     * @param record
     * @param example
     * @return 影响的记录数
     */
    int updateByExample(@Param("record") T record, @Param("example") U example);

    /**
     * @param records
     * @return 影响的记录数
     */
    int batchUpdate(@Param("records") List<T> records);
}
