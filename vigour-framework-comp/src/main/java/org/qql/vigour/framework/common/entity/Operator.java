package org.qql.vigour.framework.common.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 操作
 */
@Setter
@Getter
@Entity
@Table(name="t_operator")
public class Operator {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**操作名称*/
    @Column(length=64)
    private String name;
    
    /**操作代码*/
    @Column(length=64)
    private String code;
    
    /**操作描述*/
    @Column(length=256)
    private String description;
    
    /**所属资源*/
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name="resource_id")
    private Resource resource;
    
    /**所属角色*/
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name="role_id")
    private Role role;
        
}
