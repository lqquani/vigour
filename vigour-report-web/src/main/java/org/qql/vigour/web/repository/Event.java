package org.qql.vigour.web.repository;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Event implements Serializable {
    /**
     * 日志标识
     */
    private Integer id;
    /**
     * 日志来源
     */
    private String source;
    /**
     * 操作用户id
     */
    private Integer userId;
    /**
     * 操作用户账号
     */
    private String account;
    /**
     * 日志级别
     */
    private String level;
    /**
     * 日志信息
     */
    private String message;
    /**
     * url
     */
    private String url;
    /**
     * 日志发生的时间
     */
    private Date gmtCreated;
}
